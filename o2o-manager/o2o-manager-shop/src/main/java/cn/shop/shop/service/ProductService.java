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
    ProductExecution addProduct(Product product);
}
