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
        Product product = new Product();
//        将shopid存入product对象
        product.setShopId(shopId);
        ProductExecution pe = productService.queryProduct(product);
//        将list放入map中
        modelMap.put("productList", pe.getProductList().getList());
//        将商品数放入map
        modelMap.put("count", pe.getCount());
        modelMap.put("success", true);
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
//        从session中得到店铺信息
//        Shop currentShop = (Shop) request.getSession().getAttribute(
//                "currentShop");

//        测试的值
        Shop currentShop=new Shop();
        currentShop.setShopId(20);
//        如果店铺不为空并且店铺ID部位空，查询该店铺的商铺类别
        if ((currentShop != null) && (currentShop.getShopId() != null)) {
            List<ProductCategory> productCategoryList=productCategoryService.getProductCategoryList(shopId);
            for (ProductCategory p:productCategoryList){
                System.out.println(p.getProductCategoryName());
            }
//            将商品类别存入map
            modelMap.put("productCategoryList",productCategoryList);
//            返回查询成功，便于判断
            modelMap.put("success",true);
        } else {
            modelMap.put("success",false);
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
    private Map<String, Object> addProduct(Product product,String imgAddr) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
//        判断验证码是否输入正确
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
//        得到shop 以及shopid
//        Shop currentShop = (Shop) request.getSession().getAttribute(
//                "currentShop");
        Shop currentShop=new Shop();
        currentShop.setShopId(20);
//        判断product是否传入值，并且shopid不为空
        if (product!=null&&currentShop.getShopId()!=null){
            try {
                product.setShopId(currentShop.getShopId());
//               默认设置商品上架
                product.setEnableStatus(1);
//                将图片地址插入product_img表 pe对象（详情图）
                ProductExecution pe=productService.addProduct(product,imgAddr);
//                判断结果状态是否等于枚举(0操作成功)
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
//                    添加成功则返回成功

                    modelMap.put("success", true);
                    modelMap.put("errMsg", "添加成功");
                } else {
//                    添加失败返回false的success  并将错误信息
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
    private Map<String, Object> getProductById(@RequestParam(value = "productId") int productId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (productId > -1) {
            Product product = productService.getProductById(productId);
            List<ProductCategory> productCategoryList = productCategoryService
                    .getProductCategoryList(product.getShopId());
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
        return modelMap;
    }

    @ResponseBody
    @RequestMapping(value="/updateProduct",method = RequestMethod.POST)
    public Map<String, Object> modifyProduct(Product product,@RequestParam(value = "imgAddrs")String imgAddr){
        System.out.println(product);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (product != null) {
            try {
                Product rs = productService.modifyProduct(product,imgAddr);
                if (rs!=null) {
//                    如果插入成功则返回成功
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "修改失败");
                }
            } catch (RuntimeException e) {
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
     * 下架商品
     * @param productId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteproduct",method = RequestMethod.GET)
    public Map<String, Object> deleteproduct(Integer productId){
        Map<String, Object> modelMap = new HashMap<String, Object>();

        if (productId!=0){
//
            int rs=productService.deleteProduct(productId);
            if (rs>0){
                modelMap.put("success", false);
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "下架失败");
            }
        }
        return modelMap;
    }
}
