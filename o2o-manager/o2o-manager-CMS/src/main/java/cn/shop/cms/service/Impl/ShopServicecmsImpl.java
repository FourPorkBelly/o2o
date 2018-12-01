package cn.shop.cms.service.Impl;

import cn.shop.cms.service.ShopServicecms;
import cn.shop.dto.ShopExecution;
import cn.shop.enums.ShopStateEnum;
import cn.shop.mapper.ShopMapper;
import cn.shop.pojo.Shop;
import cn.shop.pojo.ShopExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yzg
 * @date 2018/11/26 - 14:43
 */@Service
public class ShopServicecmsImpl implements ShopServicecms {
    @Autowired
    private ShopMapper shopMapper;

    /**
     * 根据条件查询分页显示相应的店铺信息
     * @param shop
     * @return
     */
    @Override
    public ShopExecution queryShopList(Shop shop, int page, int limit) {
        ShopExample example = new ShopExample();
        ShopExample.Criteria criteria = example.createCriteria();
        if (shop!=null){
            if(StringUtil.isNotEmpty(shop.getShopName())){
                criteria.andShopNameLike("%"+shop.getShopName()+"%");
            }

        }
        ShopExecution shopExecution = new ShopExecution();
        PageHelper.startPage(page,limit);

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
}
