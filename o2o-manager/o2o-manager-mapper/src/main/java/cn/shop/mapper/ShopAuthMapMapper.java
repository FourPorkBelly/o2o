package cn.shop.mapper;

import cn.shop.pojo.ShopAuthMap;
import cn.shop.pojo.ShopAuthMapExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShopAuthMapMapper {
    long countByExample(ShopAuthMapExample example);

    int deleteByExample(ShopAuthMapExample example);

    int deleteByPrimaryKey(Integer shopAuthId);

    int insert(ShopAuthMap record);

    int insertSelective(ShopAuthMap record);

    List<ShopAuthMap> selectByExample(ShopAuthMapExample example);

    ShopAuthMap selectByPrimaryKey(Integer shopAuthId);

    int updateByExampleSelective(@Param("record") ShopAuthMap record, @Param("example") ShopAuthMapExample example);

    int updateByExample(@Param("record") ShopAuthMap record, @Param("example") ShopAuthMapExample example);

    int updateByPrimaryKeySelective(ShopAuthMap record);

    int updateByPrimaryKey(ShopAuthMap record);
}