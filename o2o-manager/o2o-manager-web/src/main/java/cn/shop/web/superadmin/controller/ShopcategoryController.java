package cn.shop.web.superadmin.controller;

import cn.shop.cms.service.Impl.ShopcategoryServiceImpl;
import cn.shop.cms.service.ShopcategoryService;
import cn.shop.dto.ShopCategoryExecution;
import cn.shop.pojo.ShopCategory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yzg
 * @date 2018/12/7 - 16:13
 */
@Controller
@RequestMapping("/shopcate")
public class ShopcategoryController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;
    @Autowired
    ShopcategoryService shopcategoryService;

    /**
     * 查询一级分类
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryshopcate1", produces = "application/json; charset=utf-8")
    public String queryshopcate1(){
        response.setContentType("text/html;charset=utf-8");
        List<ShopCategory> list=shopcategoryService.getShopCategoryList();
        JSONArray jsonArray=new JSONArray();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ShopCategory s:list){
            JSONObject object=new JSONObject();
            object.put("shopCategoryId",s.getShopCategoryId());
            object.put("shopCategoryName",s.getShopCategoryName());
            object.put("shopCategoryImg",s.getShopCategoryImg());
            object.put("priority",s.getPriority());
            object.put("lastEditTime",formatter.format(s.getLastEditTime()));
            jsonArray.add(object);
        }
        String jso="{\"code\":0,\"msg\":\"\",\"count\":"+list.size()+",\"data\":"+jsonArray.toString()+"}";
        return jso;
    }
    /**
     * 查询二级分类
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryshopcate2", produces = "application/json; charset=utf-8")
    public String queryshopcate2(int page,int limit){
        response.setContentType("text/html;charset=utf-8");
        ShopCategoryExecution execution=shopcategoryService.getShopCategoryList2(page,limit);
        List<ShopCategory> list=execution.getShopCategoryList();
        JSONArray jsonArray=new JSONArray();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (ShopCategory s:list){
            JSONObject object=new JSONObject();
            object.put("shopCategoryId",s.getShopCategoryId());
            object.put("shopCategoryName",s.getShopCategoryName());
            object.put("shopCategoryImg",s.getShopCategoryImg());
            object.put("priority",s.getPriority());
            object.put("lastEditTime",formatter.format(s.getLastEditTime()));
            jsonArray.add(object);
        }
        String jso="{\"code\":0,\"msg\":\"\",\"count\":"+execution.getCount()+",\"data\":"+jsonArray.toString()+"}";
        return jso;
    }
    /**
     * 根据shopcategoryid得到ShopCategory对象
     * @param shopCategoryId
     * @return
     */
    @RequestMapping(value = "/getShopCategoryById",method = RequestMethod.GET)
    public String getShopCategoryById(int shopCategoryId){
        response.setContentType("text/html;charset=utf-8");
        ShopCategory shopCategory = shopcategoryService.getShopCategoryById(shopCategoryId);
        request.getSession().setAttribute("shopCategory",shopCategory);
        return "/superadmin/updateshopcate";
    }

    /**
     * 将shopcategory对象赋值到html页面
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/setShopCategory",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public String setShopCategory(){
        ShopCategory shopCategory=(ShopCategory)request.getSession().getAttribute("shopCategory");
        JSONObject object=new JSONObject();
        object.put("shopCategoryId",shopCategory.getShopCategoryId());
        object.put("shopCategoryName",shopCategory.getShopCategoryName());
        object.put("shopCategoryDesc",shopCategory.getShopCategoryDesc());
        object.put("img",shopCategory.getShopCategoryImg());
        object.put("priority",shopCategory.getPriority());
        return object.toString();
    }
    @ResponseBody
    @RequestMapping(value = "updateShopCategory",method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Map<String,Object> updateShopCategory(ShopCategory shopCategory) throws IOException {
        Map<String,Object> map=new HashMap<>();
        System.out.println(shopCategory+"shopCategory-----------");
        int i = shopcategoryService.updateShopCategory(shopCategory);
        if (i>0){
            map.put("msg","修改成功");
        }else {
            map.put("msg","修改失败");
        }
        return  map;
    }
}
