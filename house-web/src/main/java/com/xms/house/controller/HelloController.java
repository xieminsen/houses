package com.xms.house.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xms.house.entity.User;
import com.xms.house.service.UserService;

@Controller
public class HelloController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("hello")
	public String hello(User user,ModelMap map) {
		List<User> userList = userService.getUserList(user);
		User user1 = userList.get(0);
		if(user1 != null ) {
			throw new  IllegalArgumentException();
		}
		map.put("user", user1);
		return "hello";
	}
}
