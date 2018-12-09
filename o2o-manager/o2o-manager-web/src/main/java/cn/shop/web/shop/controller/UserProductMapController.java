package cn.shop.web.shop.controller;

import cn.shop.pojo.Shop;
import cn.shop.pojo.UserProductMap;
import cn.shop.shop.service.UserProductMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
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
    @Autowired
    private HttpSession session;
    /**
     * 查询消费记录
     * @param shopId
     * @param cxtj
     * @return
     */
    @RequestMapping("/listuserproductmapsbyshop")
    @ResponseBody
    public Map<String,Object> listUserProductMapsByShop(Integer shopId,String cxtj){
        Map<String, Object> map = new HashMap<>();
        //从session中获取shopid
        Shop currentShop = (Shop) session.getAttribute("currentShop");
        if(currentShop!=null){
            UserProductMap userProductMap = new UserProductMap();
            userProductMap.setShopId(shopId);
            map = userProductMapService.getUserProductMapByShopId(shopId, cxtj);
            map.put("success",true);
        }else {
            map.put("success",false);
            map.put("errMsg","商铺不存在");
        }
        return map;
    }
}
