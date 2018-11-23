package cn.shop.potal.service.impl;

import cn.shop.mapper.ShopMapper;
import cn.shop.pojo.Shop;
import cn.shop.pojo.ShopExample;
import cn.shop.potal.service.ShopServicePotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServicePotalServiceImpl implements ShopServicePotalService{
    @Autowired
    ShopMapper shopMapper;

    public List<Shop> queryShopList(Shop shopCondition, Integer rowIndex, Integer pageSize) {
        if (rowIndex != null && pageSize != null) {
            return shopMapper.queryShopList(shopCondition, rowIndex, pageSize);
        } else {
            return null;
        }

    }

    /**
     * @Description: 根据条件：店铺名（模糊），店铺状态，店铺Id,店铺类别,区域ID查出总行数
     * @Author: oy
     * @CreateDate: 2018/11/22 0022 上午 8:59
     */
    public long countByExample(Shop shop) {
        ShopExample shopExample=new ShopExample();
        ShopExample.Criteria criteria=shopExample.createCriteria();
        //店铺状态
        criteria.andEnableStatusEqualTo(1);
        //按照shop.getShopId()查询
        if(shop!=null&& shop.getShopId()!=null&&!"".equals(shop.getShopId())){
            criteria.andShopIdEqualTo(shop.getShopId());
        }
        //按照父级商铺类别
        if(shop!=null&&shop.getShopCategory()!=null&&shop.getShopCategory().getParentId()!=null&&!"".equals(shop.getShopCategory().getParentId())&&shop.getShopCategory().getParentId()>-1){
            criteria.andParentCategoryIdEqualTo(shop.getShopCategory().getParentId());
        }
        //按照当前级别
        if(shop!=null&&shop.getShopCategory()!=null&&shop.getShopCategory().getShopCategoryId()!=null&&!"".equals(shop.getShopCategory().getShopCategoryId())&&shop.getShopCategory().getShopCategoryId()>-1){
            criteria.andShopCategoryIdEqualTo(shop.getShopCategory().getShopCategoryId());
        }
        //按照区域查询
        if(shop!=null&&shop.getArea()!=null&&shop.getArea().getAreaId()!=null&&!"".equals(shop.getArea().getAreaId())&&shop.getArea().getAreaId()>-1){
            criteria.andAreaIdEqualTo(shop.getArea().getAreaId());
        }
        //店铺名模糊查询
        if(shop!=null&&shop.getShopName()!=null&&!"".equals(shop.getShopName())){
            criteria.andShopNameLike("%"+shop.getShopName()+"%");
        }
        System.out.println(shop.toString());
        return shopMapper.countByExample(shopExample);
    }
}
