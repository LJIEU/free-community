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
        // ???IP??????????????????
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
        // ????????? ?????????????????????
        InvitationCommentEntity invitationCommentEntity = new InvitationCommentEntity();
        invitationCommentEntity.setCommentId(commentId);
        invitationCommentEntity.setInvitationId(articleId);
        invitationCommentService.save(invitationCommentEntity);
        // ???????????? ?????????????????????
        List<InvitationCommentEntity> invitationCommentEntities = invitationCommentService.list(new QueryWrapper<InvitationCommentEntity>()
                .eq("invitation_id", commentVo.getArticleId()));

        // ???????????????????????????
        InvitationEntity post = invitationService.getPostById(commentVo.getArticleId());
        post.setComments((long) invitationCommentEntities.size());
        invitationService.updateById(post);

        // ????????????????????????
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName(); // ????????????
        UserEntity user = userService.getByUsername(name);

        UserCommentEntity userCommentEntity = new UserCommentEntity();
        userCommentEntity.setUserId(user.getUserId());
        userCommentEntity.setCommentId(commentId);
        userCommentService.save(userCommentEntity); // ??????


        // ???????????????
        CommentGroupEntity commentGroupEntity = new CommentGroupEntity();
        commentGroupEntity.setCommentId(commentId);
        // ??????????????????ID
        commentGroupEntity.setParentId(commentVo.getReplyId());
        commentGroupService.save(commentGroupEntity); // ??????
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CommentEntity> getCommentList(Long postId) {
        // ??????????????????ID?????????????????????
        // ???????????????????????????
        List<InvitationCommentEntity> invitationCommentEntityList = invitationCommentService.list(
                new QueryWrapper<InvitationCommentEntity>().eq("invitation_id", postId));

        Long size = (long) invitationCommentEntityList.size(); // ?????????
        InvitationEntity invitationEntity = invitationService.getPostById(postId);
        invitationEntity.setComments(size);
        invitationService.updateById(invitationEntity);// ???????????????????????????

//        System.out.println("=================== ??? ??? ??? ??? ??? ===========================");
        // ????????????????????????
        List<CommentGroupEntity> commentGroupList = invitationCommentEntityList.stream()
                .map(v -> commentGroupService.getOne(new QueryWrapper<CommentGroupEntity>().eq("comment_id", v.getCommentId()))).collect(Collectors.toList());
//        commentGroupList.forEach(System.out::println);

//        System.out.println("=================== ??? ??? ??? ??????????????? ===========================");
        // ???????????????????????????????????????????????????????????????null??????
        List<CommentEntity> result = commentGroupList.stream()
                .filter(Objects::nonNull)
                .map(v -> {
                    // ??????????????????parentId??????0????????????????????????
                    if (v.getParentId() == 0) {
                        CommentEntity parent = baseMapper.selectOne(new QueryWrapper<CommentEntity>().eq("comment_id", v.getCommentId()));
                        List<CommentEntity> child = new ArrayList<>();
                        // ?????????????????????
                        parent = commentChild(parent, child);
                        return parent;
                    } else {
                        return null;
                    }
                }).collect(Collectors.toList());
        // ????????????????????????null?????????
        result = result.stream().filter(Objects::nonNull).collect(Collectors.toList());
//        result.forEach(System.out::println);
        return result;
    }

    // ???????????????

    /**
     * @param parent ?????????
     * @param child  ???????????????
     * @return ????????????????????????
     */
    public CommentEntity commentChild(CommentEntity parent, List<CommentEntity> child) {
        // ?????????????????????????????? ????????????List????????????
        List<CommentGroupEntity> select = commentGroupService.list(new QueryWrapper<CommentGroupEntity>()
                .eq("parent_id", parent.getCommentId()));

        // ????????????????????????null ??????????????????
        if (select == null) {
            return parent;
        }

        // ???????????????????????? ?????????????????? ?????????child???
        select.forEach(v -> {
            CommentEntity entity = baseMapper.selectOne(new QueryWrapper<CommentEntity>()
                    .eq("comment_id", v.getCommentId()));
            child.add(entity);
        });

//        System.out.println("===================???????????????================");
//        select.forEach(System.out::print);

        // ??? ?????????child ????????? parent ???
        parent.setChildList(child);


        // ???????????? ?????? ??????????????????
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

        // ??????
        JSONObject content = jsonObject.getJSONObject("content");

        return content.getString("address");
    }
}