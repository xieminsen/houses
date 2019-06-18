package com.xms.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xms.house.entity.Agency;
import com.xms.house.entity.User;
import com.xms.house.page.PageParams;

public interface AgencyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Agency record);

    int insertSelective(Agency record);

    Agency selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Agency record);

    int updateByPrimaryKey(Agency record);
    
    List<Agency> select(Agency agency);


    List<User>	selectAgent(@Param("user") User user,@Param("pageParams") PageParams pageParams);

	Long selectAgentCount(@Param("user")User user);
}