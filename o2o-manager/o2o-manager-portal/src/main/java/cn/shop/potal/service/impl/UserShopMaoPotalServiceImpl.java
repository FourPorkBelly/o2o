package cn.shop.potal.service.impl;

import cn.shop.mapper.UserShopMapMapper;
import cn.shop.pojo.UserShopMap;
import cn.shop.pojo.UserShopMapExample;
import cn.shop.potal.service.UserShopMapPotalService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserShopMaoPotalServiceImpl implements UserShopMapPotalService {
    @Autowired
    UserShopMapMapper userShopMapMapper;
    /**
     * @Description:    根据id查询积分
     * @Author:         oy
     * @CreateDate:     2018/11/26 0026 下午 3:54
     */
    @Override
    public List<UserShopMap> selectByExample(Integer userShopId, Integer shopid,Integer pagenum,Integer pagesize) {
        UserShopMapExample userShopMapExample=new UserShopMapExample();
        UserShopMapExample.Criteria criteria=userShopMapExample.createCriteria();
        criteria.andUserIdEqualTo(userShopId);
        if(shopid!=null){
        criteria.andShopIdEqualTo(shopid);
        }
        if(pagenum!=null&&pagesize!=null){
            PageHelper.startPage(pagenum,pagesize);
        }
        return  userShopMapMapper.selectByExample(userShopMapExample);
    }
    /**
     * @Description:    新增积分
     * @Author:         oy
     * @CreateDate:     2018/11/27 0027 上午 9:42
     */
    @Override
    public int insertSelective(UserShopMap record) {

        return userShopMapMapper.insertSelective(record);
    }
    /**
     * @Description:    修改积分
     * @Author:         oy
     * @CreateDate:     2018/11/27 0027 上午 9:42
     */
    @Override
    public int updateByExample(UserShopMap record) {
        UserShopMapExample userShopMapExample=new UserShopMapExample();
        UserShopMapExample.Criteria criteria=userShopMapExample.createCriteria();
        //根据id 和商铺id进行修改
        criteria.andShopIdEqualTo(record.getShopId());
        criteria.andUserIdEqualTo(record.getUserId());

        return userShopMapMapper.updateByExample(record,userShopMapExample);
    }
    /**
     * @Description:        查询用户店铺积分条数
     * @Author:         oy
     * @CreateDate:     2018/11/28 0028 下午 2:48
     */
    @Override
    public long countByExample(Integer integer) {
        UserShopMapExample userShopMapExample=new UserShopMapExample();
        UserShopMapExample.Criteria criteria=userShopMapExample.createCriteria();
        criteria.andUserIdEqualTo(integer);
        return userShopMapMapper.countByExample(userShopMapExample);
    }
}
