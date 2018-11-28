package cn.shop.potal.service;

import cn.shop.pojo.UserShopMap;
import cn.shop.pojo.UserShopMapExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @Description:    用户积分
 * @Author:         oy
 * @CreateDate:     2018/11/28 0028 上午 11:25
 */
public interface UserShopMapPotalService {
    /**
     * @Description:    根据用户id和商品id查询积分
     * @Author:         oy
     * @CreateDate:     2018/11/26 0026 下午 3:53
     */
    List<UserShopMap> selectByExample(Integer userShopId, Integer shopid,Integer pagenum,Integer pagesize);
    int insertSelective(UserShopMap record);
    int updateByExample( UserShopMap record);
    long countByExample(Integer integer);
}
