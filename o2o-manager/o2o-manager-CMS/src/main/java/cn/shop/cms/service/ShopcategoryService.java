package cn.shop.cms.service;

import cn.shop.dto.ShopCategoryExecution;
import cn.shop.pojo.ShopCategory;

import java.util.List;

/**
 * @author yzg
 * @date 2018/12/7 - 15:53
 */
public interface ShopcategoryService {
    /**
     * 得到以及商铺类别
     * @return
     */
    List<ShopCategory> getShopCategoryList();

    /**
     * 得到二级商铺类别
     * @param page
     * @param limit
     * @return
     */
    ShopCategoryExecution getShopCategoryList2(int page,int limit);

    /**
     * 通过id得到商铺类别对象
     * @param shopCategoryId
     * @return
     */
    ShopCategory getShopCategoryById(int shopCategoryId);

    /**
     * 修改商铺类别信息
     * @param shopCategory
     * @return
     */
    int updateShopCategory(ShopCategory shopCategory);
}
