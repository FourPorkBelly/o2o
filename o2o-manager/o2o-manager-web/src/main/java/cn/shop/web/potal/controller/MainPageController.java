package cn.shop.web.potal.controller;

import cn.shop.pojo.AeadLine;
import cn.shop.pojo.ShopCategory;
import cn.shop.potal.service.AeadLineService;

import cn.shop.potal.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:   前台
 * @Author:         oy
 * @CreateDate:     2018/11/21 0021 上午 9:55
 */
@Controller
@RequestMapping("/frontend")
public class MainPageController {
    @Autowired
    private AeadLineService aeadLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @RequestMapping(value = "/listmainpageinfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> listMainPageInfo(){
        Map<String,Object> map=new HashMap<String,Object>();

        List<ShopCategory> shopCategoryList=new ArrayList<ShopCategory>();
        try {
            //获取没有父级的商品类别
            shopCategoryList= shopCategoryService.getShopCategoryListByparent(null);
            //将数据返回到页面
            map.put("shopCategoryList",shopCategoryList);
        }catch (Exception e){
            //报错
            map.put("success",false);
            map.put("errMsg",e.getMessage());
        }
        List<AeadLine> aeadLines=new ArrayList<AeadLine>();
        try {
            //获取状态为可用的(1)头条列表
            AeadLine aeadLine=new AeadLine();
            aeadLine.setEnableStatus(1);
            aeadLines=aeadLineService.queryAeadLine(aeadLine);
            map.put("headLineList",aeadLines);
        }catch (Exception e){
            map.put("success",false);
            map.put("errMsg",e.getMessage());
        }
        map.put("success",true);
        return  map;
    }
}
