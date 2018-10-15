package com.example.cache.repostory;

import com.example.cache.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据工厂
 * 模拟数据库的数据
 *
 * @author kevin
 * @date 2018-10-14 22:35
 **/
public class DataFactory {

    private DataFactory() {
    }

    private static List<UserDto> userDtoList;

    static {
        // 初始化集合
        userDtoList = new ArrayList<>();

        UserDto user = null;
        for (int i = 0; i < 10; i++) {
            user = new UserDto();
            user.setName("star" + i);
            user.setAge("2" + i);
            userDtoList.add(user);
        }
    }

    public static List<UserDto> getUserDaoList() {
        return userDtoList;
    }
}
