package cn.shop.web.shop.controller;

import cn.shop.dto.AwardExecution;
import cn.shop.dto.UserAwardMapExecution;
import cn.shop.enums.AwardStateEnum;
import cn.shop.enums.UserAwardMapStateEnum;
import cn.shop.pojo.Shop;
import cn.shop.shop.service.UserAwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zmt
 * @date 2018/12/10 - 2:04
 */
@Controller
@RequestMapping("/shop")
public class UserAwardController {
    @Autowired
    private UserAwardService userAwardService;
    @Autowired
    private HttpSession session;

    @RequestMapping("/listuserawardmapsbyshop")
    @ResponseBody
    public Map<String,Object> listUserAwardMapByShop(Integer shopId, Integer pageIndex, Integer pageSize, String awardName){
        Map<String,Object> map = new HashMap<>();
        //从session中获取shop
        Shop currentShop = (Shop) session.getAttribute("currentShop");
        if(currentShop!=null&&currentShop.getShopId()!=null){
            UserAwardMapExecution execution = userAwardService.getUserAwardListByShopId(shopId, pageIndex, pageSize, awardName);
            if(execution.getState()== UserAwardMapStateEnum.SUCCESS.getState()){
                map.put("success",true);
                map.put("userAwardMapList",execution.getUserAwardMapList());
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
