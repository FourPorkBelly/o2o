package cn.shop.shop.service;

import cn.shop.dto.UserAwardMapExecution;

/**
 * @author zmt
 * @date 2018/12/10 - 1:43
 */
public interface UserAwardService {
    UserAwardMapExecution getUserAwardListByShopId(Integer shopId,Integer pageIndex,Integer pageSize,String awardName);
}
