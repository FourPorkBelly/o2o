package cn.shop.cms.service;

import cn.shop.dto.PersonInfoExecution;
import cn.shop.pojo.LocalAuth;
import cn.shop.pojo.PersonInfo;

import java.util.List;

/**
 * @author yzg
 * @date 2018/11/26 - 14:58
 */
public interface LocalService {
    /**
     * 管理员登录
     * @param localAuth
     * @return
     */
    LocalAuth login(LocalAuth localAuth);

    /**
     * 查询用户信息
     * @param personInfo
     * @param page
     * @param limit
     * @return
     */
    PersonInfoExecution queryPersonInfo(PersonInfo personInfo, int page, int limit);

    /**
     * 设置用户状态
     * @param personInfo
     * @return
     */
    int disableUser(PersonInfo personInfo);

    /**
     *得到用户状态
     * @param userid
     * @return
     */
    int getUserEnable(int userid);

    int getUserCount();
    int getShopCount();
}
