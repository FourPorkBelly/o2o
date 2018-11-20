package cn.shop.web.shop.controller;

import cn.shop.dto.ProductExecution;
import cn.shop.enums.ProductStateEnum;
import cn.shop.pojo.Product;
import cn.shop.pojo.Shop;
import cn.shop.shop.service.ProductService;
import cn.shop.utlis.CodeUtil;
import cn.shop.utlis.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
//    @Autowired
//    private ProductCategoryService productCategoryService;
    @RequestMapping(value="/queryProduct",method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductsByShop(@RequestParam(value = "pageIndex")Integer pageIndex,@RequestParam(value = "pageSize")Integer pageSize,@RequestParam(value = "shopId")Integer shopId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Product product=new Product();
//        将shopid存入product对象
        product.setShopId(shopId);
        ProductExecution pe = productService.queryProduct(product);
//        将list放入map中
        modelMap.put("productList", pe.getProductList().getList());
//        将商品数放入map
        modelMap.put("count", pe.getCount());modelMap.put("success", true);
        return modelMap;
    }
    @RequestMapping("/addProduct")
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request,
                "productStr");
        MultipartHttpServletRequest multipartRequest = null;
        CommonsMultipartFile thumbnail = null;
        List<CommonsMultipartFile> productImgs = new ArrayList<CommonsMultipartFile>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            if (multipartResolver.isMultipart(request)) {
                multipartRequest = (MultipartHttpServletRequest) request;
                thumbnail = (CommonsMultipartFile) multipartRequest
                        .getFile("thumbnail");
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile productImg = (CommonsMultipartFile) multipartRequest
                            .getFile("productImg" + i);
                    if (productImg != null) {
                        productImgs.add(productImg);
                    }
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        try {
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (product != null && thumbnail != null && productImgs.size() > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute(
                        "currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShopId(shop.getShopId());
                ProductExecution pe = productService.addProduct(product);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
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
}
