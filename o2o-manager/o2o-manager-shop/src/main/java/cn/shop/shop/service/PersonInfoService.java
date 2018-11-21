package cn.shop.shop.service;

import cn.shop.dto.PersonInfoExecution;
import cn.shop.pojo.PersonInfo;

/**
 * @author 赵铭涛
 * @creation time 2018/11/21 - 9:07
 */
public interface PersonInfoService {
    /**
     *根据id获取用户信息
     * @param userId
     * @return
     */
    PersonInfo getPersonInfoById(Integer userId);

    /**
     *根据条件查询用户信息
     * @param personInfo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    PersonInfoExecution getPersonInfoList(PersonInfo personInfo,
                                          int pageIndex, int pageSize);

    /**
     *添加用户信息
     * @param personInfo
     * @return
     */
    PersonInfoExecution addPersonInfo(PersonInfo personInfo);

    /**
     *修改用户信息
     * @param personInfo
     * @return
     */
    PersonInfoExecution updatePersonInfo(PersonInfo personInfo);
}
