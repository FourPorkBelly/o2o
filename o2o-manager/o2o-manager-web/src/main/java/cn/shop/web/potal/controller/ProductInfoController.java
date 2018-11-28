package cn.shop.web.potal.controller;

import cn.shop.pojo.*;
import cn.shop.potal.service.ProductPotalService;
import cn.shop.potal.service.UserProductMapPotalService;
import cn.shop.potal.service.UserShopMapPotalService;
import cn.shop.shop.service.ProductCategoryService;
import cn.shop.shop.service.ShopService;
import cn.shop.utlis.HttpServletRequestUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ProductInfoController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductPotalService productPotalService;
    @Autowired
    private  UserShopMapPotalService userShopMapPotalService;
    @Autowired
    private UserProductMapPotalService userProductMapPotalService;
    @RequestMapping("/listshopdetailpageinfo")
    @ResponseBody
    /**
     * @Description: 根据id查询店铺 以及店铺类别
     * @Author: oy
     * @CreateDate: 2018/11/23 0023 下午 2:21
     */
    public Map<String, Object> getByIdShop(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Shop shop = null;
        List<ProductCategory> productCategories = null;
        //获取页面的ID
        Integer shopid = HttpServletRequestUtil.getInt(request, "shopId");
        //判断是否存在
        if (shopid != null && shopid > -1) {
            //得到店铺
            shop = shopService.getByShopId(shopid);
            productCategories = productCategoryService.getProductCategoryList(shopid);
            //设置数据到页面
            map.put("shop", shop);
            map.put("productCategoryList", productCategories);
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }
    /**
     * @Description:   分页、按条件查询商品
     * @Author:         oy
     * @CreateDate:     2018/11/26 0026 上午 8:57
     */
    @RequestMapping("/listproductsbyshop")
    @ResponseBody
    public Map<String, Object> listproductsbyshop(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        //商品id
        Integer shopid = HttpServletRequestUtil.getInt(request, "shopId");
        //当前页
        Integer pagenum = HttpServletRequestUtil.getInt(request, "pageIndex");
        //显示多少行
        Integer pagesize = HttpServletRequestUtil.getInt(request, "pageSize");
        //判断是否为空
        if (shopid != null && shopid > -1 && pagenum != null && pagenum > -1 && pagesize != null && pagesize > -1) {
            //类别id
            Integer productCategoryId = HttpServletRequestUtil.getInt(request, "productCategoryId");
            //商品名字
            String productName = new String(request.getParameter("productName").getBytes("ISO-8859-1"), "utf-8");
           //将数据设置到对象中
            Product product=new Product();
            product.setShopId(shopid);
            product.setProductCategoryId(productCategoryId);
            product.setProductName(productName);
            //查询商品分页将数据返回到页面
            map.put("productList",productPotalService.selectByExample(product,pagenum,pagesize));
            //查询行数
            map.put("count",productPotalService.countByExample(product));
            map.put("success",true);
        }else {
            map.put("success",false);
        }
        return map;

    }
    @RequestMapping("/listproductdetailpageinfo")
    @ResponseBody
    /**
     * @Description:    根据id查看商品详情
     * @Author:         oy
     * @CreateDate:     2018/11/23 0023 下午 3:57
     */
    public Map<String, Object> listproductdetailpageinfo(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        //获取productid
        Integer productid= HttpServletRequestUtil.getInt(request, "productId");
        if(productid!=null&&productid>-1){
            //根据id查询商品
            map.put("product",productPotalService.selectByPrimaryKey(productid));
            map.put("success",true);
        }else{
            map.put("success",false);
        }
        return  map;
    }
    /**
     * @Description:    购买商品 添加积分 添加消费记录
     * @Author:         oy
     * @CreateDate:     2018/11/27 0027 上午 10:50
     */
    @RequestMapping(value = "/purchaseproduct",method = RequestMethod.POST)
    @ResponseBody
    public  Map<String, Object> purchaseproduct(@Param("productId") Integer productId){
        Map<String, Object> map = new HashMap<String, Object>();
        //从Session中获取用户
        //查询此商品的积分
        Product product=productPotalService.selectByPrimaryKey(productId);
        //查询店铺

        Shop shop=shopService.getByShopId(product.getShopId());
        //查询改用户在此店铺是否有积分
        UserShopMap userShopMap=null;
        //创建一个用户消费记录
        UserProductMap userProductMap=new UserProductMap();
        userProductMap.setUserId(11);
        userProductMap.setProductId(product.getProductId());
        userProductMap.setShopId(shop.getShopId());
        userProductMap.setUserName("音策");
        userProductMap.setProductName(product.getProductName());
        userProductMap.setPoint(product.getPoint());
        userProductMap.setCreateTime(new Date());

        if(userShopMapPotalService.selectByExample(11,shop.getShopId()).size()>0){
            //赋值
            userShopMap=userShopMapPotalService.selectByExample(11,shop.getShopId()).get(0);
            //原因积分加上现有积分
            userShopMap.setPoint(userShopMap.getPoint()+product.getPoint());
            //修改在以有的用户上加积分
            if(userShopMapPotalService.updateByExample(userShopMap)>0){
                //新增消费记录
                if(userProductMapPotalService.insertSelective(userProductMap)>0){
                    map.put("success",true);
                }else{
                    map.put("success",false);
                }

            }else{
                map.put("success",false);
            }
        }else{
            userShopMap=new UserShopMap();
            userShopMap.setPoint(product.getPoint());
            userShopMap.setCreateTime(new Date());
            userShopMap.setShopName(shop.getShopName());
            userShopMap.setShopId(shop.getShopId());
            userShopMap.setUserId(11);
            userShopMap.setUserName("音策");
            //新增积分
            if(userShopMapPotalService.insertSelective(userShopMap)>0){
                //新增消费记录
                if(userProductMapPotalService.insertSelective(userProductMap)>0){
                    map.put("success",true);
                }else{
                    map.put("success",false);
                }
            }else{
                map.put("success",false);
            }
        }
        return  map;
    }
}
