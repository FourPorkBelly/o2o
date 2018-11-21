package cn.shop.web.shop.controller;

import cn.shop.dto.ProductCategoryExecution;
import cn.shop.dto.ShopExecution;
import cn.shop.enums.ShopStateEnum;
import cn.shop.pojo.Area;
import cn.shop.pojo.PersonInfo;
import cn.shop.pojo.ProductCategory;
import cn.shop.pojo.Shop;
import cn.shop.shop.service.AreaService;
import cn.shop.shop.service.ProductCategoryService;
import cn.shop.shop.service.ShopCategoryService;
import cn.shop.shop.service.ShopService;
import cn.shop.utlis.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private HttpSession session;
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
        //从session中获取用户信息
        PersonInfo owner = (PersonInfo) session.getAttribute("user");
        Map<String,Object> map = new HashMap<>();
        shop.setOwnerId(owner.getUserId());
        if(!CodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errMsg","请输入正确的验证码");
            return map;
        }
        ShopExecution se = shopService.addShop(shop);
        if(se.getState()== ShopStateEnum.CHECK.getState()){
            //如果注册成功则加入session作为权限使用，用户智只能操作自己的店铺
            List<Shop> shopList = (List<Shop>) session.getAttribute("shopList");
            shopList.add(se.getShop());
            session.setAttribute("shopList",shopList);
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
        //从session中获取用户信息
        PersonInfo owner = (PersonInfo) session.getAttribute("user");
        Map<String,Object> map = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errMsg","请输入正确的验证码");
            return map;
        }
        ShopExecution execution = shopService.updateShop(shop);
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
     * @param
     * @return
     */
    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getShopById(){
        Map<String,Object> map = new HashMap<>();
        Shop currentShop = (Shop) session.getAttribute("currentShop");
        if(currentShop!=null){
            try {
                //根据shopid查询
                Shop shop = shopService.getByShopId(currentShop.getShopId());
                session.setAttribute("currentShop",shop);
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
        //从session中获取用户信息
        PersonInfo owner = (PersonInfo) session.getAttribute("user");
        //判断用户信息
        owner = new PersonInfo();
        owner.setUserId(8);
        if (owner!=null) {
            shop.setOwnerId(owner.getUserId());
            try {
                ShopExecution shopList = shopService.getShopList(shop, 1, 999);
                map.put("owner",shopList.getShopList().get(0).getOwner());
                map.put("shopList",shopList);
                map.put("success",true);
                // 列出店铺成功之后，将店铺放入session中作为权限验证依据，即该帐号只能操作它自己的店铺
                request.getSession().setAttribute("shopList", shopList.getShopList());
            }catch (Exception e){
                map.put("success",false);
                map.put("errMsg",e.getMessage());
                e.printStackTrace();
            }
        }else{
            map.put("success",false);
            map.put("errMsg","用户信息错误");
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
            List<Shop> shopList = (List<Shop>) session.getAttribute("shopList");
            for (Shop shop : shopList) {
                if (shop.getShopId()==shopId) {
                    map.put("redirect",false);
                    session.setAttribute("currentShop",shop);
                    return map;
                }
            }
            map.put("redirect",true);
            map.put("url","/shop/shoplist");
        }
        return map;
    }


}
