package cn.shop.shop.service;

import cn.shop.dto.O2oExecution;

import java.util.List;

/**
 * @author 赵铭涛
 * @creation time 2018/11/29 - 15:51
 */
public interface ProductImgService {

    /**
     * 通过商品id获取图片集合
     * @param productId
     * @return
     */
    List<ProductImgService> getProductImgByProductId(Integer productId);

    /**
     * 批量新增图片
     * @param imgs
     * @return
     */
    O2oExecution addProductImgs(String imgs);
}
