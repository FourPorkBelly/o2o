package cn.shop.potal.service;

import cn.shop.pojo.Product;

import cn.shop.pojo.ProductExample;


import java.util.List;

public interface ProductPotalService {
    List<Product> selectByExample(Product product, Integer pageNum,Integer pagesize);
    Long countByExample(Product product);
    Product selectByPrimaryKey(Integer productId);
}
