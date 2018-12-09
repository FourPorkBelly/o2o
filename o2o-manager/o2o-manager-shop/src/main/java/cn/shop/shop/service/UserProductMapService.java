package cn.shop.shop.service;

import cn.shop.dto.UserProductMapExecution;
import cn.shop.pojo.UserProductMap;

import java.util.Map;

/**
 * 用户消费记录
 * @author 赵铭涛
 * @creation time 2018/12/9 - 14:54
 */
public interface UserProductMapService {

    Map<String,Object> getUserProductMapByShopId(Integer ShopId,String cxtj);
    UserProductMapExecution getUserProductMap(Integer ShopId);
}
