package cn.shop.cms.service;

import cn.shop.dto.ShopExecution;
import cn.shop.pojo.Shop;

/**
 * @author yzg
 * @date 2018/11/26 - 14:42
 */
public interface ShopServicecms {
    /**
     * 查询已审核的商铺
     * @param shop
     * @param page
     * @param limit
     * @return
     */
    ShopExecution queryShopList(Shop shop, int page, int limit);

    /**
     * 查询待审核的店铺
     * @param shop
     * @param page
     * @param limit
     * @return
     */
    ShopExecution queryAuditShopList(Shop shop, int page, int limit);

    /**
     * 审核通过
     * @param shop
     * @return
     */
    int passShop(Shop shop);
}
