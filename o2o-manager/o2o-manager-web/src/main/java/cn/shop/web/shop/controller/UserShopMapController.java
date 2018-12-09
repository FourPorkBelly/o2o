package cn.shop.web.shop.controller;

import cn.shop.dto.UserAwardMapExecution;
import cn.shop.dto.UserShopMapExecution;
import cn.shop.enums.UserAwardMapStateEnum;
import cn.shop.enums.UserShopMapStateEnum;
import cn.shop.pojo.Shop;
import cn.shop.shop.service.UserShopMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zmt
 * @date 2018/12/10 - 2:28
 */
@Controller
@RequestMapping("/shop")
public class UserShopMapController {
    @Autowired
    private UserShopMapService userShopMapService;
    @Autowired
    private HttpSession session;

    @RequestMapping("/listusershopmapsbyshop")
    @ResponseBody
    public Map<String,Object> listUserShopMapsByShop(Integer pageIndex, Integer pageSize, Integer shopId, String userName){
        Map<String,Object> map = new HashMap<>();
        //从session中获取shop
        Shop currentShop = (Shop) session.getAttribute("currentShop");
        if(currentShop!=null&&currentShop.getShopId()!=null){
            UserShopMapExecution execution = userShopMapService.getUserShopMapListByShopIdAndUserName(pageIndex, pageSize, shopId, userName);
            if(execution.getState()== UserShopMapStateEnum.SUCCESS.getState()){
                map.put("success",true);
                map.put("userShopMapList",execution.getUserShopMapList());
            }else {
                map.put("success",false);
                map.put("errMsg","后台异常");
            }
        }else {
            map.put("success",false);
            map.put("errMsg","商铺信息错误");
        }
        return map;
    }
}
