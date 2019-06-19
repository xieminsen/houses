package com.xms.house.util;

import java.nio.charset.Charset;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * 密码加盐加密
 * @author xie
 *
 */
public class HashUtils {
	
	private static final HashFunction FUNCTION = Hashing.md5();
	
	private static final String SALT = "xms.com";
	//加盐加密
	public static String encryPassword(String password){
	   HashCode hashCode =	FUNCTION.hashString(password+SALT, Charset.forName("UTF-8"));
	   return hashCode.toString();
	}
	

}
