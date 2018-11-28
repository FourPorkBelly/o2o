package cn.shop.mapper;

import cn.shop.pojo.Shop;
import cn.shop.pojo.ShopExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShopMapper {
    long countByExample(ShopExample example);

    int deleteByExample(ShopExample example);

    int deleteByPrimaryKey(Integer shopId);

    int insert(Shop record);

    int insertSelective(Shop record);

    List<Shop> selectByExample(ShopExample example);

    List<Shop> selectByEmployeeId(@Param("employeeId") Integer employeeId);

    List<Shop> selectByExampleWidthAreaPersonInfoShopCategory(ShopExample example);

    Shop selectByPrimaryKey(Integer shopId);

    Shop selectByPrimaryKeyWidthAreaPersonInfoShopCategory(Integer shopId);

    int updateByExampleSelective(@Param("record") Shop record, @Param("example") ShopExample example);

    int updateByExample(@Param("record") Shop record, @Param("example") ShopExample example);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);
    //分页查询店铺,可输入的条件有：店铺名（模糊），店铺状态，店铺Id,店铺类别,区域ID
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") Integer rowIndex, @Param("pageSize") Integer pageSize);
}