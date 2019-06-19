package com.xms.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xms.house.entity.Comment;
import com.xms.house.entity.Community;
import com.xms.house.entity.House;
import com.xms.house.entity.UserMsg;
import com.xms.house.page.PageParams;

@Mapper
public interface CommentMapper {
	
	int deleteByPrimaryKey(Long id);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

  List<House> selectHouse(@Param("house") House query, @Param("pageParams")  PageParams pageParams);

  Long selectHouseCount(@Param("house")House query);

  List<Community> selectCommunity(Community community);

  int insertUserMsg(UserMsg userMsg);

  int updateHouse(House house);

  int insert(Comment comment);

  List<Comment> selectComments(@Param("houseId")long houseId, @Param("size")int size);

  List<Comment> selectBlogComments(@Param("blogId")long blogId, @Param("size")int size);
  
}

