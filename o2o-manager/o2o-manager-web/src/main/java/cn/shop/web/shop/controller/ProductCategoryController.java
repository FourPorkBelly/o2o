package cn.shop.web.shop.controller;

import cn.shop.dto.Result;
import cn.shop.enums.ProductCategoryStateEnum;
import cn.shop.pojo.ProductCategory;
import cn.shop.shop.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

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
}
