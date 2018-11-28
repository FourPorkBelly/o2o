package cn.shop.potal.service;

import cn.shop.pojo.UserAwardMap;
import cn.shop.pojo.UserAwardMapExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @Description:   用户积分消费
 * @Author:         oy
 * @CreateDate:     2018/11/28 0028 上午 11:21
 */
public interface UserAwardMapProtalService {
    int insertSelective(UserAwardMap record);

}
