package cn.shop.potal.service.impl;

import cn.shop.mapper.UserProductMapMapper;
import cn.shop.pojo.UserProductMap;
import cn.shop.pojo.UserProductMapExample;
import cn.shop.potal.service.UserProductMapPotalService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    /**
     * @Description:    根据用户id查询用户消费记录 以及分页
     * @Author:         oy
     * @CreateDate:     2018/11/28 0028 下午 1:37
     */
    @Override
    public List<UserProductMap> selectByExample(UserProductMap userProductMap,Integer pagenum,Integer pagesize) {
        UserProductMapExample userProductMapExample = new UserProductMapExample();
        UserProductMapExample.Criteria criteria=userProductMapExample.createCriteria();
        if(userProductMap.getUserId()!=null&&userProductMap.getUserId()>-1){
            //根据用户ID
            criteria.andUserIdEqualTo(userProductMap.getUserId());
        }

        if(pagenum!=null&&pagenum>-1&&pagesize!=null&&pagesize>-1){
           //分页
            PageHelper.startPage(pagenum,pagesize);
        }

        return userProductMapMapper.selectByExample(userProductMapExample);
    }
    /**
     * @Description:   查询用户的消费记录条数
     * @Author:         oy
     * @CreateDate:     2018/11/28 0028 下午 2:06
     */
    @Override
    public long countByExample(UserProductMap userProductMap) {
        UserProductMapExample userProductMapExample = new UserProductMapExample();
        UserProductMapExample.Criteria criteria=userProductMapExample.createCriteria();
        userProductMapExample.setOrderByClause("create_time asc");
        if(userProductMap.getUserId()!=null&&userProductMap.getUserId()>-1){
            //根据用户ID
            criteria.andUserIdEqualTo(userProductMap.getUserId());
        }
        return userProductMapMapper.countByExample(userProductMapExample);
    }
}
