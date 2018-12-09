package cn.shop.shop.service.impl;

import cn.shop.dto.UserProductMapExecution;
import cn.shop.enums.UserProductMapStateEnum;
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
    public Map<String, Object> getUserProductMapByShopId(Integer shopId,String cxtj) {
        Map<String, Object> map = new HashMap<>();
        if (cxtj.equals("")) {
            cxtj=null;
        }
        //执行查询
        List<UserProductMap> userProductMaps = userProductMapMapper.selectByShopIdConcat(shopId,cxtj);
        map.put("userProductMaps",userProductMaps);
        for (UserProductMap productMap : userProductMaps) {
            productMap.setWeekCreateTime(DateFormat.getDateInstance(DateFormat.FULL).format(productMap.getCreateTime()));
            productMap.setIsCreateTime(DateFormat.getDateInstance().format(productMap.getCreateTime()));
            System.out.println("星期："+productMap.getWeekCreateTime());
            System.out.println("时间："+productMap.getIsCreateTime());
        }
        //java8新特性
        //获取分组id
        Map<Integer,List<UserProductMap>> productIdKey = userProductMaps.stream().collect(Collectors.groupingBy(UserProductMap::getProductId));

        List<String> legend = new ArrayList<>();
        for (Integer integer : productIdKey.keySet()) {
            legend.add(productIdKey.get(integer).get(0).getProductName());
        }
        map.put("legend",legend);
        //获取日期
        Map<String,List<UserProductMap>> createTimeKey = userProductMaps.stream().collect(Collectors.groupingBy(UserProductMap::getIsCreateTime));
        List<String> xAxis = new ArrayList<>();
        List<String> createTime = new ArrayList<>();
        for (String s : createTimeKey.keySet()) {
            xAxis.add(createTimeKey.get(s).get(0).getWeekCreateTime());
            createTime.add(createTimeKey.get(s).get(0).getIsCreateTime());
        }
        map.put("xAxis",xAxis);
        map.put("createTime",createTime);
        List<Map<String,Object>> series = new ArrayList<>();
        for (Integer integer : productIdKey.keySet()) {
            Map<String,Object> product1 = new HashMap<>();
            product1.put("name",productIdKey.get(integer).get(0).getProductName());
            List<Object> xl = new ArrayList<>();
            for (String istime : createTimeKey.keySet()) {
                int sum = 0;
                List<UserProductMap> collect = userProductMaps.stream().filter(userProductMap1 -> userProductMap1.getProductId()==integer && userProductMap1.getIsCreateTime().equals(istime)).distinct().collect(Collectors.toList());
                for (UserProductMap productMap : collect) {
                    sum+=productMap.getPoint();
                }
                xl.add(sum);
            }
            product1.put("data",xl);
            product1.put("type","bar");
            System.out.println("xl:"+xl.toString());
            series.add(product1);
        }
        map.put("series",series);
        System.out.println("map:"+map.toString());
        /*List<UserProductMap> collect = userProductMaps.stream().filter(userProductMap1 -> userProductMap1.getProductId()==10 && userProductMap1.getIsCreateTime().equals("2018-11-27")).distinct().collect(Collectors.toList());
        for (UserProductMap productMap : collect) {
            System.out.println("productMap:"+productMap);
        }*/

        return map;
    }

    /**
     * 获取所有
     * @return
     */
    @Override
    public UserProductMapExecution getUserProductMap(Integer ShopId) {
        UserProductMapExecution execution = new UserProductMapExecution();
        UserProductMapExample example = new UserProductMapExample();
        UserProductMapExample.Criteria criteria = example.createCriteria();
        criteria.andShopIdEqualTo(ShopId);
        List<UserProductMap> userProductMaps = userProductMapMapper.selectByExample(example);
        execution.setState(UserProductMapStateEnum.SUCCESS.getState());
        execution.setUserProductMapList(userProductMaps);
        return execution;
    }
}
