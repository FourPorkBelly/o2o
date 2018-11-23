package cn.shop.potal.service;

import cn.shop.pojo.Shop;
import cn.shop.pojo.ShopExample;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ShopServicePotalService {
    /**
     * @Description:    分页查询店铺,可输入的条件有：店铺名（模糊），店铺状态，店铺Id,店铺类别,区域ID
     * @Author:         oy
     * @CreateDate:     2018/11/21 0021 下午 1:39
     */
    List<Shop> queryShopList(Shop shopCondition, Integer rowIndex, Integer pageSize);
    /**
     * @Description:    根据条件：店铺名（模糊），店铺状态，店铺Id,店铺类别,区域ID查出总行数
     * @Author:         oy
     * @CreateDate:     2018/11/22 0022 上午 8:59
     */
    long countByExample(Shop shop);
}
