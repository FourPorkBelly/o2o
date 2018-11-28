package cn.shop.potal.service.impl;

import cn.shop.mapper.ShopCategoryMapper;
import cn.shop.pojo.ShopCategory;
import cn.shop.pojo.ShopCategoryExample;

import cn.shop.potal.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 商品类别
 * @Author: oy
 * @CreateDate: 2018/11/21 0021 上午 11:24
 */
@Service
public class ShopCategoryPotalServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryMapper shopCategoryMapper;

    //查询没有父级的商品类别
    @Override
    public List<ShopCategory> getShopCategoryListByparent(ShopCategory shopCategory) {
        ShopCategoryExample example = new ShopCategoryExample();
        ShopCategoryExample.Criteria criteria = example.createCriteria();
        //为空时查询一级分类
        if (shopCategory == null) {
            criteria.andParentIdIsNull();
        } else {
            //否则查询父类为ParentId的子类
            criteria.andParentIdEqualTo(shopCategory.getParentId());
        }
        return shopCategoryMapper.selectByExample(example);
    }

}
