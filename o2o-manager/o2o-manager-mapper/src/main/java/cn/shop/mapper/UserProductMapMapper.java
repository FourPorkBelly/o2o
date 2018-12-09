package cn.shop.mapper;

import cn.shop.pojo.Shop;
import cn.shop.pojo.UserProductMap;
import cn.shop.pojo.UserProductMapExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserProductMapMapper {
    long countByExample(UserProductMapExample example);

    int deleteByExample(UserProductMapExample example);

    int deleteByPrimaryKey(Integer userProductId);

    int insert(UserProductMap record);

    int insertSelective(UserProductMap record);

    List<UserProductMap> selectByExample(UserProductMapExample example);

    List<UserProductMap> selectByShopIdConcat(@Param("shopId") Integer shopId,@Param("cxtj") String cxtj);

    List<UserProductMap> selectByExampleWhitPersonInfoProductShop(UserProductMapExample example);

    UserProductMap selectByPrimaryKey(Integer userProductId);

    UserProductMap selectByPrimaryKeyWhitPersonInfoProductShop(Integer userProductId);

   /* UserProductMap selectGroupByProductId(@Param("shopId")Integer shopId);

    UserProductMap selectGroupByCreateTime(@Param("shopId")Integer shopId);

    UserProductMap selectByProduct(UserProductMap userProductMap);*/

    int updateByExampleSelective(@Param("record") UserProductMap record, @Param("example") UserProductMapExample example);

    int updateByExample(@Param("record") UserProductMap record, @Param("example") UserProductMapExample example);

    int updateByPrimaryKeySelective(UserProductMap record);

    int updateByPrimaryKey(UserProductMap record);
}