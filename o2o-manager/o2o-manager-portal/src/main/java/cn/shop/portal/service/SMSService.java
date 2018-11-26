package cn.shop.portal.service;

import java.util.Map;

/**
 * @author 赵铭涛
 * @creation time 2018/11/26 - 14:11
 */
public interface SMSService {
    Map<String,Object> verifySMS(String phone);
}
