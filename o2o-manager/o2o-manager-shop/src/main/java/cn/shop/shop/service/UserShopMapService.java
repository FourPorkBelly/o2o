package cn.shop.shop.service;

import cn.shop.dto.UserShopMapExecution;

/**
 * @author zmt
 * @date 2018/12/10 - 2:16
 */
public interface UserShopMapService {

    UserShopMapExecution getUserShopMapListByShopIdAndUserName(Integer pageIndex,Integer pageSize,Integer shopId,String userName);
}
