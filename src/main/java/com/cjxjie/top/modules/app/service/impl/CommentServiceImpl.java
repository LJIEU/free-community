package com.cjxjie.top.modules.app.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cjxjie.top.modules.app.entity.*;
import com.cjxjie.top.modules.app.service.*;
import com.cjxjie.top.modules.app.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjxjie.top.common.utils.PageUtils;
import com.cjxjie.top.common.utils.Query;

import com.cjxjie.top.modules.app.dao.CommentDao;
import org.springframework.transaction.annotation.Transactional;


@Service("commentService")
@DS(value = "app")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {
    @Autowired
    private InvitationCommentService invitationCommentService;

    @Autowired
    InvitationService invitationService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserCommentService userCommentService;

    @Autowired
    private CommentGroupService commentGroupService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommentEntity> page = this.page(
                new Query<CommentEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(CommentVo commentVo) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentVo.getContent());
        // 对IP地址进行处理
        String address = null;
        try {
            address = getIpCity(commentVo.getIp());
        } catch (IOException e) {
            e.printStackTrace();
        }
        commentEntity.setAddress(address);
        commentEntity.setIp(commentVo.getIp());
        this.save(commentEntity);

        Long commentId = commentEntity.getCommentId();
        Long articleId = commentVo.getArticleId();
        // 还需要 评论和帖子关联
        InvitationCommentEntity invitationCommentEntity = new InvitationCommentEntity();
        invitationCommentEntity.setCommentId(commentId);
        invitationCommentEntity.setInvitationId(articleId);
        invitationCommentService.save(invitationCommentEntity);
        // 顺便查询 该帖子的评论数
        List<InvitationCommentEntity> invitationCommentEntities = invitationCommentService.list(new QueryWrapper<InvitationCommentEntity>()
                .eq("invitation_id", commentVo.getArticleId()));

        // 修改该帖子的评论数
        InvitationEntity post = invitationService.getPostById(commentVo.getArticleId());
        post.setComments((long) invitationCommentEntities.size());
        invitationService.updateById(post);

        // 评论和评论者关联
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName(); // 用户名称
        UserEntity user = userService.getByUsername(name);

        UserCommentEntity userCommentEntity = new UserCommentEntity();
        userCommentEntity.setUserId(user.getUserId());
        userCommentEntity.setCommentId(commentId);
        userCommentService.save(userCommentEntity); // 保存


        // 评论结构树
        CommentGroupEntity commentGroupEntity = new CommentGroupEntity();
        commentGroupEntity.setCommentId(commentId);
        // 回复的父评论ID
        commentGroupEntity.setParentId(commentVo.getReplyId());
        commentGroupService.save(commentGroupEntity); // 保存
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CommentEntity> getCommentList(Long postId) {
        // 查询根据文章ID查询出所有评论
        // 所有该帖子评论信息
        List<InvitationCommentEntity> invitationCommentEntityList = invitationCommentService.list(
                new QueryWrapper<InvitationCommentEntity>().eq("invitation_id", postId));

        Long size = (long) invitationCommentEntityList.size(); // 评论数
        InvitationEntity invitationEntity = invitationService.getPostById(postId);
        invitationEntity.setComments(size);
        invitationService.updateById(invitationEntity);// 修改该帖子的评论数

//        System.out.println("=================== 评 论 组 信 息 ===========================");
        // 获取对应的评论组
        List<CommentGroupEntity> commentGroupList = invitationCommentEntityList.stream()
                .map(v -> commentGroupService.getOne(new QueryWrapper<CommentGroupEntity>().eq("comment_id", v.getCommentId()))).collect(Collectors.toList());
//        commentGroupList.forEach(System.out::println);

//        System.out.println("=================== 分 组 信 息【过滤】 ===========================");
        // 将评论组中对应评论信息整理【过滤评论组中的null值】
        List<CommentEntity> result = commentGroupList.stream()
                .filter(Objects::nonNull)
                .map(v -> {
                    // 如果是评论的parentId等于0的那就是基本评论
                    if (v.getParentId() == 0) {
                        CommentEntity parent = baseMapper.selectOne(new QueryWrapper<CommentEntity>().eq("comment_id", v.getCommentId()));
                        List<CommentEntity> child = new ArrayList<>();
                        // 进行查询子评论
                        parent = commentChild(parent, child);
                        return parent;
                    } else {
                        return null;
                    }
                }).collect(Collectors.toList());
        // 将评论结果集中的null值过滤
        result = result.stream().filter(Objects::nonNull).collect(Collectors.toList());
//        result.forEach(System.out::println);
        return result;
    }

    // 获取子列表

    /**
     * @param parent 父评论
     * @param child  子评论集合
     * @return 返回组装好的评论
     */
    public CommentEntity commentChild(CommentEntity parent, List<CommentEntity> child) {
        // 该查询可能是个结果集 所以使用List集合接收
        List<CommentGroupEntity> select = commentGroupService.list(new QueryWrapper<CommentGroupEntity>()
                .eq("parent_id", parent.getCommentId()));

        // 如果查询的结果为null 则说明不存在
        if (select == null) {
            return parent;
        }

        // 获取评论基本信息 并将它添加到 子集合child中
        select.forEach(v -> {
            CommentEntity entity = baseMapper.selectOne(new QueryWrapper<CommentEntity>()
                    .eq("comment_id", v.getCommentId()));
            child.add(entity);
        });

//        System.out.println("===================子评论信息================");
//        select.forEach(System.out::print);

        // 将 子集合child 添加到 parent 中
        parent.setChildList(child);


        // 遍历集合 查看 子集合是否有
        List<CommentEntity> collect = parent.getChildList().stream().map(v -> {
            ArrayList<CommentEntity> temp = new ArrayList<>();
            return commentChild(v, temp);
        }).collect(Collectors.toList());
        parent.setChildList(collect);
        return parent;
    }

    @Value("${baidu.ak}")
    private String ak;

    public String getIpCity(String ip) throws IOException {
        // yVyECiHqbGEVlDKMEGwqKfK0eCBqOgTo
        String api = "https://api.map.baidu.com/location/ip?ak=" + ak
                + "&ip=" + ip + "&coor=bd09ll";
//        SendRequest.sendGet(api);
        URL url = null;
        HttpURLConnection httpURLConnection = null;

        BufferedReader input = null;
        StringBuilder buffer = new StringBuilder();

        url = new URL(api);
        input = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        String str = null;
        while ((str = input.readLine()) != null) {
            buffer.append(str);
        }
        input.close();

        String result = buffer.toString();
        JSONObject jsonObject = JSON.parseObject(result);

        // 内容
        JSONObject content = jsonObject.getJSONObject("content");

        return content.getString("address");
    }
}