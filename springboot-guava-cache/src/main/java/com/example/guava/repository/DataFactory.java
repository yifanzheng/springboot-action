package com.example.guava.repository;

import com.example.guava.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据工厂，模拟数据库的数据
 *
 * @author star
 **/
public class DataFactory {

    private DataFactory() {
    }

    private static List<UserDto> userDtoList;

    static {
        // 初始化集合
        userDtoList = new ArrayList<>();

        UserDto user = null;
        for (int i = 0; i < 5; i++) {
            user = new UserDto();
            user.setName("star" + i);
            user.setAge(23);
            userDtoList.add(user);
        }
    }

    public static List<UserDto> getUserDaoList() {
        return userDtoList;
    }
}
