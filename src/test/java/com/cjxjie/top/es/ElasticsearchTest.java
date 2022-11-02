package com.cjxjie.top.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjxjie.top.modules.app.entity.UserEntity;
import com.cjxjie.top.modules.app.service.InvitationService;
import com.cjxjie.top.modules.app.service.UserService;
import com.cjxjie.top.modules.es.docment.ESPost;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * @author 刘杰
 * @version 1.0
 * @since 2022/10/29 10:30
 */
@SpringBootTest
@SuppressWarnings("all")
public class ElasticsearchTest {

    @Autowired
//    private ElasticsearchConfig elasticsearchConfig;
    private RestHighLevelClient client;

    @Autowired
    private InvitationService invitationService;

    @Test
    public void test() {
        List<ESPost> importAllList = invitationService.getImportAllList();
        importAllList.forEach(v -> {
            System.out.println(v);
        });
    }


    @Test
    public void search() throws IOException, ParseException {
        // 用户名模糊搜索2  or 职业模糊搜索2  or 帖子标题模糊搜索2  or  帖子内容模糊搜索2
        String keyword = "2";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD");
        Date date = simpleDateFormat.parse("20222-10-01");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        // 查询所有
        builder.must(QueryBuilders.matchAllQuery());

        // 根据建立查询信息 进行分页查询  第0页 每页2个user数据  根据userId 进行升序排序
        searchSourceBuilder.query(builder).sort("userId", SortOrder.ASC)
                .sort("posts.invitationId", SortOrder.ASC)
                .from(0)
                .size(5);
        SearchRequest searchRequest = new SearchRequest("user_post").source(searchSourceBuilder);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHit[] hits = search.getHits().getHits();
        List<ESPost> list = new Vector<>();
        if (hits.length == 0) {
            System.out.println("没有找到数据!");
            return;
        }
        for (SearchHit hit : hits) {
            // 获取查询结果的源数据
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);

            // 将其转为 JSON 数据
            JSON parse = (JSON) JSON.parse(sourceAsString);
            System.out.println(parse);

            // 再将 JSON 数据 转 对象
            ESPost esUserAndPost = JSONObject.toJavaObject(parse, ESPost.class);

            // 放入集合中
            if (esUserAndPost != null)
                list.add(esUserAndPost);
        }
        list.forEach(System.out::println);
    }

    @Test
    public void selectDoc() throws IOException {
//        GetRequest getRequest = new GetRequest("new_index");
        GetRequest getRequest = new GetRequest("user_post");
        getRequest.id("1");
        // 发送请求 [GET http://localhost:9200/new_index/_doc/1]
        GetResponse documentFields = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(documentFields);
    }

    @Test
    public void createDoc() throws IOException {
        UserEntity user = new UserEntity();
        user.setUserId(1L).setUsername("LIU").setPhone("14717477909");
        // 创建index请求
        IndexRequest indexRequest = new IndexRequest("new_index");
        // 设置索引
        indexRequest.id("1");
        // 设置超时时间 【默认就是 5s 】
        indexRequest.timeout(TimeValue.timeValueSeconds(5));
        // 添加数据
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);

        // 发送请求 [PUT http://localhost:9200/new_index/_doc/1?timeout=5s]
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse);
    }

    @Test
    public void indexCRD() throws IOException {
        createIndex();
        selectIndex();
        deleteIndex();
        selectIndex();
    }

    public void deleteIndex() throws IOException {
        // 删除索引
        DeleteIndexRequest deleteRequest = new DeleteIndexRequest("test_index");
        AcknowledgedResponse response = client.indices().delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    public void selectIndex() throws IOException {
        // 查询索引是否存在
        GetIndexRequest getRequest = new GetIndexRequest("test_index");
        boolean exists = client.indices().exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    private void createIndex() throws IOException {
        // 创建索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("test_index");
        CreateIndexResponse response = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(response);
    }
}
