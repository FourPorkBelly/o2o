package cn.shop.shop.service;

import cn.shop.pojo.ShopCategory;

import java.util.List;

/**
 * @author zmt
 * @date 2018/11/18 - 1:27
 */
public interface ShopCategoryService {

    List<ShopCategory> getShopCategoryList(ShopCategory shopCategory);

}
