package com.example.mybatis.mapper.provider;

import com.example.mybatis.entity.User;

import java.util.Map;

/**
 * UserDAOProvider
 *
 * @author star
 */
public class UserDAOProvider {

    public String updateByPrimaryKey(Map<String, Object> map) {
        User user = (User) map.get("user");
        if (user == null || user.getId() == null) {
            throw new RuntimeException("The primaryKey can not be null.");
        }
        // 拼装 sql 语句
        StringBuilder updateStrSb = new StringBuilder("UPDATE user SET ");
        StringBuilder setStrSb = new StringBuilder();
        if (user.getUsername() != null) {
            setStrSb.append("username = #{user.username},");
        }
        if (user.getPassword() != null) {
            setStrSb.append("password = #{user.password}");
        }

        if (setStrSb.length() <= 0) {
            throw new RuntimeException("None element to update.");
        }
        updateStrSb.append(setStrSb).append(" WHERE id = #{user.id}");

        return updateStrSb.toString();
    }
}
