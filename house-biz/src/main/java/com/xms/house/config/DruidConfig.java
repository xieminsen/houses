package com.xms.house.config;

import java.sql.SQLException;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;

@Configuration
public class DruidConfig {

	@ConfigurationProperties(prefix="spring.druid")//使用configurationProperties将配置文件中的配置写入
	@Bean(initMethod="init",destroyMethod="close")//加上bean注解
	public DruidDataSource dataSource(Filter statFilter) throws SQLException{
		DruidDataSource dataSource = new DruidDataSource();
		//将下边的慢日志方法添加statFilter
		dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
		return dataSource;
	}
	
	/**
	 * 打印慢sql日志
	 * @return
	 */
	@Bean
	public Filter statFilter(){
		StatFilter filter = new StatFilter();
		filter.setSlowSqlMillis(5000);//设置慢sql的时间
		filter.setLogSlowSql(true);
		filter.setMergeSql(true);
		return filter;
	}
	
	/**
	 * 指定拦截
	 * @return
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
		return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
	}
	
}
