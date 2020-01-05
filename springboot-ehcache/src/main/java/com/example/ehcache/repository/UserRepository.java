package com.example.ehcache.repository;

import com.example.ehcache.dto.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * UserRepository
 *
 * @author star
 */
@Repository
public class UserRepository {

    /**
     * 获取用户信息(此处是模拟的数据)
     */
    public UserDTO getUserByName(String username) {
        UserDTO user = getUserFromList(username);
        return user;
    }

    /**
     * 从模拟的数据集合中筛选 username 的数据
     */
    private UserDTO getUserFromList(String username) {

        List<UserDTO> userDaoList = DataFactory.getUserDaoList();
        for (UserDTO user : userDaoList) {
            if (Objects.equals(user.getName(), username)) {
                return user;
            }
        }
        return null;
    }
}
