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

    /**
     * 得到以及商铺类别
     * @return
     */
    @Override
    public List<ShopCategory> getShopCategoryList() {
        ShopCategoryExample example = new ShopCategoryExample();
        ShopCategoryExample.Criteria criteria = example.createCriteria();
        //        查询ParentId为空的ShopCategory
        criteria.andParentIdIsNull();
        return shopCategoryMapper.selectByExample(example);
    }
    /**
     * 得到二级商铺类别
     * @param page
     * @param limit
     * @return
     */
    @Override
    public ShopCategoryExecution getShopCategoryList2(int page,int limit) {
        ShopCategoryExecution execution=new ShopCategoryExecution();
        ShopCategoryExample example = new ShopCategoryExample();
        ShopCategoryExample.Criteria criteria = example.createCriteria();
//        查询ParentId不为空的ShopCategory
        criteria.andParentIdIsNotNull();
//        分页
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

    /**
     * 根据商铺类别id获取商铺类别对象
     * @param shopCategoryId
     * @return
     */
    @Override
    public ShopCategory getShopCategoryById(int shopCategoryId) {
        ShopCategory shopCategory = shopCategoryMapper.selectByPrimaryKey(shopCategoryId);
//        如果商铺查询的类别不为空则返回shopCategory对象
        if (shopCategory!=null){
            return shopCategory;
        }
        return null;
    }

    /**
     * 修改商铺类别信息
     * @param shopCategory
     * @return
     */
    @Override
    public int updateShopCategory(ShopCategory shopCategory) {
        int i = shopCategoryMapper.updateByPrimaryKeySelective(shopCategory);
        return i;
    }
}
