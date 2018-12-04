package cn.shop.potal.service.impl;

import cn.shop.mapper.UserAwardMapMapper;
import cn.shop.pojo.UserAwardMap;
import cn.shop.pojo.UserAwardMapExample;
import cn.shop.potal.service.UserAwardMapProtalService;
import com.github.pagehelper.PageHelper;
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
    /**
     * @Description:   查询用户的积分消费记录行数
     * @Author:         oy
     * @CreateDate:     2018/11/29 0029 上午 8:39
     */
    @Override
    public long countByExample(Integer userid) {
        UserAwardMapExample userAwardMapExample=new UserAwardMapExample();
        UserAwardMapExample.Criteria criteria=userAwardMapExample.createCriteria();
        criteria.andUserIdEqualTo(userid);
        return userAwardMapMapper.countByExample(userAwardMapExample);
    }
    /**
     * @Description:    查询用户的积分消费 加分页
     * @Author:         oy
     * @CreateDate:     2018/11/29 0029 上午 8:40
     */
    @Override
    public List<UserAwardMap> selectByExample(Integer userid,Integer pagenum,Integer pagesize) {
        UserAwardMapExample userAwardMapExample=new UserAwardMapExample();
        UserAwardMapExample.Criteria criteria=userAwardMapExample.createCriteria();
        criteria.andUserIdEqualTo(userid);
        if(pagenum!=null&&pagenum>-1&&pagesize!=null&&pagesize>-1){
            PageHelper.startPage(pagenum,pagesize);
        }
        return userAwardMapMapper.selectByExample(userAwardMapExample);
    }
}
