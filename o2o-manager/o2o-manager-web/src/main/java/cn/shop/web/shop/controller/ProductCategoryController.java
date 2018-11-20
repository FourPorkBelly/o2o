package cn.shop.web.shop.controller;

import cn.shop.dto.ProductCategoryExecution;
import cn.shop.dto.Result;
import cn.shop.enums.ProductCategoryStateEnum;
import cn.shop.pojo.ProductCategory;
import cn.shop.pojo.Shop;
import cn.shop.shop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赵铭涛
 * @creation time 2018/11/20 - 8:56
 */
@Controller
@RequestMapping("/shop")
public class ProductCategoryController {
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    HttpSession session;
    /**
     * 根据shopid显示商品类别列表
     * @return
     */
    @ResponseBody
    @RequestMapping("/getproductcategorylist")
    public Result<List<ProductCategory>> getProductCategoryList(Integer shopId){
        //从session中获取商品用户信息

        List<ProductCategory> list = null;
        if (shopId!=null && shopId>0){
            //用户信息不为空，查询并返回数据
            list = productCategoryService.getProductCategoryList(shopId);
            return new Result<List<ProductCategory>>(true,list);
        }else {
            //用户信息为空，返回错误信息
            ProductCategoryStateEnum productCategoryStateEnum = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false,productCategoryStateEnum.getState(),productCategoryStateEnum.getStateInfo());
        }
    }

    /**
     * 添加店铺商品分类
     * @param productCategoryList
     * @return
     */
    @RequestMapping(value = "/addproductcategorys",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList){
        Map<String,Object> map = new HashMap<>();
        //判断是否存在商品类别
        if(productCategoryList!=null&&productCategoryList.size()>0){
            //补全店铺信息
            for (ProductCategory productCategory : productCategoryList) {
                productCategory.setShopId(15);
            }
            try {
                ProductCategoryExecution pe = productCategoryService.addProductCategoryList(productCategoryList);
                //判断是否添加成功
                if(pe.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
                    //成功返回true
                    map.put("success",true);
                }else {
                    //失败返回false并返回错误信息
                    map.put("success",false);
                    map.put("errMsg",pe.getStateInfo());
                }
            }catch (Exception e){
                //失败返回false并返回错误信息
                map.put("success",false);
                map.put("errMsg",e.toString());
            }
        }else {
            map.put("success",false);
            map.put("errMsg","请输入至少一个商品类别");
        }
        return map;
    }

    /**
     * 删除商品类别信息
     * 将此类别下的商品里的类别id设置为空，再删除掉该商品类别
     * @param productCategoryId
     * @return
     */
    @RequestMapping(value = "/removeproductcategory",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> removeProductCategory(Integer productCategoryId){
        Map<String,Object> map = new HashMap<>();
        int shopId = 15;
        if(productCategoryId!=null&&productCategoryId>0){
            try {
                //调用删除
                ProductCategoryExecution pe = productCategoryService.deleteProductCategory(shopId, productCategoryId);
                //判断是否删除成功
                if (pe.getState()== ProductCategoryStateEnum.SUCCESS.getState()) {
                    map.putIfAbsent("success",true);
                }else {
                    //失败返回错误信息
                    map.putIfAbsent("success",false);
                    map.put("errMsg",pe.getStateInfo());
                }
            }catch (Exception e){
                map.putIfAbsent("success",false);
                map.put("errMsg",e.toString());
            }
        }else {
            map.put("success",false);
        }
        return map;
    }
}
