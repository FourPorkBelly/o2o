package cn.shop.web.shop.controller;

import cn.shop.dto.ShopAuthMapExecution;
import cn.shop.enums.ShopAuthMapStateEnum;
import cn.shop.pojo.PersonInfo;
import cn.shop.pojo.Shop;
import cn.shop.pojo.ShopAuthMap;
import cn.shop.shop.service.ShopAuthMapService;
import cn.shop.utlis.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赵铭涛
 * @creation time 2018/11/23 - 10:17
 */
@Controller
@RequestMapping("/shop")
public class ShopAuthMapController {
    @Autowired
    ShopAuthMapService shopAuthMapService;
    @Autowired
    HttpSession session;
    @Autowired
    private HttpServletRequest request;
    /**
     * 根据商铺id分页显示该店铺的授权信息
     * @param shopId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/listshopauthmapsbyshop")
    @ResponseBody
    public Map<String,Object> getShopAuthMapList(Integer shopId,Integer pageIndex,Integer pageSize){
        //返回值
        Map<String,Object> map = new HashMap<>();
        //从session中获取店铺信息
        Shop currentShop = (Shop) session.getAttribute("currentShop");
        //空值判断
        if(currentShop!=null&&currentShop.getShopId()!=null){
            //從session中取出用戶信息
            PersonInfo user = (PersonInfo) session.getAttribute("user");
            //分页取出该店铺下的信息列表
            ShopAuthMapExecution se = shopAuthMapService.getShopAuthMapList(currentShop.getShopId(),pageIndex,pageSize,user.getUserId());
            //查出的結果存入session
            session.setAttribute("shopAuthMapList",se.getShopAuthMapList());
            map.put("shopAuthMapList",se.getShopAuthMapList());
            map.put("count",se.getCount());
            map.put("success",true);
        }else{
            map.put("success",false);
            map.put("errMsg","信息错误");
        }
        return map;
    }

    /**
     * 根据shopAuthId返回对应的授权信息
     * @param shopAuthId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getshopauthmapbyid")
    public Map<String,Object> getShopAuthMapById(Integer shopAuthId){
        Map<String,Object> map = new HashMap<>();
        if(isShopAuthMapById(shopAuthId)){
            //非空判断
            if (shopAuthId!=null&&shopAuthId>=0) {
                //根据前台传入的shopAuthId查找对应的授权信息
                ShopAuthMap shopAuthMap = shopAuthMapService.getShopAuthMapById(shopAuthId);
                //將信息存入session中
                session.setAttribute("shopAuthMap",shopAuthMap);
                map.put("shopAuthMap",shopAuthMap);
                map.put("success",true);
            }else{
                map.put("success",false);
                map.put("errMsg","传入类型错误");
            }
        }else {
            map.put("success",false);
            map.put("errMsg","传入类型错误");
        }
        return map;
    }


    /**
     * 修改店员信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/modifyshopauthmap")
    public Map<String,Object> updateShopAuthMap(String title){
        Map<String,Object> map = new HashMap<>();
        if(!CodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errMsg","请输入正确的验证码");
            return map;
        }

        //从session中获取店员信息
        ShopAuthMap shopAuthMap = (ShopAuthMap) session.getAttribute("shopAuthMap");
        if (shopAuthMap!=null){
            ShopAuthMap authMap = new ShopAuthMap();
            authMap.setShopAuthId(shopAuthMap.getShopAuthId());
            authMap.setTitle(title);
            ShopAuthMapExecution execution = shopAuthMapService.updateShopAuthMap(authMap);
            if(execution.getState()==ShopAuthMapStateEnum.SUCCESS.getState()){
                map.put("success",true);
                map.put("errMsg",execution.getStateInfo());
            }else{
                map.put("success",false);
                map.put("errMsg", ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO.getStateInfo());
            }
        }else {
            map.put("success",false);
            map.put("errMsg", ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO.getStateInfo());
        }
        return map;
    }

    private boolean isShopAuthMapById(Integer shopAuthId){
        //從session中獲取shopAuthMapList
        List<ShopAuthMap> shopAuthMapList = (List<ShopAuthMap>) session.getAttribute("shopAuthMapList");
        for (ShopAuthMap shopAuthMap : shopAuthMapList) {
            if (shopAuthMap.getShopAuthId()==shopAuthId) {
                return true;
            }
        }
        return false;
    }
}
