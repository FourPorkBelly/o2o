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
    LocalAuth login(LocalAuth localAuth);
    PersonInfoExecution queryPersonInfo(PersonInfo personInfo, int page, int limit);
    int disableUser(PersonInfo personInfo);

    int getUserEnable(int userid);
}
