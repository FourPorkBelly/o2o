package cn.shop.shop.service;

import java.util.List;

import cn.shop.dto.ProductCategoryExecution;
import cn.shop.pojo.ProductCategory;
/**
 * @author 赵铭涛
 * @creation time 2018/11/20 - 8:45
 */
public interface ProductCategoryService {

    /**
     * 通过shopid查询商品类别信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(Integer shopId);

    /**
     * 批量添加
     * @param productCategoryList
     * @return
     */
    ProductCategoryExecution addProductCategoryList(List<ProductCategory> productCategoryList);
}
