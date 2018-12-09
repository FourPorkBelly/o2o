package cn.shop.shop.service.impl;

import cn.shop.dto.UserShopMapExecution;
import cn.shop.enums.UserShopMapStateEnum;
import cn.shop.mapper.UserShopMapMapper;
import cn.shop.pojo.UserShopMap;
import cn.shop.pojo.UserShopMapExample;
import cn.shop.shop.service.UserShopMapService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zmt
 * @date 2018/12/10 - 2:20
 */
@Service
public class UserShopMapServiceImpl implements UserShopMapService {
    @Autowired
    private UserShopMapMapper userShopMapMapper;

    /**
     * 通过shopid获取用户列表
     * @param pageIndex
     * @param pageSize
     * @param shopId
     * @param userName
     * @return
     */
    @Override
    public UserShopMapExecution getUserShopMapListByShopIdAndUserName(Integer pageIndex, Integer pageSize, Integer shopId, String userName) {
        UserShopMapExecution execution = new UserShopMapExecution();
        try {
            if(userName.equals("")){
                userName=null;
            }
            //创建查询条件
            UserShopMapExample example = new UserShopMapExample();
            UserShopMapExample.Criteria criteria = example.createCriteria();
            criteria.andShopIdEqualTo(shopId);
            if(userName!=null){
                criteria.andUserNameLike("%"+ userName +"%");
            }
            PageHelper.startPage(pageIndex,pageSize);

            List<UserShopMap> userShopMaps = userShopMapMapper.selectByExample(example);

            PageInfo pageInfo = new PageInfo(userShopMaps);

            execution.setState(UserShopMapStateEnum.SUCCESS.getState());
            execution.setUserShopMapList(userShopMaps);
        }catch (Exception e){
            e.printStackTrace();
            return new UserShopMapExecution(UserShopMapStateEnum.INNER_ERROR);
        }
        return execution;
    }
}
