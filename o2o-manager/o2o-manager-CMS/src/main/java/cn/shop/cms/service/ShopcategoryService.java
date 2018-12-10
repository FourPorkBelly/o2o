package cn.shop.cms.service;

import cn.shop.dto.ShopCategoryExecution;
import cn.shop.pojo.ShopCategory;

import java.util.List;

/**
 * @author yzg
 * @date 2018/12/7 - 15:53
 */
public interface ShopcategoryService {
    List<ShopCategory> getShopCategoryList();
    ShopCategoryExecution getShopCategoryList2(int page,int limit);
    ShopCategory getShopCategoryById(int shopCategoryId);
    int updateShopCategory(ShopCategory shopCategory);
}
