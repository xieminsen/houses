package com.xms.house.interceptor;

import com.xms.house.entity.User;

public class UserContext {
	//使用线程池共享
	private static final ThreadLocal<User> USER_HODLER = new ThreadLocal<>();
    
	public static void setUser(User user){
		USER_HODLER.set(user);
	}
	
	public static void remove(){
		USER_HODLER.remove();
	}
	
	public static User getUser(){
		return USER_HODLER.get();
	}
}
