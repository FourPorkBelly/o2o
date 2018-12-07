package cn.shop.shop.service.impl;

import cn.shop.dto.ProductCategoryExecution;
import cn.shop.enums.ProductCategoryStateEnum;
import cn.shop.exceptions.OperationException;
import cn.shop.mapper.ProductCategoryMapper;
import cn.shop.pojo.ProductCategory;
import cn.shop.pojo.ProductCategoryExample;
import cn.shop.shop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 商品类别列表
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
        //创建条件查询
        ProductCategoryExample example = new ProductCategoryExample();
        ProductCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andShopIdEqualTo(shopId);
        //执行查询
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
            //补全信息
            for (ProductCategory productCategory : productCategoryList) {
                productCategory.setCreateTime(new Date());
                productCategory.setLastEditTime(new Date());
            }

            try {
                int i = productCategoryMapper.insertList(productCategoryList);
                if (i<=0) {
                    //如果失败则创建自己定义的异常信息
                    throw new OperationException("店铺类别创建失败");
                }else{
                    //成功返回成功状态信息
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                e.printStackTrace();
                //抛出异常信息
                throw new OperationException("error"+e.getMessage());
            }
        }else {
            //信息为空返回错误信息
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }

    }

    /**
     * 删除商品类别信息
     * 将此类别下的商品里的类别id设置为空，再删除掉该商品类别
     * @param shopid
     * @param productCategoryId
     * @return
     */
    @Override
    public ProductCategoryExecution deleteProductCategory(Integer shopid, Integer productCategoryId) {

        //将此类别下的商品里的类别id设置为空

        try {
            //添加删除的条件
            ProductCategoryExample example = new ProductCategoryExample();
            ProductCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andShopIdEqualTo(shopid);
            criteria.andProductCategoryIdEqualTo(productCategoryId);
            //执行删除
            int i = productCategoryMapper.deleteByExample(example);
            //判断是否删除成功
            if (i<=0) {
                throw  new OperationException("商品类别删除失败");
            }else{
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        }catch (Exception e){
            //如果出现错误抛出异常信息
            e.printStackTrace();
            throw new OperationException("商品类别删除 error"+e.getMessage());
        }
    }
}
