package com.cjxjie.top.modules.app.service.impl;

import cn.hutool.http.HttpStatus;
import com.apistd.uni.Uni;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cjxjie.top.common.utils.DateUtils;
import com.cjxjie.top.common.utils.R;
import com.cjxjie.top.modules.app.entity.*;
import com.cjxjie.top.modules.app.service.*;
import com.cjxjie.top.modules.app.utils.AppRedis;
import com.cjxjie.top.modules.app.utils.JwtTokenUtil;
import com.cjxjie.top.modules.app.vo.CommentAndUserVo;
import com.cjxjie.top.modules.app.vo.RegisterVo;
import com.cjxjie.top.modules.app.vo.UserInfoVo;
import com.cjxjie.top.modules.es.docment.ESUserAndPost;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;

import com.cjxjie.top.modules.app.dao.UserDao;
import org.springframework.transaction.annotation.Transactional;


/**
 * The type User service.
 */
@Service("userService")
@DS(value = "app")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Lazy
    @Autowired
    private Producer producer;

    @Autowired
    private CodeService codeService;

    @Autowired
    private UserCommentService userCommentService;

    @Lazy
    @Autowired
    private CommentLikeService commentLikeService;

    @Autowired
    private CommentGroupService commentGroupService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Lazy
    @Autowired
    private AppRedis appRedis;

    @Value(value = "${jwt.tokenHeader}")
    private String tokenHeader;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /**
     * 登录验证
     */
    @Override

    public R login(String username, String password) {
        String jwtToken = null;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UserEntity user = baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", username));
        if (user == null) {
            return R.error(HttpStatus.HTTP_BAD_GATEWAY, "用户不存在!");
        }
        String hex = new Sha256Hash(password, user.getSalt()).toHex();
        if (!hex.equals(user.getPassword())) { // 如果不相等说明密码错误
            return R.error(HttpStatus.HTTP_BAD_GATEWAY, "密码不正确!");
        }

        if (user.getState() == 0) {
            return R.error(HttpStatus.HTTP_BAD_GATEWAY, "账号已被锁定,请联系管理员");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        jwtToken = jwtTokenUtil.generateToken(userDetails);
        return R.ok().put("jwt_token", jwtToken);
    }

    @Override
    public UserEntity getByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", username));
    }


    @Override
    public List<CommentAndUserVo> getCommentAndUser(List<CommentEntity> commentList, UserEntity user) {
        // 结果集初始化
        List<CommentAndUserVo> list = new ArrayList<>();

        // 遍历评论集 组装评论对应的评论者信息
        for (CommentEntity v : commentList) {
            // 如果当前节点有子节点 遍历取出
            if (v.getChildList() != null) {
                List<CommentAndUserVo> child = getChild(v.getChildList(), new ArrayList<>(), user);
                if (child == null)
                    break;
                CommentAndUserVo commentAndUserVo = new CommentAndUserVo();
                commentAndUserVo.setChildList(child);
                BeanUtils.copyProperties(v, commentAndUserVo);
                UserInfoVo userInfoVo = getUserInfoVo(v);
                // 评论者信息
                commentAndUserVo.setAuthor(userInfoVo);

                log.warn("用户信息:" + user);

                // 当前用户是否点赞
                commentAndUserVo = isLike(user, commentAndUserVo);
                list.add(commentAndUserVo);
            } else {

                // 组装用户信息
//            ArrayList<UserInfoVo> userList = new ArrayList<>();
                UserInfoVo userInfoVo = getUserInfoVo(v);
//            userList.add(userInfoVo);

                // 评论信息
                CommentAndUserVo commentAndUserVo = new CommentAndUserVo();
                BeanUtils.copyProperties(v, commentAndUserVo);
                commentAndUserVo.setAuthor(userInfoVo);
                list.add(commentAndUserVo);
            }
        }
        return list;
    }

    /**
     * 根据 评论ID 查找父评论 用户信息
     */
    @Override
    public UserEntity getParent(Long commentId) {
        CommentGroupEntity groupEntity = commentGroupService.getOne(new QueryWrapper<CommentGroupEntity>().eq("comment_id", commentId));
        Long parentId = groupEntity.getParentId();

        UserCommentEntity userComment = userCommentService.getOne(new QueryWrapper<UserCommentEntity>().eq("comment_id", parentId));

        return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("user_id", userComment.getUserId()));
    }


    /**
     * The constant ACCESS_KEY_ID.
     */
    @Value(value = "${uni.accessKeyId}")
    public static String ACCESS_KEY_ID;

    @Value(value = "${uni.accessKeySecret}")
    private static String ACCESS_KEY_SECRET;

    /**
     * 生成验证码
     *
     * @param phone 手机号码
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BufferedImage getVerifiCode(String phone) {

        // 发送短信服务初始化
        Uni.init(ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        // 生成验证码
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }


        // 设置自定义参数 (变量短信)

        // 构建信息

        // 发送短信
/*        try {
            UniResponse res = message.send();
            System.out.println(res);*/

        // 将验证码写入数据库中
        CodeEntity codeEntity = new CodeEntity();

        if (appRedis.getKey(phone) == null || Objects.equals(appRedis.getKey(phone), "")) {
            // 存在就删除
            appRedis.removeKey(phone);
        }
        // 再写入 3分钟 缓存
        appRedis.saveOrUpdate(phone, code, 180L);
/*        CodeEntity temp = codeService.getOne(new QueryWrapper<CodeEntity>().eq("phone", phone));
        if (temp != null) {
            // 删除存在的验证码
            codeService.remove(new QueryWrapper<CodeEntity>().eq("phone", phone));
        }

        // 然后继续重建
        codeEntity.setPhone(phone);
        codeEntity.setCode(code.toString());
        codeEntity.setOverTime(DateUtils.addDateMinutes(new Date(), 3));
        codeService.save(codeEntity);*/

/*        } catch (UniException e) {
            System.out.println("Error: " + e);
            System.out.println("RequestId: " + e.requestId);
        }*/

        // 没证 所以短信不可能发送  用图片
        BufferedImage image = producer.createImage(String.valueOf(code));
        return image;
    }

    /**
     * 注册用户
     *
     * @param registerVo 注册基本信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void Register(RegisterVo registerVo) {

        UserEntity user = new UserEntity();
        user.setUsername(registerVo.getUsername());

        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(registerVo.getPassword(), salt).toHex());
        user.setSalt(salt);
        user.setPhone(registerVo.getPhone());

        log.warn("注册用户信息:" + user);
        this.save(user);
    }

    @Override
    public List<ESUserAndPost> getImportAllList() {
        return baseMapper.getImportAllList();
    }

    /**
     * 获取用户基本信息
     */
    private UserInfoVo getUserInfoVo(CommentEntity v) {
        UserEntity user = getUser(v);
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setId(user.getUserId());
        userInfoVo.setName(user.getUsername());
        userInfoVo.setAvatarURL(user.getAvatar());
        userInfoVo.setHomeURL("目前未设置！");
        return userInfoVo;
    }

    /**
     * Gets child.
     *
     * @param parent           the parent
     * @param commentAndUserVo the comment and user vo
     * @param user             the user
     * @return the child
     */
    public List<CommentAndUserVo> getChild(List<CommentEntity> parent, List<CommentAndUserVo> commentAndUserVo, UserEntity user) {
        // 遍历 parent 集 进行组装信息
        for (CommentEntity v : parent) {
            // 组装用户信息
            UserInfoVo userInfoVo = getUserInfoVo(v);

            // 评论信息
            CommentAndUserVo temp = new CommentAndUserVo();

            BeanUtils.copyProperties(v, temp);
            temp.setAuthor(userInfoVo); // 加入用户信息
            System.out.println("组装信息:" + temp);

            // 当前用户是否点赞
            temp = isLike(user, temp);
            commentAndUserVo.add(temp);


            if (v.getChildList() == null) {
                break;
            } else {  // 如果 其还有子评论则 递归进行
                getChild(v.getChildList(), commentAndUserVo, user);
            }
        }
        return commentAndUserVo;
    }

    /**
     * 获取用户信息
     *
     * @param entity the entity
     * @return user
     */
    public UserEntity getUser(CommentEntity entity) {
        UserCommentEntity userComment = userCommentService.getOne(new QueryWrapper<UserCommentEntity>().eq("comment_id", entity.getCommentId()));
        return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("user_id", userComment.getUserId()));
    }

    /**
     * Is like comment and user vo.
     *
     * @param user the user
     * @param temp the temp
     * @return the comment and user vo
     */
    public CommentAndUserVo isLike(UserEntity user, CommentAndUserVo temp) {
        if (user != null) {
            CommentLikeEntity commentLike = commentLikeService.getOne(new QueryWrapper<CommentLikeEntity>()
                    .eq("user_id", user.getUserId())
                    .eq("comment_id", temp.getCommentId()));
            if (commentLike != null)
                // 不为null 说明找到了 该用户点了赞
                temp.setIsLike(1);
            else
                temp.setIsLike(0);
        }
        return temp;
    }
}