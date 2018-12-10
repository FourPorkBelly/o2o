package cn.shop.cms.service.Impl;

import cn.shop.cms.service.HeadLineCMSService;
import cn.shop.mapper.AeadLineMapper;
import cn.shop.pojo.AeadLine;
import cn.shop.pojo.AeadLineExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yzg
 * @date 2018/12/9 - 23:38
 */
@Service
public class HeadLineCMSServiceImpl implements HeadLineCMSService {
    @Autowired
    AeadLineMapper mapper;
    @Override
    public List<AeadLine> queryHeadLine() {
        AeadLineExample example=new AeadLineExample();
        //根据优先级别倒序
        example.setOrderByClause("priority desc");
        AeadLineExample.Criteria criteria=example.createCriteria();
        criteria.andEnableStatusEqualTo(1);
        return mapper.selectByExample(example);
    }
}
