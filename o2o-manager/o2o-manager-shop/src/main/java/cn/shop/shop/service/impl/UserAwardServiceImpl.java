package cn.shop.shop.service.impl;

import cn.shop.dto.UserAwardMapExecution;
import cn.shop.enums.UserAwardMapStateEnum;
import cn.shop.mapper.UserAwardMapMapper;
import cn.shop.pojo.UserAwardMap;
import cn.shop.shop.service.UserAwardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zmt
 * @date 2018/12/10 - 1:46
 */
@Service
public class UserAwardServiceImpl implements UserAwardService {
    @Autowired
    private UserAwardMapMapper userAwardMapMapper;


    /**
     * 根据shopid和条件查询
     * @param shopId
     * @param pageIndex
     * @param pageSize
     * @param awardName
     * @return
     */
    @Override
    public UserAwardMapExecution getUserAwardListByShopId(Integer shopId, Integer pageIndex, Integer pageSize, String awardName) {
        UserAwardMapExecution execution = new UserAwardMapExecution();
        try {
            PageHelper.startPage(pageIndex,pageSize);

            List<UserAwardMap> userAwardMaps = userAwardMapMapper.selectByShopIdAndAwardName(shopId, awardName);

            PageInfo pageInfo = new PageInfo(userAwardMaps);

            execution.setUserAwardMapList(userAwardMaps);
            execution.setState(UserAwardMapStateEnum.SUCCESS.getState());
        }catch (Exception e){
            e.printStackTrace();
            return new UserAwardMapExecution(UserAwardMapStateEnum.INNER_ERROR);
        }
        return execution;
    }
}
