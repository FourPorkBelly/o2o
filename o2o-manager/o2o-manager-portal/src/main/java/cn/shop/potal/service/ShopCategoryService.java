package cn.shop.potal.service;

import cn.shop.pojo.ShopCategory;

import java.util.List;

/**
 * @Description:    商品类别
 * @Author:         oy
 * @CreateDate:     2018/11/21 0021 上午 11:23
 */
public interface ShopCategoryService {
    //查询没有父级的商品类别
    List<ShopCategory> getShopCategoryListByparent(ShopCategory shopCategory);


}
