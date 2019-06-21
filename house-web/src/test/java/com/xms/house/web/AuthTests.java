package com.xms.house.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.xms.house.entity.User;
import com.xms.house.service.HouseService;
import com.xms.house.service.UserService;



@RunWith(SpringRunner.class)
@SpringBootTest//(webEnvironment=WebEnvironment.DEFINED_PORT)
public class AuthTests {

	@Autowired
	private UserService userService;
	@Autowired
	private HouseService houseService;

	@Test
	public void testAuth() {
		houseService.bindUser2House(Long.valueOf(47),Long.valueOf(35),false);
	}

}