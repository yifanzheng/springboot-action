package com.example.cache.repostory;

import com.example.cache.dto.UserDto;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * 持久层
 *
 * @author kevin
 * @date 2018-10-14 21:26
 **/
@Repository
@CacheConfig(cacheNames = "users")// 指定缓存名称，在本类中是全局的
public class UserRepo {

    /**
     * 获取用户信息(此处是模拟的数据)
     *
     * @param username
     * @return
     */
    @Cacheable(key = "#username") // 缓存key是username的数据到缓存users中，如果没有指定key则方法参数作为key保存到缓存中
    public UserDto getUser(String username) {

        UserDto user = getUserFromList(username);
        System.out.println(username + " 已被缓存");
        return user;
    }

    /**
     * 删除用户信息
     *
     * @param username
     * @return
     */
    @CacheEvict(key = "#username") // 从缓存users中删除key是username的数据
    public List<UserDto> deleteUser(String username) {

        List<UserDto> userDaoList = DataFactory.getUserDaoList();
        userDaoList.remove(getUserFromList(username));

        return userDaoList;
    }

    /**
     *
     * @param username
     * @return
     */
    @CachePut(key = "#username") // 新增或更新缓存中的数据
    public List<UserDto> save(String username) {

        // 添加到集合
        List<UserDto> userDaoList = DataFactory.getUserDaoList();
        for (UserDto userDto : userDaoList) {
             // 不能重复添加相同数据
             if(Objects.equals(userDto.getName(),username)){
                 return userDaoList;
             }
        }
        UserDto user = new UserDto();
        user.setName(username);
        user.setAge("50");
        userDaoList.add(user);

        return userDaoList;
    }

    /**
     * 从模拟的数据集合中筛选username的数据
     *
     * @param username
     * @return
     */
    private UserDto getUserFromList(String username) {

        List<UserDto> userDaoList = DataFactory.getUserDaoList();
        for (UserDto user : userDaoList) {
            if (Objects.equals(user.getName(), username)) {
                return user;
            }
        }
        return null;
    }
}
