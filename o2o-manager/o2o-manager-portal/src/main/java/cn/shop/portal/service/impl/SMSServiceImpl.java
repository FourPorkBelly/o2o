package cn.shop.portal.service.impl;

import cn.shop.portal.service.SMSService;
import cn.shop.utlis.SMSUtil;
import cn.shop.utlis.jedis.JedisClient;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 赵铭涛
 * @creation time 2018/11/26 - 14:15
 */
@Service
public class SMSServiceImpl implements SMSService {
    @Autowired
    private JedisClient jedisClient;

    @Value("PHONE_CHECK_CODE")
    private String PHONE_CHECK_CODE;
    /**
     * 获取手机验证码
     * @param phone
     * @return
     */
    @Override
    public Map<String, Object> getVerifySMS(String phone) {
        Map<String, Object> map = new HashMap<>();
        //验证手机号码

        //获取随机字符串（验证码）(6位)
        String checkCode = SMSUtil.generateCheckCode(6);
        //发送验证码
        try {
            String send = SMSUtil.send(phone, checkCode);
            JSONObject jsonObject = JSONObject.fromObject(send);
            System.out.println(jsonObject);
            if(!"00000".equals(jsonObject.get("respCode"))){
                System.out.println("信息："+jsonObject.get("respDesc"));
                map.put("sucess",false);
                map.put("errMsg","未知错误");
            }else{
                try {
                    //将验证码md5加密后存入redis
                    jedisClient.set(PHONE_CHECK_CODE, DigestUtils.md5Hex(checkCode));
                    //设置key的有效期
                    jedisClient.expire(PHONE_CHECK_CODE,300);
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("信息："+jsonObject.get("respDesc"));
                map.put("sucess",true);
                map.put("msg",jsonObject.get("respDesc"));
            }

        }catch (Exception e){
            map.put("errMsg","未知错误");
            map.put("sucess",false);
        }
        return map;
    }

    /**
     * 效验手机验证码
     * @param verification
     * @return
     */
    @Override
    public Map<String, Object> verifySMS(String verification) {
        Map<String, Object> map = new HashMap<>();
        //验证码md5加密
        verification = DigestUtils.md5Hex(verification);
        try {
            //从redis中获取验证码信息
            String phoneSMS = jedisClient.get(PHONE_CHECK_CODE);
            if(phoneSMS==null){
                map.put("sucess",false);
                map.put("errMsg","验证码已过期");
            }else{
                if(phoneSMS.equals(verification)){
                    map.put("sucess",true);
                    map.put("errMsg","验证通过");
                    //验证通过后清空redis
                    jedisClient.del(PHONE_CHECK_CODE);
                }else{
                    map.put("sucess",false);
                    map.put("errMsg","请输入正确的验证码");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
