package com.cjxjie.top.es;

import com.alibaba.fastjson.JSON;
import com.cjxjie.top.modules.app.entity.UserEntity;
import com.cjxjie.top.modules.app.service.InvitationService;
import com.cjxjie.top.modules.app.service.UserService;
import com.cjxjie.top.modules.es.docment.ESUserAndPost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.io.IOException;
import java.util.List;

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
    private UserService userService;

    @Test
    public void test() {
        List<ESUserAndPost> importAllList = userService.getImportAllList();
        importAllList.forEach(v -> {
            System.out.println(v);
        });
    }

/*
# nested 用于嵌套字段查询
GET user_post/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "match": {
            "username": "user"
          }
        },
        {
          "nested": {
            "path": "posts",
            "query": {
              "bool": {
                "must": [
                  {
                    "match": {
                      "posts.content": "wsfw"
                    }
                  }
                ]
              }
            }
          }
        }
      ]
    }
  }
}
     */

    @Test
    public void search() {
        String keyword = "2";

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.termQuery("", keyword));
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
