package cn.shop.shop.service;


import cn.shop.dto.ProductExecution;
import cn.shop.pojo.Product;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

/**
 * @author yzg
 * @date 2018/11/19 - 11:25
 */
public interface ProductService {
    /**
     * 查询所有商品
     * @return
     */
    ProductExecution queryProduct(Product productCondition);

    /**
     * 添加商品信息
     * @param product
     * @param
     * @return
     */
    ProductExecution addProduct(Product product,String imgAddrs);

    /**
     * 根据id获取商品信息
     * @param productId
     * @return
     */
    Product getProductById(int productId);

    /**
     * 修改商品信息
     * @param product
     * @return
     */
    Product updateProduct(Product product,String imgAddrs);
}
