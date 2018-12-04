package cn.shop.web.potal.controller;

import cn.shop.pojo.*;
import cn.shop.potal.service.AwardPotalService;
import cn.shop.potal.service.ProductPotalService;
import cn.shop.potal.service.UserAwardMapProtalService;
import cn.shop.potal.service.UserShopMapPotalService;
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
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class AwardPotalController {
    @Autowired
    private AwardPotalService awardPotalService;
    @Autowired
    private UserShopMapPotalService userShopMaoPotalService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductPotalService productPotalService;
    @Autowired
    private UserAwardMapProtalService userAwardMapProtalService;
    /**
     * @Description:    根据店铺Id查询店铺奖品
     * @Author:         oy
     * @CreateDate:     2018/11/26 0026 下午 1:57
     */
    @RequestMapping("/getbyshopidaward")
    @ResponseBody
    public Map<Object,Object> getByShopIdAward(HttpServletRequest request) throws Exception {
        Map<Object,Object> map=new HashMap<Object,Object>();
        //从Seesion中获取用户
        //获取店铺Id
        Integer shopid= HttpServletRequestUtil.getInt(request,"shopId");
        //第几页
        Integer pagenum= HttpServletRequestUtil.getInt(request,"pageIndex");
        //每页显示几行
        Integer pagesize= HttpServletRequestUtil.getInt(request,"pageSize");
        //商品名字
        String name=new String(request.getParameter("productName").getBytes("ISO-8859-1"),"utf-8");
        //创建一个积分对象
        UserShopMap userShopMap=null;
        //积分
        Integer point=0;
        if(shopid!=null&&shopid>-1){
            //查询此用户有在这个店铺是否有积分
            if(userShopMaoPotalService.selectByExample(11,shopid,null,null).size()>0){
                //得到积分
             userShopMap=userShopMaoPotalService.selectByExample(11,shopid,null,null).get(0);
             point=userShopMap.getPoint();
            }
            //将数据返回到页面
            map.put("point",point);
            map.put("awardlist",awardPotalService.selectByExample(shopid,name,pagenum,pagesize));
           //查询行数
            map.put("count",awardPotalService.countByExample(shopid,name));
            map.put("success",true);
        }
        return map;
    }
    /**
     * @Description:    兑换奖品
     * @Author:         oy
     * @CreateDate:     2018/11/28 0028 上午 11:03
     */
    @RequestMapping(value = "/convertAward",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> convertAward(@Param("productId") Integer productId){
        Map<String,Object> map=new HashMap<String,Object>();
        //从Seesion中获取用户
        //查询此奖品需要多少积分
        Award award=awardPotalService.selectByPrimaryKey(productId);
        //查询此奖品属于哪个商铺
        Shop shop=shopService.getByShopId(award.getShopId());
        //创建一个积分对象
        UserShopMap userShopMap=null;
        //查询是在此店铺是否有积分
        if(userShopMaoPotalService.selectByExample(11,shop.getShopId(),null,null).size()>0){
            //得到商铺积分
            userShopMap=userShopMaoPotalService.selectByExample(11,shop.getShopId(),null,null).get(0);
            //判断积分是否足够
            if(userShopMap.getPoint()>award.getPoint()||userShopMap.getPoint()==award.getPoint()){
                //修改用户在店铺的积分
                userShopMap.setPoint(userShopMap.getPoint()-award.getPoint());
                //修改时间
                userShopMap.setCreateTime(new Date());
                if(userShopMaoPotalService.updateByExample(userShopMap)>0){
                    //添加积分消费记录
                    UserAwardMap userAwardMap=new UserAwardMap();
                    userAwardMap.setUserId(11);
                    userAwardMap.setAwardId(award.getAwardId());
                    userAwardMap.setShopId(shop.getShopId());
                    userAwardMap.setUserName("音策");
                    userAwardMap.setAwardName(award.getAwardName());
                    userAwardMap.setExpireTime(award.getExpireTime());
                    userAwardMap.setCreateTime(new Date());
                    userAwardMap.setPoint(award.getPoint());
                    userAwardMap.setUsedStatus(1);
                    if(userAwardMapProtalService.insertSelective(userAwardMap)>0){
                        map.put("success",true);
                        map.put("msg","兑换成功");
                    }
                }

            }else{
                map.put("success",false);
                map.put("msg","积分不足");
            }
        }else{
            map.put("success",false);
            map.put("msg","你在此店铺没有积分");
        }
        return map;

    }
}
