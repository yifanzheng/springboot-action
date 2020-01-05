package com.example.ehcache.repository;

import com.example.ehcache.dto.UserDTO;

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

    private static List<UserDTO> userDtoList;

    static {
        // 初始化集合
        userDtoList = new ArrayList<>();

        UserDTO user = null;
        for (int i = 0; i < 5; i++) {
            user = new UserDTO();
            user.setName("star" + i);
            user.setAge(23);
            userDtoList.add(user);
        }
    }

    public static List<UserDTO> getUserDaoList() {
        return userDtoList;
    }
}
