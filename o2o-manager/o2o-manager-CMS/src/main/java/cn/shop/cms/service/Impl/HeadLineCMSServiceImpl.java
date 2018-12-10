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
public class HeadLineCMSServiceImpl implements HeadLineCMSService
{
    @Autowired
    AeadLineMapper mapper;

    /**
     * 查询头条信息
     * @return
     */
    @Override
    public List<AeadLine> queryHeadLine() {
        AeadLineExample example=new AeadLineExample();
        //根据优先级别倒序
        example.setOrderByClause("priority desc");
        AeadLineExample.Criteria criteria=example.createCriteria();
        criteria.andEnableStatusEqualTo(1);
//        返回查询
        return mapper.selectByExample(example);
    }

    /**
     * 根据headlinid获取AeadLine对象
     * @param headLineById
     * @return
     */
    @Override
    public AeadLine getHeadLineById(int headLineById) {
        AeadLine aeadLine = mapper.selectByPrimaryKey(headLineById);
//        如果返回对象不为空则返回对象
        if (aeadLine!=null){
            return aeadLine;
        }
        return null;
    }

    /**
     * 修改头条信息
     * @param aeadLine
     * @return
     */
    @Override
    public int updateHeadLine(AeadLine aeadLine) {
        int i = mapper.updateByPrimaryKeySelective(aeadLine);
        return i;
    }
}
