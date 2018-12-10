package cn.shop.web.superadmin.controller;

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
//        设置response编码格式为text/html;charset=utf-8
        response.setContentType("text/html;charset=utf-8");
//        新建list接收一级分类的集合
        List<ShopCategory> list=shopcategoryService.getShopCategoryList();
//        新建一个jsonarray
        JSONArray jsonArray=new JSONArray();
//        转换日期格式SimpleDateFormat  为yyyy-MM-dd HH:mm:ss
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        foreach循环遍历list
        for (ShopCategory s:list){
//            新建JSONObject对象  将所需要显示的值
//            shopCategoryId，shopCategoryName，shopCategoryImg，priority，lastEditTime
//            传入json对象
            JSONObject object=new JSONObject();
            object.put("shopCategoryId",s.getShopCategoryId());
            object.put("shopCategoryName",s.getShopCategoryName());
            object.put("shopCategoryImg",s.getShopCategoryImg());
            object.put("priority",s.getPriority());
            object.put("lastEditTime",formatter.format(s.getLastEditTime()));
//            将json对象存入jsonarray
            jsonArray.add(object);
        }
//        转换数据格式
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
//        设置response编码格式为text/html;charset=utf-8
        response.setContentType("text/html;charset=utf-8");
//        新建ShopCategoryExecution接收二级分类的查询结果
        ShopCategoryExecution execution=shopcategoryService.getShopCategoryList2(page,limit);
//        新建list，将查询集合赋值给list
        List<ShopCategory> list=execution.getShopCategoryList();
//        新建一个jsonarray
        JSONArray jsonArray=new JSONArray();
//        转换日期格式SimpleDateFormat  为yyyy-MM-dd HH:mm:ss
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            新建JSONObject对象  将所需要显示的值
//            shopCategoryId，shopCategoryName，shopCategoryImg，priority，lastEditTime
//            传入json对象
        for (ShopCategory s:list){
            JSONObject object=new JSONObject();
            object.put("shopCategoryId",s.getShopCategoryId());
            object.put("shopCategoryName",s.getShopCategoryName());
            object.put("shopCategoryImg",s.getShopCategoryImg());
            object.put("priority",s.getPriority());
            object.put("lastEditTime",formatter.format(s.getLastEditTime()));
            jsonArray.add(object);
        }
//         转换数据格式
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
//        设置编码格式
        response.setContentType("text/html;charset=utf-8");
//        根据shopcategoryid得到ShopCategory对象
        ShopCategory shopCategory = shopcategoryService.getShopCategoryById(shopCategoryId);
//        将查询出的单个shopCategory对象存入session
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
//        从sessin得到shopCategory对象
        ShopCategory shopCategory=(ShopCategory)request.getSession().getAttribute("shopCategory");
//        新建JSONObject接收shopCategory对象
        JSONObject object=new JSONObject();
//        将shopCategory的值以键值对形式给到json对象
        object.put("shopCategoryId",shopCategory.getShopCategoryId());
        object.put("shopCategoryName",shopCategory.getShopCategoryName());
        object.put("shopCategoryDesc",shopCategory.getShopCategoryDesc());
        object.put("img",shopCategory.getShopCategoryImg());
        object.put("priority",shopCategory.getPriority());
//        返回json  toString
        return object.toString();
    }

    /**
     * 修改商铺类别
     * @param shopCategory
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "updateShopCategory",method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public Map<String,Object> updateShopCategory(ShopCategory shopCategory) throws IOException {
        Map<String,Object> map=new HashMap<>();
//        返回修改结果
        int i = shopcategoryService.updateShopCategory(shopCategory);
//        如果大于0则修改成功 map.put
        if (i>0){
            map.put("msg","修改成功");
        }else {
            map.put("msg","修改失败");
        }
        return  map;
    }
}
