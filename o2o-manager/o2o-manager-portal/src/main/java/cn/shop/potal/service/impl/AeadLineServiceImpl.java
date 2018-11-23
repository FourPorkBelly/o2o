package cn.shop.potal.service.impl;

import cn.shop.mapper.AeadLineMapper;
import cn.shop.pojo.AeadLine;
import cn.shop.pojo.AeadLineExample;

import cn.shop.potal.service.AeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @Description:    头条信息
 * @Author:         oy
 * @CreateDate:     2018/11/21 0021 上午 9:29
 */
@Service
public class AeadLineServiceImpl implements AeadLineService {
    @Autowired
    AeadLineMapper aeadLineMapper;
    //查询所有头条信息
    @Override
    public List<AeadLine> queryAeadLine(AeadLine aeadLine) {
        AeadLineExample example=new AeadLineExample();
        //根据优先级别倒序
        example.setOrderByClause("priority desc");
        AeadLineExample.Criteria criteria=example.createCriteria();
        //根据状态查询
        criteria.andEnableStatusEqualTo(aeadLine.getEnableStatus());
        return aeadLineMapper.selectByExample(example);
    }


}
