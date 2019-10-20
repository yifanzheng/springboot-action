package com.example.c3p0;

import com.example.c3p0.domain.User;
import com.example.c3p0.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class C3p0ApplicationTests {
	@Autowired
	private UserService userService;

	@Test
	public void testGetUser() {
		List<User> users = userService.getUsers();
		System.out.println(users.size());
	}

	@Test
	public void testInsert(){
		User user = new User();
		user.setUser_name("kent");
		user.setScore(1208);
		userService.insertUser(user);
	}

}
