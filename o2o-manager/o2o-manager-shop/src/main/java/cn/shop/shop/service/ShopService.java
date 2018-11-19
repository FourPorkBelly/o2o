package cn.shop.shop.service;

import cn.shop.dto.ShopExecution;
import cn.shop.pojo.Shop;

import java.io.File;

/**
 * 店铺信息
 * @author zmt
 * @date 2018/11/17 - 21:23
 */
public interface ShopService {

    /**
     * 注册店铺信息
     * @param shop
     * @return
     */
    ShopExecution addShop(Shop shop);

    /**
     * 通过店铺id获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(Integer shopId);

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    ShopExecution modifyShop(Shop shop);

    /**
     *根据条件查询分页显示相应的店铺信息
     * @param shop
     * @return
     */
    ShopExecution getShopList(Shop shop,int pageIndex,int pageSize);
}
