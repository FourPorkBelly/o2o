package cn.shop.mapper;

import cn.shop.pojo.UserShopMap;
import cn.shop.pojo.UserShopMapExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserShopMapMapper {
    long countByExample(UserShopMapExample example);

    int deleteByExample(UserShopMapExample example);

    int deleteByPrimaryKey(Integer userShopId);

    int insert(UserShopMap record);

    int insertSelective(UserShopMap record);

    List<UserShopMap> selectByExample(UserShopMapExample example);

    UserShopMap selectByPrimaryKey(Integer userShopId);

    int updateByExampleSelective(@Param("record") UserShopMap record, @Param("example") UserShopMapExample example);

    int updateByExample(@Param("record") UserShopMap record, @Param("example") UserShopMapExample example);

    int updateByPrimaryKeySelective(UserShopMap record);

    int updateByPrimaryKey(UserShopMap record);
}