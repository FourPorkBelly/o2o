package cn.shop.mapper;

import cn.shop.pojo.WechatAuth;
import cn.shop.pojo.WechatAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WechatAuthMapper {
    long countByExample(WechatAuthExample example);

    int deleteByExample(WechatAuthExample example);

    int deleteByPrimaryKey(Integer wechatAuthId);

    int insert(WechatAuth record);

    int insertSelective(WechatAuth record);

    List<WechatAuth> selectByExample(WechatAuthExample example);

    List<WechatAuth> selectByExampleWithPersonInfo(WechatAuthExample example);

    WechatAuth selectByPrimaryKey(Integer wechatAuthId);

    int updateByExampleSelective(@Param("record") WechatAuth record, @Param("example") WechatAuthExample example);

    int updateByExample(@Param("record") WechatAuth record, @Param("example") WechatAuthExample example);

    int updateByPrimaryKeySelective(WechatAuth record);

    int updateByPrimaryKey(WechatAuth record);
}