package cn.shop.shop.service.impl;

import cn.shop.dto.ShopExecution;
import cn.shop.enums.ShopStateEnum;
import cn.shop.mapper.ShopMapper;
import cn.shop.pojo.Shop;
import cn.shop.pojo.ShopExample;
import cn.shop.shop.service.ShopService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public Shop getByShopId(Integer shopId) {
        return shopMapper.selectByPrimaryKeyWidthAreaPersonInfoShopCategory(shopId);
    }
    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    @Override
    public ShopExecution updateShop(Shop shop) {
        try {
            shopMapper.updateByPrimaryKeySelective(shop);
            shop = getByShopId(shop.getShopId());
            return new ShopExecution(ShopStateEnum.SUCCESS,shop);
        }catch (Exception e){
            return new ShopExecution(ShopStateEnum.INNER_ERROR);
        }
    }

    /**
     * 根据条件查询分页显示相应的店铺信息
     * @param shop
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ShopExecution getShopList(Shop shop, int pageIndex, int pageSize) {
        ShopExample example = new ShopExample();
        ShopExample.Criteria criteria = example.createCriteria();
        if(shop.getShopName()!=null){
            criteria.andShopNameLike("%"+shop.getShopName()+"%");
        }
        if(shop.getEnableStatus()!=null){
            criteria.andEnableStatusEqualTo(shop.getEnableStatus());
        }
        if(shop.getParentCategoryId()!=null){
            criteria.andParentCategoryIdEqualTo(shop.getParentCategoryId());
        }
        if (shop.getAreaId()!=null){
            criteria.andAreaIdEqualTo(shop.getAreaId());
        }
        if(shop.getOwnerId()!=null){
            criteria.andOwnerIdEqualTo(shop.getOwnerId());
        }
        ShopExecution shopExecution = new ShopExecution();
        PageHelper.startPage(pageIndex,pageSize);

        List<Shop> list = shopMapper.selectByExampleWidthAreaPersonInfoShopCategory(example);

        PageInfo pageInfo = new PageInfo(list);
        if (list!=null&&list.size()>0) {
            shopExecution.setShopList(list);
            shopExecution.setCount((int)pageInfo.getTotal());
        }else {
            shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        }

        return shopExecution;
    }
    /**
     * 根据employeeId查询商铺信息
     * @param employeeId
     * @return
     */
    @Override
    public ShopExecution getByEmployeeId(Integer employeeId) {
        return null;
    }
}
