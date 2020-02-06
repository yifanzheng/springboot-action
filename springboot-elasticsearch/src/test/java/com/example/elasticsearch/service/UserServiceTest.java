package com.example.elasticsearch.service;

import com.example.elasticsearch.ElasticSearchApplication;
import com.example.elasticsearch.entity.User;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * UserServiceTest
 *
 * @author star
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = ElasticSearchApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private static final int ID = 1;

    private static final String USER_NAME = "star";

    private User mockUser() {
        User user = new User();
        user.setId(ID);
        user.setName(USER_NAME);
        user.setEmail("test@new.com");
        user.setAge(24);

        return user;
    }

    @Test
    public void test1SaveUser() {
        User user = this.mockUser();
        // 保存数据
        userService.saveUser(user);
        User newUser = userService.getUser(user.getId().toString());
        // 验证结果
        Assert.assertEquals(user.getId(), newUser.getId());
        Assert.assertEquals(user.getName(), newUser.getName());
        Assert.assertEquals(user.getEmail(), newUser.getEmail());
        Assert.assertEquals(user.getAge(), newUser.getAge());
    }

    @Test
    public void test2SearchUserByName() {
        List<User> users = userService.searchUserByName(USER_NAME);
        // 验证结果
        Assert.assertNotNull(users);
        Assert.assertTrue(users.size() > 0);
    }

    @Test
    public void test3DeleteUser() {
        String id = String.valueOf(ID);

        // 删除数据
        userService.deleteUser(id);

        User newUser = userService.getUser(id);
        // 验证结果
        Assert.assertNull(newUser);
    }
}
