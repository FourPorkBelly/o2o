package cn.shop.web.superadmin.controller;

import cn.shop.cms.service.ShopServicecms;
import cn.shop.dto.ShopExecution;
import cn.shop.pojo.Shop;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * 查询所有商铺
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryShopList",method = RequestMethod.GET,produces={"application/json;charset=utf-8"})
    public String queryShopList(Shop shops,int page,int limit) throws IOException {
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
//            jsonObject.put("priority",shop.getPriority());
//            Date currentTime = new Date();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            String createTime = formatter.format(shop.getCreateTime());
//            String lastEditTime=formatter.format(shop.getLastEditTime());
//            jsonObject.put("lastEditTime",lastEditTime);
            jsonObject.put("enableStatus",shop.getEnableStatus());
//            jsonObject.put("advice",shop.getAdvice());
            jsonArray.add(jsonObject);
        }
        int count=shopExecution.getCount();
        String jso="{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+jsonArray.toString()+"}";

        System.out.println(jso);
        return jso;
    }

}
