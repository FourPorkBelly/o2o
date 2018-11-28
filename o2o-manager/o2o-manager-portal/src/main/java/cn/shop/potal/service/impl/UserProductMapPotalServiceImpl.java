package cn.shop.potal.service.impl;

import cn.shop.mapper.UserProductMapMapper;
import cn.shop.pojo.UserProductMap;
import cn.shop.pojo.UserProductMapExample;
import cn.shop.potal.service.UserProductMapPotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:    消费记录
 * @Author:         oy
 * @CreateDate:     2018/11/27 0027 上午 9:33
 */
@Service
public class UserProductMapPotalServiceImpl implements UserProductMapPotalService{
    @Autowired
    UserProductMapMapper userProductMapMapper;
    /**
     * @Description:    新增消费记录
     * @Author:         oy
     * @CreateDate:     2018/11/27 0027 上午 9:45
     */
    @Override
    public int insertSelective(UserProductMap record) {

        return userProductMapMapper.insertSelective(record);
    }
}
