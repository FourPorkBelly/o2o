package cn.shop.web.shop.controller;

import cn.shop.dto.ShopExecution;
import cn.shop.enums.ShopStateEnum;
import cn.shop.pojo.Shop;
import cn.shop.shop.service.AreaService;
import cn.shop.shop.service.ShopCategoryService;
import cn.shop.shop.service.ShopService;
import cn.shop.utlis.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zmt
 * @date 2018/11/17 - 23:03
 */
@Controller
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> registerShop(Shop shop,HttpServletRequest request){
        System.out.println(shop);
        Map<String,Object> map = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errMsg","请输入正确的验证码");
            return map;
        }
        ShopExecution execution = shopService.addShop(shop);
        if(execution.getState()== ShopStateEnum.CHECK.getState()){
            map.put("success",true);
            map.put("errMsg","注册成功");
            return map;
        }
        map.put("success",false);
        map.put("errMsg","注册失败");
        return map;
    }

    /**
     * 获取商铺类别
     * @return
     */
    @RequestMapping("/getshopinitinfo")
    @ResponseBody
    public Map<String,Object> getShopCategoryList(){
        Map<String,Object> map = new HashMap<>();
        try {
            map.put("shopCategoryList",shopCategoryService.getShopCategoryList(null));
            map.put("areaList",areaService.getAreaList());
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);
            map.put("errMsg",e.getMessage());
        }
        return map;
    }

}
