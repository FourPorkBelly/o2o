package cn.shop.portal.service.impl;

import cn.shop.portal.service.SMSService;
import cn.shop.utlis.SMSUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 赵铭涛
 * @creation time 2018/11/26 - 14:15
 */
public class SMSServiceImpl implements SMSService {
    //手机验证码
    @Override
    public Map<String, Object> verifySMS(String phone) {
        Map<String, Object> map = new HashMap<>();
        //验证手机号码

        //获取随机字符串（验证码）(6位)
        String checkCode = SMSUtil.generateCheckCode(6);
        System.out.println(checkCode);
        //发送验证码
        try {
            String send = SMSUtil.send(phone, checkCode);
            map.put("sucess",true);
        }catch (Exception e){
            map.put("sucess",false);
        }
        return null;
    }
}
