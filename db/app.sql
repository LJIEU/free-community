CREATE TABLE `category`
(
    `category_id` bigint UNSIGNED     NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `parent_id`   bigint UNSIGNED     NOT NULL DEFAULT 0 COMMENT '父分类ID',
    `name`        varchar(50)         NULL COMMENT '名称',
    `sort`        int UNSIGNED        NULL COMMENT '排序',
    `icon`        varchar(255)        NULL COMMENT '图标URL',
    `state`       tinyint(2) UNSIGNED NOT NULL COMMENT '状态【0:不显示  1:显示】',
    `is_delete`   tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除【0:否  1:删除】',
    `create_time` datetime            NULL COMMENT '创建时间',
    PRIMARY KEY (`category_id`)
) ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8mb4 COMMENT = '帖子分类';

CREATE TABLE `comment`
(
    `comment_id`  bigint(20) UNSIGNED                                   NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '评论内容',
    `state`       tinyint(2) UNSIGNED                                   NOT NULL DEFAULT 1 COMMENT '状态【0:不显示  1:显示】 ',
    `create_time` datetime                                              NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    `is_delete`   tinyint(2) UNSIGNED                                   NULL     DEFAULT 0 COMMENT '是否删除【0:不删除  1:删除】',
    PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '评论信息表\r\n'
  ROW_FORMAT = Dynamic;

CREATE TABLE `invitation_category`
(
    `id`            bigint          NOT NULL COMMENT 'ID ',
    `invitation_id` bigint UNSIGNED NOT NULL COMMENT '帖子ID',
    `category_id`   bigint UNSIGNED NOT NULL COMMENT '分类ID',
    PRIMARY KEY (`id`)
) ENGINE=`InnoDB` DEFAULT CHARACTER SET utf8mb4 COMMENT = '帖子和分类关联表';

CREATE TABLE `invitation`
(
    `invitation_id` bigint(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
    `title`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '标题',
    `content`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '帖子内容',
    `document`      longblob                                                      NULL COMMENT '文件',
    `pageviews`     bigint(20) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '浏览量',
    `comments`      bigint(20) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '评论数',
    `likes`         bigint(20) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '点赞数',
    `topic`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '话题',
    `sort`          int(10) UNSIGNED                                              NOT NULL DEFAULT 0 COMMENT '排序',
    `state`         tinyint(2) UNSIGNED                                           NOT NULL DEFAULT 1 COMMENT '状态【0: 不显示  1:显示】',
    `address`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '发帖地址',
    `create_time`   datetime                                                      NULL     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    `is_delete`     tinyint(2) UNSIGNED                                           NOT NULL DEFAULT 0 COMMENT '是否删除【0:不删除  1:删除】',
    PRIMARY KEY (`invitation_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '帖子信息表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `invitation_comment`
(
    `id`            bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `invitation_id` bigint(20) UNSIGNED NOT NULL COMMENT '帖子ID',
    `comment_id`    bigint(20) UNSIGNED NOT NULL COMMENT '评论ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '帖子与评论关联表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `user`
(
    `user_id`     bigint(20) UNSIGNED                                           NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户名',
    `salt`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '盐',
    `password`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `avatar`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '头像url',
    `sex`         tinyint(2) UNSIGNED                                           NOT NULL DEFAULT 3 COMMENT '性别【0: 男  1: 女  3: 未知】',
    `signature`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '个性签名',
    `address`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '地址',
    `profession`  varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '职业',
    `cover`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '封面url',
    `state`       tinyint(2) UNSIGNED                                           NOT NULL DEFAULT 1 COMMENT '状态 【0: 下线  1: 在线】',
    `is_delete`   tinyint(2) UNSIGNED                                           NULL     DEFAULT 0 COMMENT '是否删除【0: 显示  1:删除】',
    `create_time` datetime                                                      NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户信息表'
  ROW_FORMAT = Dynamic;

CREATE TABLE `user_invitation`
(
    `id`            bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`       bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
    `invitation_id` bigint(20) UNSIGNED NOT NULL COMMENT '帖子ID',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户与帖子关联表'
  ROW_FORMAT = Dynamic;

