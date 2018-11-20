package cn.shop.shop.service.impl;

import cn.shop.dto.ProductCategoryExecution;
import cn.shop.enums.ProductCategoryStateEnum;
import cn.shop.mapper.ProductCategoryMapper;
import cn.shop.pojo.ProductCategory;
import cn.shop.pojo.ProductCategoryExample;
import cn.shop.shop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 赵铭涛
 * @creation time 2018/11/20 - 8:48
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    ProductCategoryMapper productCategoryMapper;

    /**
     * 根据shopId获取商品类别列表
     * @param shopId
     * @return
     */
    @Override
    public List<ProductCategory> getProductCategoryList(Integer shopId) {
        ProductCategoryExample example = new ProductCategoryExample();
        ProductCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        List<ProductCategory> productCategoryList = productCategoryMapper.selectByExample(example);
        return productCategoryList;
    }

    /**
     * 批量添加
     * @param productCategoryList
     * @return
     */
    @Override
    public ProductCategoryExecution addProductCategoryList(List<ProductCategory> productCategoryList) {
        //判断信息是否为空
        if (productCategoryList!=null&&productCategoryList.size()>0) {
            try {
                int i = productCategoryMapper.insertList(productCategoryList);
                if (i<=0) {
                    /*throw new ProductCategoryExecution(ProductCategoryStateEnum.INNER_ERROR.getState()"");*/
                }
            }catch (Exception e){

            }
        }
        return null;
    }
}
