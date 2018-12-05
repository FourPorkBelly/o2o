package cn.shop.shop.service;

import cn.shop.dto.ShopAuthMapExecution;
import cn.shop.pojo.ShopAuthMap;

/**
 * @author 赵铭涛
 * @creation time 2018/11/21 - 13:58
 */
public interface ShopAuthMapService {
    /**
     * 根据商铺id分页显示该店铺的授权信息
     * @param shopId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopAuthMapExecution getShopAuthMapList(Integer shopId,Integer pageIndex,Integer pageSize);

    /**
     * 根据shopAuthId返回对应的授权信息
     * @param shopAuthId
     * @return
     */
    ShopAuthMap getShopAuthMapById(Integer shopAuthId);

    /**
     * 添加授权信息
     * @param shopAuthMap
     * @return
     */
    ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap);

    /**
     * 更新授权信息，包括职位，状态等
     * @param shopAuthMap
     * @return
     */
    ShopAuthMapExecution updateShopAuthMap(ShopAuthMap shopAuthMap);
}
