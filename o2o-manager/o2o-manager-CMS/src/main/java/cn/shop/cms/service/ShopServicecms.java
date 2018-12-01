package cn.shop.cms.service;

import cn.shop.dto.ShopExecution;
import cn.shop.pojo.Shop;

/**
 * @author yzg
 * @date 2018/11/26 - 14:42
 */
public interface ShopServicecms {
    ShopExecution queryShopList(Shop shop, int page, int limit);
}
