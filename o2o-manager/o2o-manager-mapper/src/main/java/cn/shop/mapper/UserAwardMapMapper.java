package cn.shop.mapper;

import cn.shop.pojo.UserAwardMap;
import cn.shop.pojo.UserAwardMapExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAwardMapMapper {
    long countByExample(UserAwardMapExample example);

    int deleteByExample(UserAwardMapExample example);

    int deleteByPrimaryKey(Integer userAwardId);

    int insert(UserAwardMap record);

    int insertSelective(UserAwardMap record);

    List<UserAwardMap> selectByExample(UserAwardMapExample example);

    List<UserAwardMap> selectByExampleWithPersonInfoAwardShop(UserAwardMapExample example);

    UserAwardMap selectByPrimaryKeyWithPersonInfoAwardShop(Integer userAwardId);
    UserAwardMap selectByPrimaryKey(Integer userAwardId);

    int updateByExampleSelective(@Param("record") UserAwardMap record, @Param("example") UserAwardMapExample example);

    int updateByExample(@Param("record") UserAwardMap record, @Param("example") UserAwardMapExample example);

    int updateByPrimaryKeySelective(UserAwardMap record);

    int updateByPrimaryKey(UserAwardMap record);
}