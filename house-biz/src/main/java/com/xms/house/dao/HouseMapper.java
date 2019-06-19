package com.xms.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xms.house.entity.Community;
import com.xms.house.entity.House;
import com.xms.house.entity.HouseUser;
import com.xms.house.entity.User;
import com.xms.house.entity.UserMsg;
import com.xms.house.page.PageParams;

public interface HouseMapper {
	
    int deleteByPrimaryKey(Long id);


    int insertSelective(House record);

    House selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(House record);

    int updateByPrimaryKey(House record);
    
  public List<House>  selectPageHouses(@Param("house")House house,@Param("pageParams")PageParams pageParams);
    
    public Long selectPageCount(@Param("house") House query);
	
	public int insert(User account);

	public List<Community> selectCommunity(Community community);

	public int insert(House house);

	public HouseUser selectHouseUser(@Param("userId")Long userId,@Param("id") Long houseId,@Param("type") Integer integer);
	
	public HouseUser selectSaleHouseUser(@Param("id") Long houseId);

	public int insertHouseUser(HouseUser houseUser);

	public int insertUserMsg(UserMsg userMsg);

	public int updateHouse(House updateHouse);
	
	public  int downHouse(Long id);

	public int deleteHouseUser(@Param("id")Long id,@Param("userId") Long userId,@Param("type") Integer value);
}