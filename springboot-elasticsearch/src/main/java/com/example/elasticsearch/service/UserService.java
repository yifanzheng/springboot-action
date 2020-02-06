package com.example.elasticsearch.service;

import com.example.elasticsearch.api.ElasticSearchDocument;
import com.example.elasticsearch.api.ElasticSearchRestAPI;
import com.example.elasticsearch.entity.User;
import com.example.elasticsearch.exception.ServiceException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserService
 *
 * @author star
 */
@Service
public class UserService {

    private static final String ES_INDEX = "test";

    @Autowired
    private ElasticSearchRestAPI elasticSearchRestAPI;

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     */
    public void saveUser(User user) {
        ElasticSearchDocument<User> document = new ElasticSearchDocument<>();
        document.setId(user.getId().toString());
        document.setData(user);
        try {
            elasticSearchRestAPI.save(ES_INDEX, document);
        } catch (IOException e) {
            throw new ServiceException("Failed to save user, id: " + user.getId(), e);
        }

    }

    /**
     * 批量保存用户
     *
     * @param userList 用户集合
     */
    public void saveAllUser(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return;
        }
        List<ElasticSearchDocument<User>> documentList = new ArrayList<>(userList.size());
        ElasticSearchDocument<User> document = null;
        for (User user : userList) {
            document = new ElasticSearchDocument<>();
            document.setId(String.valueOf(user.getId()));
            document.setData(user);

            documentList.add(document);
        }

        try {
            elasticSearchRestAPI.saveAll(ES_INDEX, documentList);
        } catch (IOException e) {
            throw new ServiceException("Failed to batch save users", e);
        }
    }

    /**
     * 根据用户 ID 获取用户信息
     *
     * @param id 用户 ID
     * @return 用户对象
     */
    public User getUser(String id) {
        try {
            User user = elasticSearchRestAPI.get(ES_INDEX, id, User.class);

            return user;
        } catch (IOException e) {
            throw new ServiceException("Failed to get user, id: " + id, e);
        }
    }

    /**
     * 根据用户姓名查询
     *
     * @param name 用户姓名
     * @return 用户集合
     */
    public List<User> searchUserByName(String name) {
        // 构建查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchPhraseQuery("name", name));
        // 构建查询生成器
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(boolQueryBuilder);
        try {
            List<User> userList = elasticSearchRestAPI.searchByQuery(ES_INDEX, sourceBuilder, User.class);

            return userList;
        } catch (IOException e) {
            throw new ServiceException("Failed to search user by name: " + name, e);
        }
    }

    /**
     * 根据用户 ID 删除用户信息
     *
     * @param id 用户 ID
     */
    public void deleteUser(String id) {
        try {
            elasticSearchRestAPI.delete(ES_INDEX, id);
        } catch (IOException e) {
            throw new ServiceException("Failed to delete user by id: " + id, e);
        }
    }

    /**
     * 根据用户姓名删除
     *
     * @param name 用户姓名
     */
    public void deleteUserByName(String name) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchPhraseQuery("name", name));

        try {
            elasticSearchRestAPI.deleteByQuery(ES_INDEX, boolQueryBuilder);
        } catch (IOException e) {
            throw new ServiceException("Failed to delete user by name: " + name, e);
        }
    }


}
