package com.example.jdbctemplate.repositry;

import com.example.jdbctemplate.entity.User;
import com.example.jdbctemplate.utils.DataConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * UserRepository
 *
 * @author star
 */
@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUser(String username) {
        String sql = "SELECT id, username, password FROM user WHERE username = ?";
        Map<String, Object> resultMap = jdbcTemplate.queryForMap(sql, username);
        User user = new User();
        user.setId(DataConvertUtils.getLong(resultMap.get("id")));
        user.setUsername(DataConvertUtils.getString(resultMap.get("username")));
        user.setPassword(DataConvertUtils.getString(resultMap.get("password")));

        return user;
    }

    public List<User> listUser() {
        String sql = "SELECT id, username, password FROM user";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        List<User> users = maps.stream().map(e -> {
            User user = new User();
            user.setId(DataConvertUtils.getLong(e.get("id")));
            user.setUsername(DataConvertUtils.getString(e.get("username")));
            user.setPassword(DataConvertUtils.getString(e.get("password")));
            return user;
        }).collect(Collectors.toList());
        return users;
    }

    /**
     * 不存在则插入，存在则更新
     * 注：被插入的数据中需要存在 UNIQUE 索引或 PRIMARY KEY 字段，这里使用 username 字段作为唯一索引 (UNIQUE)
     *
     * @param user
     */
    public int saveOrUpdateUser(User user) {
        String sql = "INSERT INTO user(username, password) VALUES(?,?)" +
                " ON DUPLICATE KEY" +
                " UPDATE password = ?";
        int count = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getPassword());

        return count;
    }

    public int deleteUser(String username) {
        String sql = "DELETE FROM user WHERE username = ?";
        int count = jdbcTemplate.update(sql, username);

        return count;
    }
}
