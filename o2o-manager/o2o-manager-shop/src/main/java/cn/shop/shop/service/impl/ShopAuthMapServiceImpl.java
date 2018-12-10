package cn.shop.shop.service.impl;

import cn.shop.dto.ShopAuthMapExecution;
import cn.shop.enums.ShopAuthMapStateEnum;
import cn.shop.mapper.ShopAuthMapMapper;
import cn.shop.pojo.ShopAuthMap;
import cn.shop.pojo.ShopAuthMapExample;
import cn.shop.shop.service.ShopAuthMapService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zmt
 * @date 2018/11/22 - 4:24
 */
@Service
public class ShopAuthMapServiceImpl implements ShopAuthMapService {
    @Autowired
    ShopAuthMapMapper shopAuthMapMapper;
    /**
     * 根据商铺id分页显示该店铺的授权信息
     * @param shopId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ShopAuthMapExecution getShopAuthMapList(Integer shopId, Integer pageIndex, Integer pageSize,Integer userId) {
        //添加分页信息
        PageHelper.startPage(pageIndex,pageSize);
        //返回值
        ShopAuthMapExecution sme = new ShopAuthMapExecution();
        //添加条件
        ShopAuthMapExample example = new ShopAuthMapExample();
        ShopAuthMapExample.Criteria criteria = example.createCriteria();
        //根据shopid进行查询
        criteria.andShopIdEqualTo(shopId);
        criteria.andEmployeeIdNotEqualTo(userId);
        List<ShopAuthMap> list = shopAuthMapMapper.selectByExampleWithPersonInfoShop(example);
        //判断返回结果是否为空
        if(list==null&&list.size()==0){
            sme.setState(ShopAuthMapStateEnum.INNER_ERROR.getState());
        }else{
            //进行分页
            PageInfo pageInfo = new PageInfo(list);
            sme.setShopAuthMapList(list);
            sme.setCount((int)pageInfo.getTotal());
            sme.setState(ShopAuthMapStateEnum.SUCCESS.getState());
        }
        return sme;
    }
    /**
     * 根据shopAuthId返回对应的授权信息
     * @param shopAuthId
     * @return
     */
    @Override
    public ShopAuthMap getShopAuthMapById(Integer shopAuthId) {
        ShopAuthMap shopAuthMap = shopAuthMapMapper.selectByPrimaryKeyWithPersonInfoShop(shopAuthId);
        return shopAuthMap;
    }
    /**
     * 添加授权信息
     * @param shopAuthMap
     * @return
     */
    @Override
    public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) {
        //判断空值，对商铺id和员工id做校验
        if(shopAuthMap!=null&&shopAuthMap.getShopId()!=null&&shopAuthMap.getEmployeeId()!=null){
            //补全信息
            shopAuthMap.setCreateTime(new Date());
            shopAuthMap.setLastEditTime(new Date());
            shopAuthMap.setEnableStatus(1);
            shopAuthMap.setTitleFlag(0);
            try {
                //添加授权信息
                int i = shopAuthMapMapper.insertSelective(shopAuthMap);
                if(i<=0){
                    return new ShopAuthMapExecution(ShopAuthMapStateEnum.INNER_ERROR);
                }
                return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS);
            }catch (Exception e){
                e.printStackTrace();
                return new ShopAuthMapExecution(ShopAuthMapStateEnum.INNER_ERROR);
            }
        }else{
            return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
        }
    }
    /**
     * 更新授权信息，包括职位，状态等
     * @param shopAuthMap
     * @return
     */
    @Override
    public ShopAuthMapExecution updateShopAuthMap(ShopAuthMap shopAuthMap) {
        //补全信息
        shopAuthMap.setLastEditTime(new Date());
        //返回值
        try {
            int i = shopAuthMapMapper.updateByPrimaryKeySelective(shopAuthMap);
            if(i>0){
                return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS);
            }else{
                return new ShopAuthMapExecution(ShopAuthMapStateEnum.INNER_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ShopAuthMapExecution(ShopAuthMapStateEnum.INNER_ERROR);
        }
    }
}
