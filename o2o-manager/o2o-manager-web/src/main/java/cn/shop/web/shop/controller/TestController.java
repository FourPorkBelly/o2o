package cn.shop.web.shop.controller;

import cn.shop.portal.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 赵铭涛
 * @creation time 2018/11/28 - 14:31
 */
@Controller
public class TestController {
    @Autowired
    private SMSService smsService;

    /**
     *
     * @param phone
     * @return
     */
    @RequestMapping("/testsms")
    @ResponseBody
    public Object testSmss(String phone){
        return smsService.getVerifySMS(phone);
    }

    @ResponseBody
    @RequestMapping("/testphonesms")
    public Object testPhoneSMS(String verify){
        return smsService.verifySMS(verify);
    }
}
