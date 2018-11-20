package cn.shop.web.shop.controller;

import cn.shop.dto.ShopExecution;
import cn.shop.enums.ShopStateEnum;
import cn.shop.pojo.Area;
import cn.shop.pojo.PersonInfo;
import cn.shop.pojo.Shop;
import cn.shop.shop.service.AreaService;
import cn.shop.shop.service.ShopCategoryService;
import cn.shop.shop.service.ShopService;
import cn.shop.utlis.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private HttpServletRequest request;

    /**
     * 店铺注册
     * @param shop
     * @param
     * @return
     */
    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> registerShop(Shop shop){
        PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
        Map<String,Object> map = new HashMap<>();
        shop.setOwnerId(owner.getUserId());
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

    /**
     * 修改店铺信息
     * @param shop
     * @return
     */
    @RequestMapping(value = "/modifyshop")
    @ResponseBody
    public Map<String,Object> getShopById(Shop shop){
        Map<String,Object> map = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errMsg","请输入正确的验证码");
            return map;
        }
        ShopExecution execution = shopService.modifyShop(shop);
        if(execution.getState()== ShopStateEnum.CHECK.getState()){
            map.put("success",true);
            map.put("errMsg","修改成功");
            return map;
        }
        map.put("success",false);
        map.put("errMsg","修改失败");
        return map;
    }

    /**
     * 通过id获取商铺信息
     * @param shopId
     * @return
     */
    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopById(@RequestParam(value = "shopId",defaultValue = "0") Integer shopId){
        Map<String,Object> map = new HashMap<>();
        if(shopId>0){
            try {
                System.out.println(shopId);
                Shop shop = shopService.getByShopId(shopId);
                System.out.println("shop:"+shop);
                List<Area> areaList = areaService.getAreaList();
                map.put("shop",shop);
                map.put("areaList",areaList);
                map.put("success",true);
            }catch (Exception e){
                e.printStackTrace();
                map.put("success",false);
                map.put("errMsg",e.toString());
            }
        }else {
            map.put("success",false);
            map.put("errMsg","信息错误");
        }
        return map;
    }

    /**
     *
     *根据用户显示店铺列表
     * @return
     */
    @RequestMapping("/getshoplist")
    @ResponseBody
    public Map<String,Object> getShopList(){
        Map<String,Object> map = new HashMap<>();
        PersonInfo user = new PersonInfo();
        Shop shop = new Shop();
        //
        shop.setOwnerId(8);
        try {
            ShopExecution shopList = shopService.getShopList(shop, 1, 999);
            map.put("owner",shopList.getShopList().get(0).getOwner());
            map.put("shopList",shopList);
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);
            map.put("errMsg",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     *根据id判断店铺是否存在
     * @param shopId
     * @return
     */
    @RequestMapping("/getshopmanagementinfo")
    @ResponseBody
    public Map<String,Object> getShopManagementInfo(@RequestParam(value = "shopId",defaultValue = "0") Integer shopId){
        Map<String,Object> map = new HashMap<>();
        if(shopId<=0){
            map.put("redirect",true);
            map.put("url","/shop/shoplist");
        }else {
            Shop shop = shopService.getByShopId(shopId);
            map.put("redirect",false);
        }
        return map;
    }
}
