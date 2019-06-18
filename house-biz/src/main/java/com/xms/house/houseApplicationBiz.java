package com.xms.house;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xms.house") // mybatis 的扫描包
public class houseApplicationBiz {

	public static void main(String[] args) {
		SpringApplication.run(houseApplicationBiz.class, args);
	}
}
