package com.example.elasticsearch.api;

import com.example.elasticsearch.ElasticSearchApplication;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ElasticSearchRestAPITest
 *
 * @author star
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = ElasticSearchApplication.class)
public class ElasticSearchRestAPITest {

    @Autowired
    private ElasticSearchRestAPI elasticSearchRestAPI;

    private static final String TEST_INDEX = "test-index";

    private Map<String, Map<String, Object>> mockProperties() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "text");

        Map<String, Map<String, Object>> propertiesMap = new HashMap<>();
        propertiesMap.put("name", map);

        return propertiesMap;
    }

    @Test
    public void testCreateIndex() throws IOException {
        // 创建索引
        elasticSearchRestAPI.createIndex(TEST_INDEX, this.mockProperties());
        boolean existIndex = elasticSearchRestAPI.isExistIndex(TEST_INDEX);
        // 验证结果
        Assert.assertTrue(existIndex);
    }

    @Test
    public void testDeleteIndex() throws IOException {
        // 删除索引
        elasticSearchRestAPI.deleteIndex(TEST_INDEX);
        boolean existIndex = elasticSearchRestAPI.isExistIndex(TEST_INDEX);
        // 验证结果
        Assert.assertFalse(existIndex);
    }
}
