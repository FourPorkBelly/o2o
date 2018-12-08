package cn.shop.web.superadmin.controller;

import cn.shop.cms.service.ShopServicecms;
import cn.shop.dto.ShopExecution;
import cn.shop.pojo.Shop;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author yzg
 * @date 2018/11/26 - 14:37
 */
@Controller
@RequestMapping("/cmsshop")
public class ShopCMSController {
    @Autowired
    private ShopServicecms shopServicecms;
    @Autowired
    HttpServletResponse response;
    /**
     * 查询审核过的商铺
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryShopList",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public String queryShopList(String shopName,int page,int limit) throws IOException {
        Shop shops=new Shop();
        shops.setShopName(shopName);
        shops.setEnableStatus(1);
        response.setContentType("text/html;charset=utf-8");
        System.out.println(shops.getShopName()+"--------------------------------------商铺名字");
        ShopExecution shopExecution=shopServicecms.queryShopList(shops,page, limit);
        List<Shop> shopList=shopExecution.getShopList();
        JSONArray jsonArray = new JSONArray();
        for (Shop shop:shopList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("shopId",shop.getShopId());
            jsonObject.put("ownerId",shop.getOwner().getName());
            jsonObject.put("areaId",shop.getArea().getAreaName());
            jsonObject.put("shopCategoryId",shop.getShopCategory().getShopCategoryName());
            jsonObject.put("shopName",shop.getShopName());
            jsonObject.put("shopDesc",shop.getShopDesc());
            jsonObject.put("shopAddr",shop.getShopAddr());
            jsonObject.put("phone",shop.getPhone());
            jsonObject.put("shopImg",shop.getShopImg());
            jsonArray.add(jsonObject);
        }
        int count=shopExecution.getCount();
        String jso="{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+jsonArray.toString()+"}";

        System.out.println(jso);
        return jso;
    }
    /**
     * 查询未审核过的商铺
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryAuditShopList",method = RequestMethod.GET,produces = "text/html;charset=UTF-8" )
    public String queryAuditShopList(String shopName,int page,int limit) throws IOException {
        Shop shops=new Shop();
        shops.setShopName(shopName);
        shops.setEnableStatus(0);
        response.setContentType("text/html;charset=utf-8");
        System.out.println(shops.getShopName()+"--------------------------------------商铺名字");
        ShopExecution shopExecution=shopServicecms.queryShopList(shops,page, limit);
        List<Shop> shopList=shopExecution.getShopList();
        JSONArray jsonArray = new JSONArray();
        for (Shop shop:shopList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("shopId",shop.getShopId());
            jsonObject.put("ownerId",shop.getOwner().getName());
            jsonObject.put("areaId",shop.getArea().getAreaName());
            jsonObject.put("shopCategoryId",shop.getShopCategory().getShopCategoryName());
            jsonObject.put("shopName",shop.getShopName());
            jsonObject.put("shopDesc",shop.getShopDesc());
            jsonObject.put("shopAddr",shop.getShopAddr());
            jsonObject.put("phone",shop.getPhone());
            jsonObject.put("shopImg",shop.getShopImg());
            jsonArray.add(jsonObject);
        }
        int count=shopExecution.getCount();
        String jso="{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+jsonArray.toString()+"}";

        System.out.println(jso);
        return jso;
    }

    /**
     * 店铺通过审核
     * @param shopid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/passShop",method = RequestMethod.GET,produces={"application/json;charset=utf-8"})
    public String passShop(@RequestParam(value = "shopId") int shopid){
        Shop shop =new Shop();
        shop.setShopId(shopid);
        shop.setEnableStatus(1);
        int rs=shopServicecms.passShop(shop);
        if (rs>0){
            return "y";
        }
        return null;
    }

    /**
     * 店铺不通过审核
     * @param shopid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/unpassShop",method = RequestMethod.GET,produces={"application/json;charset=utf-8"})
    public String unpassShop(@RequestParam(value = "shopId") int shopid){
        Shop shop =new Shop();
        shop.setShopId(shopid);
        shop.setEnableStatus(-1);
        int rs=shopServicecms.passShop(shop);
        if (rs>0){
            return "y";
        }
        return null;
    }

}
