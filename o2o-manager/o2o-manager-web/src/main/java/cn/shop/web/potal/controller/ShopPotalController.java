package cn.shop.web.potal.controller;


import cn.shop.pojo.Area;
import cn.shop.pojo.Shop;
import cn.shop.pojo.ShopCategory;
import cn.shop.potal.service.AreaPotalService;
import cn.shop.potal.service.ShopCategoryService;

import cn.shop.potal.service.ShopServicePotalService;
import cn.shop.utlis.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shop")
public class ShopPotalController {
    @Autowired
    private ShopServicePotalService shopServicePotalService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaPotalService areaService;

    @RequestMapping(value = "/shopListinfo", method = RequestMethod.GET)
    @ResponseBody
    /**
     * @Description: 按照parentId分查询几级类别 和区域信息
     * @Author: oy
     * @CreateDate: 2018/11/22 0022 上午 8:32
     */
    public Map<String, Object> getShopCategoryAndAreaList(HttpServletRequest request) {
        //用于页面返回数据
        Map<String, Object> map = new HashMap<String, Object>();
        //获取页面传回来的parentId
        Integer parentId = HttpServletRequestUtil.getInt(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        //父级iD为空的时候查询一级分类
        if (parentId == -1) {
            try {
                //获取一级分类
                shopCategoryList = shopCategoryService.getShopCategoryListByparent(null);
            } catch (Exception e) {
                map.put("success", false);
                map.put("errMsg", e.getMessage());
            }

        } else {
            //否则查询父类为ParentId的子类
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setParentId(parentId);
            shopCategoryList = shopCategoryService.getShopCategoryListByparent(shopCategory);
        }

        List<Area> areas = null;
        try {
            //获取区域信息
            areas = areaService.selectByAreaExample();
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", e.getMessage());
        }
        //返回数据
        map.put("success", true);
        map.put("shopCategoryList", shopCategoryList);
        map.put("areaList", areas);
        return map;
    }

    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    /**
     * @Description: 根据条件查询商铺及分页
     * @Author: oy
     * @CreateDate: 2018/11/22 0022 上午 8:46
     */
    public Map<String, Object> listShops(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");
        Map<String, Object> map = new HashMap<String, Object>();
        //页码
        Integer pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        //行数
        Integer pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (pageIndex != null && (pageIndex > -1) && pageSize != null && (pageSize > -1)) {
            //一级类别id
            Integer parentId = HttpServletRequestUtil.getInt(request, "parentId");
            //二级类比
            Integer shopCategoryId = HttpServletRequestUtil.getInt(request, "shopCategoryId");
            //区域id
            Integer areaId = HttpServletRequestUtil.getInt(request, "areaId");
            //店铺名
            String shopName = new String(request.getParameter("shopName").getBytes("ISO-8859-1"), "utf-8");
           // String shopName = HttpServletRequestUtil.getString(request, "shopName");
            //将获取到的数据设置到对象中
            Shop shop = new Shop();
            //设置状态为1
            shop.setEnableStatus(1);
            shop.setShopName(shopName);
            ShopCategory shopCategory = new ShopCategory();
            if (parentId != null && parentId > -1) {
                shopCategory.setParentId(parentId);
            }
            if (shopCategoryId != null && shopCategoryId > -1) {
                shopCategory.setShopCategoryId(shopCategoryId);
            }
            shop.setShopCategory(shopCategory);
            Area area = new Area();
            if (areaId != null && areaId > -1) {
                area.setAreaId(areaId);
            }
            shop.setArea(area);
            System.out.println("一级类别id"+parentId+"二级类比"+shopCategoryId+"区域id"+areaId+"店铺名"+shopName);
            //返回数据
            map.put("success", true);
            map.put("parentId",parentId);
            map.put("shopList", shopServicePotalService.queryShopList(shop, (pageIndex - 1) * pageSize, pageSize));
            map.put("count", shopServicePotalService.countByExample(shop));
        } else {
            map.put("success", false);
        }
        return map;
    }
}
