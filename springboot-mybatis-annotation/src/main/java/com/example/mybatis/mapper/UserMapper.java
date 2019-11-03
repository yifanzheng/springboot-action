package com.example.mybatis.mapper;

import com.example.mybatis.domain.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 数据持久层
 *
 * @author kevin
 * @date 2018-10-22 11:01
 **/
public interface UserMapper {

    @Select("SELECT * FROM user WHERE NAME = #{name}")
    Users findByName(@Param("name") String name);

    @Insert("INSERT INTO user(NAME, password) VALUES(#{name}, #{password})")
    int insert(@Param("name") String name, @Param("password") String password);
}
