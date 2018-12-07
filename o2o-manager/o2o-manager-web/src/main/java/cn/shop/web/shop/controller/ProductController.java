package cn.shop.web.shop.controller;

import cn.shop.dto.ProductExecution;
import cn.shop.enums.ProductStateEnum;
import cn.shop.pojo.Product;
import cn.shop.pojo.ProductCategory;
import cn.shop.pojo.Shop;
import cn.shop.shop.service.ProductCategoryService;
import cn.shop.shop.service.ProductService;
import cn.shop.utlis.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yzg
 * @date 2018/11/19 - 13:42
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    private static final int IMAGEMAXCOUNT = 6;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;
    /**
     * 根据商铺id查询商品（预分页）
     *
     * @param pageIndex
     * @param pageSize
     * @param shopId
     * @return
     */
    @RequestMapping(value = "/queryProduct", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductsByShop(@RequestParam(value = "pageIndex") Integer pageIndex, @RequestParam(value = "pageSize") Integer pageSize, @RequestParam(value = "shopId") Integer shopId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //从session中获取shop信息
        Shop currentShop = (Shop) session.getAttribute("currentShop");
        if(currentShop!=null){
            try {
                Product product = new Product();
                //将shopid存入product对象
                product.setShopId(currentShop.getShopId());
                ProductExecution pe = productService.queryProduct(product);
                List<Product> productList = pe.getProductList().getList();
                //如果查询到数据就把数据保存到session中
                if(productList!=null&&productList.size()>0){
                    session.setAttribute("productList",productList);
                }
                //将list放入map中
                modelMap.put("productList", productList);
                //将商品数放入map
                modelMap.put("count", pe.getCount());
                modelMap.put("success", true);
            }catch (Exception e){
                e.printStackTrace();
                modelMap.put("success",false);
                modelMap.put("errMsg","发生未知错误");
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","信息错误");
        }

        return modelMap;
    }

    /**
     * 根据商铺id查询商品类别
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getproductcategorylistbyshopId", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductCategoryListByShopId(
            HttpServletRequest request, Integer shopId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //从session中得到店铺信息
        Shop currentShop = (Shop) request.getSession().getAttribute(
                "currentShop");


//        如果店铺不为空并且店铺ID部位空，查询该店铺的商铺类别
        if ((currentShop != null) && (currentShop.getShopId() != null)) {
            List<ProductCategory> productCategoryList = productCategoryService
                    .getProductCategoryList(currentShop.getShopId());
//            将商品类别存入map
            modelMap.put("productCategoryList", productCategoryList);
//            返回查询成功，便于判断
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
//            返回错误信息
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    /**
     * 添加商品信息
     *
     * @return
     */
    @RequestMapping(value = "/modifyproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(Product product,String imgAddrs) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
//        判断验证码是否输入正确
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
//        得到shop 以及shopid
        Shop currentShop = (Shop) request.getSession().getAttribute(
                "currentShop");
//        判断product是否传入值，并且shopid不为空
        if (product!=null&&currentShop.getShopId()!=null){
            try {
                product.setShopId(currentShop.getShopId());
//               默认设置商品上架
                product.setEnableStatus(1);
//                将图片地址插入product_img表（详情图）
                ProductExecution pe=productService.addProduct(product,imgAddrs);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                    modelMap.put("errMsg", "添加成功");
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            }catch (RuntimeException e){
                e.printStackTrace();
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
            }
        }
        else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    /**
     * 根据商品id 获得商品信息
     * @param productId
     * @return
     */
    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductById(@RequestParam(value = "productId",defaultValue = "0") Integer productId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //判断productId是否存在
        if(isProductId(productId)){
            //从session中获取shop
            Shop currentShop = (Shop) session.getAttribute("currentShop");
            if (currentShop!=null) {
                //创建查询条件
                Product product = productService.getProductById(productId);
                System.out.println("product:"+product);
                //获得目录信息
                List<ProductCategory> productCategoryList = productCategoryService
                        .getProductCategoryList(currentShop.getShopId());

//            将商品类别存入map
                modelMap.put("productCategoryList", productCategoryList);
//            商品对象存入map
                modelMap.put("product", product);
//            返回查询成功，便于判断
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
            }
        }else{
            //如果不存在就返回错误信息
            modelMap.put("success", false);
            modelMap.put("errMsg", "信息错误");
        }

        return modelMap;
    }

    /**
     *  修改
     * @param product
     * @param imgAddrs
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/updateProduct")
    public Map<String, Object> modifyProduct(Product product,String imgAddrs){
        System.out.println("imgAddrs:"+imgAddrs);
        System.out.println(product);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (product != null) {
            try {

                Product rs = productService.updateProduct(product,imgAddrs);

                System.out.println("修改商品——————————————————————————————————————————————-");
                if (rs!=null) {
                    modelMap.put("product",rs);
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "修改失败");
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    /**
     *判断session中是否存在该商品
     * @param productId
     * @return
     */
    private boolean isProductId(Integer productId){
        //从session中获取productList
        List<Product> productList = (List<Product>) session.getAttribute("productList");
        //如果找到就返回true
        if(productList!=null&&productList.size()>0){
            for (Product product : productList) {
                if(product.getProductId()==productId){
                    return true;
                }
            }
        }
        return false;
    }
}
