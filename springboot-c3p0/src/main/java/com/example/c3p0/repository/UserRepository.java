package com.example.c3p0.repository;

import com.example.c3p0.domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * DataRepository
 *
 * @author star
 **/
@Repository
public class UserRepository {

    @Autowired
    private QueryRunner queryRunner;

    /**
     * 获取所有用户信息
     *
     * @return
     */
    public List<User> getData() {
        try {
            String sql = "SELECT id,user_name,score FROM user_point";
            List<User> userList = queryRunner.query(sql, new BeanListHandler<>(User.class));
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * 插入数据
     *
     * @param user
     */
    public void insertData(User user) {
        try {
            String sql = "INSERT INTO user_point(user_name,score) VALUES(?,?)";
            queryRunner.update(sql,
                    user.getUser_name(),
                    user.getScore());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
