package cn.shop.mapper;

import cn.shop.pojo.ShopCategory;
import cn.shop.pojo.ShopCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShopCategoryMapper {
    long countByExample(ShopCategoryExample example);

    int deleteByExample(ShopCategoryExample example);

    int deleteByPrimaryKey(Integer shopCategoryId);

    int insert(ShopCategory record);

    int insertSelective(ShopCategory record);

    List<ShopCategory> selectByExample(ShopCategoryExample example);

    ShopCategory selectByPrimaryKey(Integer shopCategoryId);

    int updateByExampleSelective(@Param("record") ShopCategory record, @Param("example") ShopCategoryExample example);

    int updateByExample(@Param("record") ShopCategory record, @Param("example") ShopCategoryExample example);

    int updateByPrimaryKeySelective(ShopCategory record);

    int updateByPrimaryKey(ShopCategory record);
}