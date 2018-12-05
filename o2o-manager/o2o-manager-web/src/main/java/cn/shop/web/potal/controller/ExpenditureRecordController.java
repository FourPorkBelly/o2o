package cn.shop.web.potal.controller;

import cn.shop.pojo.PersonInfo;
import cn.shop.pojo.UserProductMap;
import cn.shop.potal.service.UserAwardMapProtalService;
import cn.shop.potal.service.UserProductMapPotalService;
import cn.shop.potal.service.UserShopMapPotalService;
import cn.shop.shop.service.ShopService;
import cn.shop.utlis.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ExpenditureRecordController {
    @Autowired
    private UserProductMapPotalService userProductMapPotalService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private UserShopMapPotalService userShopMapPotalService;
    @Autowired
    private UserAwardMapProtalService userAwardMapProtalService;
    /**
     * @Description:   查询用户的消费记录
     * @Author:         oy
     * @CreateDate:     2018/11/28 0028 下午 2:08
     */
    @RequestMapping("/listuserproductmapsbycustomer")
    @ResponseBody
    public Map<String,Object> getUserProductMapList(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        //从session获取用户
        PersonInfo user = (PersonInfo) request.getSession()
                .getAttribute("user");
        //得到页码
        Integer pagenum= HttpServletRequestUtil.getInt(request,"pageIndex");
        //获取每页显示行数
        Integer pageSize= HttpServletRequestUtil.getInt(request,"pageSize");
        if(pagenum!=null&&pagenum>-1&&pageSize!=null&&pageSize>-1){
            //创建一个用户消费对象
            UserProductMap userProductMap=new UserProductMap();
            userProductMap.setUserId(user.getUserId());
            map.put("success",true);
            //查询该用户的消费记录条数 用户消费对象
            map.put("count",userProductMapPotalService.countByExample(userProductMap));
            //查询该用户的消费记录行数 用户消费对象 页码 数量
            map.put("userProductMapList", userProductMapPotalService.selectByExample(userProductMap,pagenum,pageSize));

        }
        return  map;
    }
    /**
     * @Description:    查询用户在那些店铺有积分
     * @Author:         oy
     * @CreateDate:     2018/11/28 0028 下午 2:43
     */
    @RequestMapping("/listusershopmapsbycustomer")
    @ResponseBody
    public Map<String,Object> getUserShopMapList(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        //从session获取用户
        PersonInfo user = (PersonInfo) request.getSession()
                .getAttribute("user");
        //获取页码
        Integer pageIndex=HttpServletRequestUtil.getInt(request,"pageIndex");
        //获取行数
        Integer pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
        if(pageIndex!=null&&pageIndex>-1&&pageSize!=null&&pageSize>-1){
            map.put("success",true);
            //得到用户在那些店铺有积分 用户id 页码 数量
            map.put("userShopMapList",userShopMapPotalService.selectByExample(user.getUserId(),null,pageIndex,pageSize));
            //得到用户在那些店铺有积分的数量
            map.put("count",userShopMapPotalService.countByExample(user.getUserId()));
        }
        return  map;
    }
    /**
     * @Description:   积分消费记录
     * @Author:         oy
     * @CreateDate:     2018/11/29 0029 上午 8:46
     */
    @RequestMapping("/listuserawardmapsbycustomer")
    @ResponseBody
    public Map<String,Object> getUserAwardMapList(HttpServletRequest request){
        Map<String,Object> map=new HashMap<String,Object>();
        //从session获取用户
        PersonInfo user = (PersonInfo) request.getSession()
                .getAttribute("user");
        //获取页码
        Integer pageIndex=HttpServletRequestUtil.getInt(request,"pageIndex");
        //获取行数
        Integer pageSize=HttpServletRequestUtil.getInt(request,"pageSize");
        if(pageIndex!=null&&pageIndex>-1&&pageSize!=null&&pageSize>-1){
            //得到消费记录 用户id 页码 数量
            map.put("userAwardMapList",userAwardMapProtalService.selectByExample(user.getUserId(),pageIndex,pageSize));
            map.put("success",true);
            //得到消费记录行数
            map.put("count",userAwardMapProtalService.countByExample(user.getUserId()));
        }
        return  map;
    }
}
