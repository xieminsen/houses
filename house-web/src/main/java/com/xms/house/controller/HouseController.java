package com.xms.house.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xms.house.common.result.ResultMsg;
import com.xms.house.constants.CommonConstants;
import com.xms.house.constants.HouseUserType;
import com.xms.house.entity.Comment;
import com.xms.house.entity.House;
import com.xms.house.entity.HouseUser;
import com.xms.house.entity.User;
import com.xms.house.entity.UserMsg;
import com.xms.house.interceptor.UserContext;
import com.xms.house.page.PageData;
import com.xms.house.page.PageParams;
import com.xms.house.service.AgencyService;
import com.xms.house.service.CityService;
import com.xms.house.service.CommentService;
import com.xms.house.service.HouseService;
import com.xms.house.service.RecommendService;


@Controller
public class HouseController {
	
	@Autowired
	private HouseService houseService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private RecommendService recommendService;
	
	@Autowired
	private CommentService commentService;

	/**
	 * 1.实现分页
	 * 2.支持小区搜索、类型搜索
	 * 3.支持排序
	 * 4.支持展示图片、价格、标题、地址等信息
	 * @return
	 */
	@RequestMapping("/house/list")
	public String houseList(Integer pageSize,Integer pageNum,House query,ModelMap modelMap){
	  PageData<House> ps =  houseService.queryHouse(query,PageParams.build(pageSize, pageNum));
	  List<House> hotHouses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
	  modelMap.put("recomHouses", hotHouses);
	  modelMap.put("ps", ps);
	  modelMap.put("vo", query);
	  return "house/listing";
	}
	
	@RequestMapping("/house/toAdd")
	public String toAdd(ModelMap modelMap) {
		modelMap.put("citys", cityService.getAllCitys());
		modelMap.put("communitys", houseService.getAllCommunitys());
		return "/house/add";
	}
	/**
	 * 添加房屋
	 * @param house
	 * @return
	 */
	@RequestMapping("/house/add")
	public String doAdd(House house){
		User user = UserContext.getUser();
		house.setState(CommonConstants.HOUSE_STATE_UP);
		houseService.addHouse(house,user);
		return "redirect:/house/ownlist";
	}
	
	@RequestMapping("house/ownlist")
	public String ownlist(House house,Integer pageNum,Integer pageSize,ModelMap modelMap){
		User user = UserContext.getUser();//获取当前用户查询
		house.setUserId(user.getId());
		house.setBookmarked(false);
		//查询个人名下的房产信息
		modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize, pageNum)));
		modelMap.put("pageType", "own");
		return "/house/ownlist";
	}
	
	/**
	 * 查询房屋详情
	 * 查询关联经纪人
	 * @param id
	 * @return
	 */
	@RequestMapping("house/detail")
	public String houseDetail(Long id,ModelMap modelMap){
		House house = houseService.queryOneHouse(id);//获取房屋详情
	    HouseUser houseUser = houseService.getHouseUser(id);//获取房屋及经纪人关联
	    recommendService.increase(id);//redis操作
	    List<Comment> comments = commentService.getHouseComments(id,8);
		if (houseUser.getUserId() != null && !houseUser.getUserId().equals(0)) {
			//如果经纪人不为空 添加modelMap
			modelMap.put("agent", agencyService.getAgentDeail(houseUser.getUserId()));
		}
	    List<House> rcHouses =  recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
	    modelMap.put("recomHouses", rcHouses);
		modelMap.put("house", house);
		 modelMap.put("commentList", comments);
		return "/house/detail";
	}
	
	/**
	 * 留言功能
	 * @param userMsg
	 * @return
	 */
	@RequestMapping("house/leaveMsg")
	public String houseMsg(UserMsg userMsg){
	  houseService.addUserMsg(userMsg);
	  return "redirect:/house/detail?id=" + userMsg.getHouseId() ;//+ ResultMsg.successMsg("留言成功").asUrlParams()
	}
	
	//1.评分
	@ResponseBody
	@RequestMapping("house/rating")
	public ResultMsg houseRate(Double rating,Long id){
		houseService.updateRating(id,rating);
		return ResultMsg.successMsg("ok");
	}
	
	
	//2.收藏
	@ResponseBody
	@RequestMapping("house/bookmark")
	public ResultMsg bookmark(Long id){
	  User user =	UserContext.getUser();
	  houseService.bindUser2House(id, user.getId(), true);
	  return ResultMsg.successMsg("ok");
	}
	
	//3.删除收藏
	@ResponseBody
	@RequestMapping("house/unbookmark")
	public ResultMsg unbookmark(Long id){
	  User user =	UserContext.getUser();
	  houseService.unbindUser2House(id,user.getId(),HouseUserType.BOOKMARK);
	  return ResultMsg.successMsg("ok");
	}
	
	@RequestMapping(value="house/del")
	public String delsale(Long id,String pageType){
	   User user = UserContext.getUser();
	   houseService.unbindUser2House(id,user.getId(),pageType.equals("own")?HouseUserType.SALE:HouseUserType.BOOKMARK);
	   return "redirect:/house/ownlist";
	}
	
	//4.收藏列表
	@RequestMapping("house/bookmarked")
	public String bookmarked(House house,Integer pageNum,Integer pageSize,ModelMap modelMap){
		User user = UserContext.getUser();
		house.setBookmarked(true);
		house.setUserId(user.getId());
		modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize, pageNum)));
		modelMap.put("pageType", "book");
		return "/house/ownlist";
	}
}
