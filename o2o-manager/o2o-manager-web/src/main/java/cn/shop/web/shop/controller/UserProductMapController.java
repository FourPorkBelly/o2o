package cn.shop.web.shop.controller;

import cn.shop.pojo.UserProductMap;
import cn.shop.shop.service.UserProductMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author 赵铭涛
 * @creation time 2018/12/9 - 15:40
 */
@Controller
@RequestMapping("/shop")
public class UserProductMapController {
    @Autowired
    private UserProductMapService userProductMapService;

    @RequestMapping("/listuserproductmapsbyshop")
    @ResponseBody
    public Map<String,Object> listUserProductMapsByShop(Integer shopId){
        UserProductMap userProductMap = new UserProductMap();
        userProductMap.setShopId(shopId);
         return userProductMapService.getUserProductMapByShopId(userProductMap);
    }
}
