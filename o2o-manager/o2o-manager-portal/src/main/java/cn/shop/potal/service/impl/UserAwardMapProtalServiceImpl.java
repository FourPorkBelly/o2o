package cn.shop.potal.service.impl;

import cn.shop.mapper.UserAwardMapMapper;
import cn.shop.pojo.UserAwardMap;
import cn.shop.pojo.UserAwardMapExample;
import cn.shop.potal.service.UserAwardMapProtalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserAwardMapProtalServiceImpl implements UserAwardMapProtalService {
    @Autowired
    UserAwardMapMapper userAwardMapMapper;
    /**
     * @Description:    新增积分消费记录
     * @Author:         oy
     * @CreateDate:     2018/11/28 0028 上午 11:28
     */
    @Override
    public int insertSelective(UserAwardMap record) {
        return userAwardMapMapper.insertSelective(record);
    }



}
