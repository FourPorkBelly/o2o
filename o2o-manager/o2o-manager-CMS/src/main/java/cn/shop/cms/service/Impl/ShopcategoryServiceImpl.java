package cn.shop.cms.service.Impl;

import cn.shop.cms.service.ShopcategoryService;
import cn.shop.dto.ShopCategoryExecution;
import cn.shop.enums.ShopStateEnum;
import cn.shop.mapper.ShopCategoryMapper;
import cn.shop.pojo.ShopCategory;
import cn.shop.pojo.ShopCategoryExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yzg
 * @date 2018/12/7 - 16:12
 */
@Service
public class ShopcategoryServiceImpl implements ShopcategoryService {
    @Autowired
    private ShopCategoryMapper shopCategoryMapper;
    //获取一级商铺类别
    @Override
    public List<ShopCategory> getShopCategoryList() {
        ShopCategoryExample example = new ShopCategoryExample();
        ShopCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdIsNull();
        return shopCategoryMapper.selectByExample(example);
    }
    //获取二级商铺类别
    @Override
    public ShopCategoryExecution getShopCategoryList2(int page,int limit) {
        ShopCategoryExecution execution=new ShopCategoryExecution();
        ShopCategoryExample example = new ShopCategoryExample();
        ShopCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdIsNotNull();
        PageHelper.startPage(page,limit);
        List<ShopCategory> list = shopCategoryMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        if (list!=null&&list.size()>0) {
            execution.setShopCategoryList(list);
            execution.setCount((int)pageInfo.getTotal());
        }else {
            execution.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return execution;
    }

    @Override
    public ShopCategory getShopCategoryById(int shopCategoryId) {
        ShopCategory shopCategory = shopCategoryMapper.selectByPrimaryKey(shopCategoryId);
        if (shopCategory!=null){
            return shopCategory;
        }
        return null;
    }

    @Override
    public int updateShopCategory(ShopCategory shopCategory) {
        int i = shopCategoryMapper.updateByPrimaryKeySelective(shopCategory);
        return i;
    }
}
