package com.xms.house.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 是用来调整上边两个拦截
 * @author xie
 *
 */
@Configuration//添加为springbean
public class WebMvcConf extends WebMvcConfigurerAdapter {

	@Autowired
	private AuthActionInterceptor authActionInterceptor;
	
	@Autowired
	private AuthInterceptor authInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		 registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");
		    registry
		        .addInterceptor(authActionInterceptor).addPathPatterns("/house/toAdd")
		        .addPathPatterns("/accounts/profile").addPathPatterns("/accounts/profileSubmit")
		        .addPathPatterns("/house/bookmarked").addPathPatterns("/house/del")
		        .addPathPatterns("/house/ownlist").addPathPatterns("/house/add")
		        .addPathPatterns("/house/toAdd").addPathPatterns("/agency/agentMsg")
		        .addPathPatterns("/comment/leaveComment").addPathPatterns("/comment/leaveBlogComment");
		    super.addInterceptors(registry);
	}

	
}
