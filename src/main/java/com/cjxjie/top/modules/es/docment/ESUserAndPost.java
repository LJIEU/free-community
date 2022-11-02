//package com.cjxjie.top.modules.es.docment;
//
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//import java.io.Serial;
//import java.io.Serializable;
//import java.util.List;
//
///**
// * @author 刘杰
// * @version 1.0
// * @since 2022/10/29 11:48
// */
//@Getter
//@Setter
//@ToString
//@Document(indexName = "user_post")
//public class ESUserAndPost implements Serializable {
//    @Serial
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    private Long id;
//
//    /**
//     * 用户ID
//     */
//    private Long userId;
//    /**
//     * 用户名
//     */
//    @Field(type = FieldType.Keyword)
//    private String username;
//    /**
//     * 头像url
//     */
//    private String avatar;
//    /**
//     * 职业
//     */
//    @Field(type = FieldType.Keyword)
//    private String profession;
//
//    // 内嵌集合 帖子信息
//    @Field(type = FieldType.Nested)
//    private List<ESPost> posts;
//}
