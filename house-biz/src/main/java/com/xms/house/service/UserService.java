package com.xms.house.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.xms.house.dao.UserMapper;
import com.xms.house.entity.User;
import com.xms.house.util.BeanHelper;
import com.xms.house.util.HashUtils;
import com.xms.house.util.MapContext;

@Service
public class UserService {

	@Resource
	private UserMapper userMapper; //注入dao
	
	  @Autowired
	  private FileService fileService;
	  
	  @Autowired
	  private MailService mailService;
	/**
	 * 得到userList
	 * @param user
	 * @return
	 */
	public List<User> getUserList(User user){
		MapContext map = MapContext.newOne();
		map.put("user", user);
		return userMapper.selectByFilter(map);
	}
	
	
	  /**
	   * 1.插入数据库，非激活;密码加盐md5;保存头像文件到本地 2.生成key，绑定email 3.发送邮件给用户
	   * 
	   * @param account
	   * @return
	   */
	  @Transactional(rollbackFor = Exception.class)
	  public boolean addAccount(User account) {
		//设置密码
	    account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
	    //设置头像
	    List<String> imgList = fileService.getImgPaths(Lists.newArrayList(account.getAvatarFile()));
	    if (!imgList.isEmpty()) {
	      account.setAvatar(imgList.get(0));
	    }
	    //BeanHelper是自己封装的一个设置默认值得工具类
	    BeanHelper.setDefaultProp(account, User.class);
	    BeanHelper.onInsert(account);
	    account.setEnable(0);
	    userMapper.insert(account);
	    mailService.registerNotify(account.getEmail());
	    return true;
	  }
	
	/**
	 * 得到userList分页
	 * @param example
	 * @return
	 */
/*	public PageVo<User> getUserPage(User user,PageParam pageParam){
		PageUtil.pageParam(pageParam);
		MapContext map = MapContext.newOne();
		map.put("user", user);
		List<User> list=userMapper.selectByFilter(map);
		return PageUtil.getPage(list, pageParam);
	}*/
	  
	  /**
	   * 邮箱激活
	   * @param key
	   * @return
	   */
	  public boolean enable(String key) {
		    return mailService.enable(key);
		  }
}
