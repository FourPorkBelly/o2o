package cn.shop.shop.service.impl;

import cn.shop.dto.ShopExecution;
import cn.shop.enums.ShopStateEnum;
import cn.shop.mapper.ShopMapper;
import cn.shop.pojo.Shop;
import cn.shop.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zmt
 * @date 2018/11/17 - 22:30
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopMapper shopMapper;

    /**
     * 添加店铺
     * @param shop
     * @return
     */
    @Override
    public ShopExecution addShop(Shop shop) {
        //判断是否为空
        if(shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP_INFO);
        }
        try {
            shop.setEnableStatus(0);//0未上架
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加用户（测试用）
            shop.setOwnerId(9);
            //添加店铺信息
            shopMapper.insert(shop);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("addShop error:"+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }
    /**
     * 通过店铺id获取店铺信息
     * @param shopId
     * @return
     */
    @Override
    public Shop getByShopId(long shopId) {
        return null;
    }
    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    @Override
    public ShopExecution modifyShop(Shop shop) {
        return null;
    }
}
