package com.xms.house.dao;

import java.util.List;

import javax.annotation.Resource;

import com.xms.house.entity.User;
import com.xms.house.util.MapContext;

@Resource
public interface UserMapper {
	//根据主键删除
    int deleteByPrimaryKey(Long id);
    //插入
    int insert(User record);
    //插入
    int insertSelective(User record);
    //根据主键查询
    User selectByPrimaryKey(Long id);
    //更新
    int updateByPrimaryKeySelective(User record);
    //更新
    int updateByPrimaryKey(User record);
    //查询list
    List<User> selectByFilter(MapContext map);
    //删除email
	public int delete(String email);

	public int update(User updateUser);

	public List<User> selectUsersByQuery(User user);
}