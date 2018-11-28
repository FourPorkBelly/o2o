package cn.shop.shop.service.impl;

import cn.shop.mapper.ShopCategoryMapper;
import cn.shop.pojo.ShopCategory;
import cn.shop.pojo.ShopCategoryExample;
import cn.shop.shop.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zmt
 * @date 2018/11/18 - 1:30
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryMapper shopCategoryMapper;
    //获取商铺类别
    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategory) {
        ShopCategoryExample example = new ShopCategoryExample();
        ShopCategoryExample.Criteria criteria = example.createCriteria();
        return shopCategoryMapper.selectByExample(example);
    }

}
