package cn.shop.shop.service.impl;

import cn.shop.mapper.UserProductMapMapper;
import cn.shop.pojo.UserProductMap;
import cn.shop.pojo.UserProductMapExample;
import cn.shop.shop.service.UserProductMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户消费记录
 * @author 赵铭涛
 * @creation time 2018/12/9 - 14:57
 */
@Service
public class UserProductMapServiceImpl implements UserProductMapService {
    @Autowired
    private UserProductMapMapper userProductMapMapper;
    @Override
    public Map<String, Object> getUserProductMapByShopId(UserProductMap userProductMap) {
        Map<String, Object> map = new HashMap<>();
        //创建查询条件
        UserProductMapExample example = new UserProductMapExample();
        UserProductMapExample.Criteria criteria = example.createCriteria();
        criteria.andShopIdEqualTo(userProductMap.getShopId());
        //执行查询
        List<UserProductMap> userProductMaps = userProductMapMapper.selectByExample(example);
        for (UserProductMap productMap : userProductMaps) {
            productMap.setWeekCreateTime(DateFormat.getDateInstance(DateFormat.FULL).format(productMap.getCreateTime()));
            productMap.setIsCreateTime(DateFormat.getDateInstance().format(productMap.getCreateTime()));
            System.out.println("星期："+productMap.getWeekCreateTime());
            System.out.println("时间："+productMap.getIsCreateTime());
        }
        //java8新特性
        //获取分组id
        Map<Integer,List<UserProductMap>> productIdKey = userProductMaps.stream().collect(Collectors.groupingBy(UserProductMap::getProductId));
        //获取日期
        Map<String,List<UserProductMap>> createTimeKey = userProductMaps.stream().collect(Collectors.groupingBy(UserProductMap::getIsCreateTime));
        List<UserProductMap> collect = userProductMaps.stream().filter(userProductMap1 -> userProductMap1.getProductId()==10).distinct().collect(Collectors.toList());
        for (UserProductMap productMap : collect) {
            System.out.println("productMap:"+productMap);
        }
        return map;
    }
}
