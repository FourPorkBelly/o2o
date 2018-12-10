package cn.shop.web.superadmin.controller;

import cn.shop.cms.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzg
 * @date 2018/12/11 - 1:18
 */
@Controller
@RequestMapping("/getCount")
public class PersonController {
    @Autowired
    private LocalService localService;

    @ResponseBody
    @RequestMapping(value = "/getuserandshop",method = RequestMethod.GET)
    public Map<String,Object> getuserandshop(){
        Map<String,Object> map=new HashMap<>();
        int personsize = localService.getUserCount();
        int shopsize = localService.getShopCount();
        map.put("img",personsize);
        map.put("size",shopsize);
        return map;
    }
}
