package com.xms.house.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.xms.house.entity.House;
import com.xms.house.page.PageParams;

import redis.clients.jedis.Jedis;

/*
 * redis连接
 */
@Service
public class RecommendService {

  private static final String HOT_HOUSE_KEY = "hot_house";//设置iredis名称

  private static final Logger logger = LoggerFactory.getLogger(RecommendService.class);//日志

  @Autowired
  private HouseService houseService;

  //初始化redis连接定义
  public void increase(Long id) {
    try {
      Jedis jedis = new Jedis("127.0.0.1");
      jedis.zincrby(HOT_HOUSE_KEY, 1.0D, id + "");
      jedis.zremrangeByRank(HOT_HOUSE_KEY, 0, -11);// 0代表第一个元素,-1代表最后一个元素，保留热度由低到高末尾10个房产
      jedis.close();
    } catch (Exception e) {
      logger.error(e.getMessage(),e);
    }
   
  }

  //返回热门房产id
  public List<Long> getHot() {
    try {
      Jedis jedis = new Jedis("127.0.0.1");
      Set<String> idSet = jedis.zrevrange(HOT_HOUSE_KEY, 0, -1);
      jedis.close();
      List<Long> ids = idSet.stream().map(Long::parseLong).collect(Collectors.toList());
      return ids;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);//有同学反应在未安装redis时会报500,在这里做下兼容,
      return Lists.newArrayList();
    }

  }

  public List<House> getHotHouse(Integer size) {
    House query = new House();
    List<Long> list = getHot();
    list = list.subList(0, Math.min(list.size(), size));
    if (list.isEmpty()) {
      return Lists.newArrayList();
    }
    query.setIds(list);
    final List<Long> order = list;
    //查询房产列表
    List<House> houses = houseService.queryAndSetImg(query, PageParams.build(size, 1));
    //排序
    Ordering<House> houseSort = Ordering.natural().onResultOf(hs -> {
      return order.indexOf(hs.getId());
    });
    return houseSort.sortedCopy(houses);
  }

  public List<House> getLastest() {
    House query = new House();
    query.setSort("create_time");
    List<House> houses = houseService.queryAndSetImg(query, new PageParams(8, 1));
    return houses;
  }
}
