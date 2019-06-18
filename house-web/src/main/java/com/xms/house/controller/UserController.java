package com.xms.house.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xms.house.Helper.UserHelper;
import com.xms.house.common.result.ResultMsg;
import com.xms.house.entity.User;
import com.xms.house.service.AgencyService;
import com.xms.house.service.UserService;

@Controller
//@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	  @Autowired
	  private AgencyService agencyService;
	/**
	 * 查询list列表
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user/list")
	public List<User> selectByFilter(User user,HttpServletRequest request){
		return userService.getUserList(user);
		
	} 
	
	 /**
	   * 注册提交:1.注册验证 2 发送邮件 3验证失败重定向到注册页面 注册页获取:根据account对象为依据判断是否注册页获取请求
	   * 
	   * @param account
	   * @param modelMap
	   * @return
	   */
	  @RequestMapping("accounts/register")
	  public String accountsRegister(User account, ModelMap modelMap) {
	    if (account == null || account.getName() == null) {
		modelMap.put("agencyList",  agencyService.getAllAgency());
	      return "/user/accounts/register";
	    }
	    // 用户验证
	    ResultMsg resultMsg = UserHelper.validate(account);
	    if (resultMsg.isSuccess() && userService.addAccount(account)) {
	      modelMap.put("email", account.getEmail());
	      return "/user/accounts/registerSubmit";
	    } else {
	      return "redirect:/accounts/register?" + resultMsg.asUrlParams();
	    }
	  }
	/*
	 * 分页查询
	 * @RequestMapping(value = "/user/list", method = RequestMethod.POST)
	public PageVo<User> userList(User user,PageParam pageParam, HttpServletRequest request) {
		return userService.getUserPage(user, pageParam);
	}*/
	
	  @RequestMapping("accounts/verify")
	  public String verify(String key) {
	    boolean result = userService.enable(key);
	    if (result) {
	      return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
	    } else {
	      return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败,请确认链接是否过期");
	    }
	  }
}
