package cn.shop.shop.service.impl;

import cn.shop.dto.ProductExecution;
import cn.shop.enums.ProductStateEnum;
import cn.shop.mapper.ProductMapper;
import cn.shop.pojo.Product;
import cn.shop.pojo.ProductExample;
import cn.shop.shop.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yzg
 * @date 2018/11/19 - 11:28
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    /**
     * 查询所有商品
     * @return
     */
    @Override
    public ProductExecution queryProduct(Product productCondition) {
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        if (productCondition.getShopId()!=null){
            criteria.andShopIdEqualTo(productCondition.getShopId());
        }
        PageHelper.startPage(1, 999);

        List<Product> productList = productMapper.selectByExample(example);

        ProductExecution pe = new ProductExecution();

        PageInfo<Product> plist=new PageInfo<>(productList);
        pe.setProductList(plist);
        pe.setCount((int)plist.getTotal());
        return pe;
    }

    @Override
    public ProductExecution addProduct(Product product) {
        if (product != null  && product.getShopId() != null) {
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            try {
                int effectedNum = productMapper.insertSelective(product);
                if (effectedNum <= 0) {
                    throw new RuntimeException("创建商品失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("创建商品失败:" + e.toString());
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

}
