package cn.shop.mapper;

import cn.shop.pojo.PersonInfo;
import cn.shop.pojo.PersonInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PersonInfoMapper {
    long countByExample(PersonInfoExample example);

    int deleteByExample(PersonInfoExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(PersonInfo record);

    int insertUserId(PersonInfo record);

    int insertSelective(PersonInfo record);

    List<PersonInfo> selectByExample(PersonInfoExample example);

    PersonInfo selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") PersonInfo record, @Param("example") PersonInfoExample example);

    int updateByExample(@Param("record") PersonInfo record, @Param("example") PersonInfoExample example);

    int updateByPrimaryKeySelective(PersonInfo record);

    int updateByPrimaryKey(PersonInfo record);
    List<PersonInfo> queryUserInfo(@Param("person")PersonInfo personInfo,@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize);
}