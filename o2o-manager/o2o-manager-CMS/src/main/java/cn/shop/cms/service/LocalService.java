package cn.shop.cms.service;

import cn.shop.pojo.LocalAuth;
import cn.shop.pojo.PersonInfo;

import java.util.List;

/**
 * @author yzg
 * @date 2018/11/26 - 14:58
 */
public interface LocalService {
    LocalAuth login(LocalAuth localAuth);
    List<PersonInfo> queryPersonInfo(PersonInfo personInfo,Integer pageIndex,Integer pageSize);
}
