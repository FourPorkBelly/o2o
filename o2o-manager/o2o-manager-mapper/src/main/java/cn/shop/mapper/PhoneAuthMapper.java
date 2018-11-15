package cn.shop.mapper;

import cn.shop.pojo.PhoneAuth;
import cn.shop.pojo.PhoneAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PhoneAuthMapper {
    long countByExample(PhoneAuthExample example);

    int deleteByExample(PhoneAuthExample example);

    int deleteByPrimaryKey(Integer phoneAuthId);

    int insert(PhoneAuth record);

    int insertSelective(PhoneAuth record);

    List<PhoneAuth> selectByExample(PhoneAuthExample example);

    PhoneAuth selectByPrimaryKey(Integer phoneAuthId);

    int updateByExampleSelective(@Param("record") PhoneAuth record, @Param("example") PhoneAuthExample example);

    int updateByExample(@Param("record") PhoneAuth record, @Param("example") PhoneAuthExample example);

    int updateByPrimaryKeySelective(PhoneAuth record);

    int updateByPrimaryKey(PhoneAuth record);
}