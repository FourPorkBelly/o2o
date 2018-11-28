package cn.shop.potal.service;

import cn.shop.pojo.UserProductMap;
import cn.shop.pojo.UserProductMapExample;

import java.util.List;

/**
 * @Description:   用户消费记录
 * @Author:         oy
 * @CreateDate:     2018/11/28 0028 上午 11:17
 */
public interface UserProductMapPotalService {
    int insertSelective(UserProductMap record);
    List<UserProductMap> selectByExample(UserProductMap userProductMap,Integer pagenum,Integer pageize);
    long countByExample(UserProductMap userProductMap);
}
