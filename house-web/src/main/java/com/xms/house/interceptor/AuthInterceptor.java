package com.xms.house.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Joiner;
import com.xms.house.constants.CommonConstants;
import com.xms.house.entity.User;

/**
 * 鉴权安全拦截
 * 假如需要访问受保护的页面 查询是否存在session 不存在直接转到登录页
 * 存在直接跳转
 * @author xie
 *
 */
@Component//注册为bean
public class AuthInterceptor implements HandlerInterceptor{

	//执行之前拦截
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Map<String, String[]> map = request.getParameterMap();
		map.forEach((k,v) -> {
			if (k.equals("errorMsg") || k.equals("successMsg") || k.equals("target")) {
				request.setAttribute(k, Joiner.on(",").join(v));
			}
		});
		
		//获取是否为静态资源请求或者error请求
	    String reqUri =	request.getRequestURI();
	    if (reqUri.startsWith("/static") || reqUri.startsWith("/error") ) {
			return true;
		}
	    HttpSession session = request.getSession(true);
	    User user =  (User)session.getAttribute(CommonConstants.USER_ATTRIBUTE);
	    if (user != null) {
	    	//使用了threadlocal 线程共享变量
			UserContext.setUser(user);
		}
		return true;
	}

	//方法执行之后执行
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	/**
	 * 页面渲染之后执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserContext.remove();
	}
	

}
