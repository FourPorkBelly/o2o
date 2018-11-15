package cn.shop.mapper;

import cn.shop.pojo.AeadLine;
import cn.shop.pojo.AeadLineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AeadLineMapper {
    long countByExample(AeadLineExample example);

    int deleteByExample(AeadLineExample example);

    int deleteByPrimaryKey(Integer lineId);

    int insert(AeadLine record);

    int insertSelective(AeadLine record);

    List<AeadLine> selectByExample(AeadLineExample example);

    AeadLine selectByPrimaryKey(Integer lineId);

    int updateByExampleSelective(@Param("record") AeadLine record, @Param("example") AeadLineExample example);

    int updateByExample(@Param("record") AeadLine record, @Param("example") AeadLineExample example);

    int updateByPrimaryKeySelective(AeadLine record);

    int updateByPrimaryKey(AeadLine record);
}