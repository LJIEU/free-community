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
import com.cjxjie.top.modules.es.docment.ESPost;
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
     * ????????????
     */
    @Override

    public R login(String username, String password) {
        String jwtToken = null;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UserEntity user = baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", username));
        if (user == null) {
            return R.error(HttpStatus.HTTP_BAD_GATEWAY, "???????????????!");
        }
        String hex = new Sha256Hash(password, user.getSalt()).toHex();
        if (!hex.equals(user.getPassword())) { // ?????????????????????????????????
            return R.error(HttpStatus.HTTP_BAD_GATEWAY, "???????????????!");
        }

        if (user.getState() == 0) {
            return R.error(HttpStatus.HTTP_BAD_GATEWAY, "??????????????????,??????????????????");
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
        // ??????????????????
        List<CommentAndUserVo> list = new ArrayList<>();

        // ??????????????? ????????????????????????????????????
        for (CommentEntity v : commentList) {
            // ?????????????????????????????? ????????????
            if (v.getChildList() != null) {
                List<CommentAndUserVo> child = getChild(v.getChildList(), new ArrayList<>(), user);
                if (child == null)
                    break;
                CommentAndUserVo commentAndUserVo = new CommentAndUserVo();
                commentAndUserVo.setChildList(child);
                BeanUtils.copyProperties(v, commentAndUserVo);
                UserInfoVo userInfoVo = getUserInfoVo(v);
                // ???????????????
                commentAndUserVo.setAuthor(userInfoVo);

                log.warn("????????????:" + user);

                // ????????????????????????
                commentAndUserVo = isLike(user, commentAndUserVo);
                list.add(commentAndUserVo);
            } else {

                // ??????????????????
//            ArrayList<UserInfoVo> userList = new ArrayList<>();
                UserInfoVo userInfoVo = getUserInfoVo(v);
//            userList.add(userInfoVo);

                // ????????????
                CommentAndUserVo commentAndUserVo = new CommentAndUserVo();
                BeanUtils.copyProperties(v, commentAndUserVo);
                commentAndUserVo.setAuthor(userInfoVo);
                list.add(commentAndUserVo);
            }
        }
        return list;
    }

    /**
     * ?????? ??????ID ??????????????? ????????????
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
     * ???????????????
     *
     * @param phone ????????????
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BufferedImage getVerifiCode(String phone) {

        // ???????????????????????????
        Uni.init(ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        // ???????????????
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }


        // ????????????????????? (????????????)

        // ????????????

        // ????????????
/*        try {
            UniResponse res = message.send();
            System.out.println(res);*/

        // ??????????????????????????????
        CodeEntity codeEntity = new CodeEntity();

        if (appRedis.getKey(phone) == null || Objects.equals(appRedis.getKey(phone), "")) {
            // ???????????????
            appRedis.removeKey(phone);
        }
        // ????????? 3?????? ??????
        appRedis.saveOrUpdate(phone, code, 180L);
/*        CodeEntity temp = codeService.getOne(new QueryWrapper<CodeEntity>().eq("phone", phone));
        if (temp != null) {
            // ????????????????????????
            codeService.remove(new QueryWrapper<CodeEntity>().eq("phone", phone));
        }

        // ??????????????????
        codeEntity.setPhone(phone);
        codeEntity.setCode(code.toString());
        codeEntity.setOverTime(DateUtils.addDateMinutes(new Date(), 3));
        codeService.save(codeEntity);*/

/*        } catch (UniException e) {
            System.out.println("Error: " + e);
            System.out.println("RequestId: " + e.requestId);
        }*/

        // ?????? ???????????????????????????  ?????????
        BufferedImage image = producer.createImage(String.valueOf(code));
        return image;
    }

    /**
     * ????????????
     *
     * @param registerVo ??????????????????
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void Register(RegisterVo registerVo) {

        UserEntity user = new UserEntity();
        user.setUsername(registerVo.getUsername());

        //sha256??????
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(registerVo.getPassword(), salt).toHex());
        user.setSalt(salt);
        user.setPhone(registerVo.getPhone());

        log.warn("??????????????????:" + user);
        this.save(user);
    }

/*    @Override
    public List<ESPost> getImportAllList() {
        return baseMapper.getImportAllList();
    }*/

    /**
     * ????????????????????????
     */
    private UserInfoVo getUserInfoVo(CommentEntity v) {
        UserEntity user = getUser(v);
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setId(user.getUserId());
        userInfoVo.setName(user.getUsername());
        userInfoVo.setAvatarURL(user.getAvatar());
        userInfoVo.setHomeURL("??????????????????");
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
        // ?????? parent ??? ??????????????????
        for (CommentEntity v : parent) {
            // ??????????????????
            UserInfoVo userInfoVo = getUserInfoVo(v);

            // ????????????
            CommentAndUserVo temp = new CommentAndUserVo();

            BeanUtils.copyProperties(v, temp);
            temp.setAuthor(userInfoVo); // ??????????????????
            System.out.println("????????????:" + temp);

            // ????????????????????????
            temp = isLike(user, temp);
            commentAndUserVo.add(temp);


            if (v.getChildList() == null) {
                break;
            } else {  // ?????? ????????????????????? ????????????
                getChild(v.getChildList(), commentAndUserVo, user);
            }
        }
        return commentAndUserVo;
    }

    /**
     * ??????????????????
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
                // ??????null ??????????????? ??????????????????
                temp.setIsLike(1);
            else
                temp.setIsLike(0);
        }
        return temp;
    }
}