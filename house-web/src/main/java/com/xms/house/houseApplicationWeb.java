package com.xms.house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.xms.house.autoconfig.EnableHttpClient;

@SpringBootApplication
@EnableHttpClient//这是自己定义的bean  
@EnableAsync
public class houseApplicationWeb {

	public static void main(String[] args) {
		SpringApplication.run(houseApplicationWeb.class, args);
	}
}
