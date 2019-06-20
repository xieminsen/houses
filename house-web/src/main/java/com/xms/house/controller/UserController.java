package com.xms.house.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xms.house.Helper.UserHelper;
import com.xms.house.common.result.ResultMsg;
import com.xms.house.constants.CommonConstants;
import com.xms.house.entity.User;
import com.xms.house.service.AgencyService;
import com.xms.house.service.UserService;
import com.xms.house.util.HashUtils;

import org.apache.commons.lang3.StringUtils;
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
		//登录页跳转
	    if (account == null || account.getName() == null) {
		modelMap.put("agencyList",  agencyService.getAllAgency());
	      return "/user/accounts/register";
		//return "/user/accounts/signin";
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
	  
	  /**
	   * 登录接口
	   */
	  @RequestMapping("/accounts/signin")
	  public String signin(HttpServletRequest req) {
	    String username = req.getParameter("username");
	    String password = req.getParameter("password");
	    String target = req.getParameter("target");
	    if (username == null || password == null) {
	      req.setAttribute("target", target);
	      return "/user/accounts/signin";
	    }
	    //校验登录
	    User user = userService.auth(username, password);
	    
	    if (user == null) {
	      return "redirect:/accounts/signin?" + "target=" + target + "&username=" + username + "&"
	          + ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
	    } else {
	      HttpSession session = req.getSession(true);
	      session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
	      // session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE, user);
	      return StringUtils.isNoneBlank(target) ? "redirect:" + target : "redirect:/index";
	    }
	  }

	  /**
	   * 登出操作
	   * 
	   * @param request
	   * @return
	   */
	  @RequestMapping("accounts/logout")
	  public String logout(HttpServletRequest request) {
	    HttpSession session = request.getSession(true);
	    session.invalidate();
	    return "redirect:/index";
	  }

	  // ---------------------个人信息页-------------------------
	  /**
	   * 1.能够提供页面信息 2.更新用户信息
	   * 
	   * @param updateUser
	   * @param model
	   * @return
	   */
	  @RequestMapping("accounts/profile")
	  public String profile(HttpServletRequest req, User updateUser, ModelMap model) {
	    if (updateUser.getEmail() == null) {
	      return "/user/accounts/profile";//返回个人信息页
	    }
	    userService.updateUser(updateUser, updateUser.getEmail());
	    //更新用户信息后重新设置session
	    //根据主键email查询
	    User query = new User();
	    query.setEmail(updateUser.getEmail());
	    List<User> users = userService.getUserByQuery(query);
	    //设置session
	    req.getSession(true).setAttribute(CommonConstants.USER_ATTRIBUTE, users.get(0));
	    //重定向到个人信息页
	    return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
	  }

	  /**
	   * 修改密码操作
	   * 
	   * @param email
	   * @param password
	   * @param newPassword
	   * @param confirmPassword
	   * @param mode
	   * @return
	   */
	  @RequestMapping("accounts/changePassword")
	  public String changePassword(String email, String password, String newPassword,
	    String confirmPassword, ModelMap mode) {
	    User user = userService.auth(email, password);
	    if (user == null || !confirmPassword.equals(newPassword)) {
	      return "redirct:/accounts/profile?" + ResultMsg.errorMsg("密码错误").asUrlParams();
	    }
	    User updateUser = new User();
	    updateUser.setPasswd(HashUtils.encryPassword(newPassword));
	    userService.updateUser(updateUser, email);
	    return "redirect:/accounts/profile?" + ResultMsg.successMsg("更新成功").asUrlParams();
	  }
	  
	  
}
