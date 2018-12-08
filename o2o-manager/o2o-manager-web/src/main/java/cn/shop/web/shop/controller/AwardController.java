package cn.shop.web.shop.controller;

import cn.shop.dto.AwardExecution;
import cn.shop.enums.AwardStateEnum;
import cn.shop.pojo.Award;
import cn.shop.pojo.Shop;
import cn.shop.shop.service.AwardService;
import cn.shop.utlis.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zmt
 * @date 2018/12/8 - 23:25
 */
@Controller
@RequestMapping("/shop")
public class AwardController {
    @Autowired
    private HttpSession session;
    @Autowired
    private AwardService awardService;
    @Autowired
    private HttpServletRequest request;
    /**
     * 根据shopid获取奖品列表
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/listawardsbyshop")
    @ResponseBody
    public Map<String,Object> getListAward(Integer pageIndex,Integer pageSize){
        Map<String,Object> map = new HashMap<>();
        //从session中获取shop
        Shop currentShop = (Shop) session.getAttribute("currentShop");
        if(currentShop!=null&&currentShop.getShopId()!=null){
            AwardExecution execution = awardService.getAwardListByShopId(currentShop.getShopId(), pageIndex, pageSize);
            if(execution.getState()== AwardStateEnum.SUCCESS.getState()){
                map.put("success",true);
                map.put("awardList",execution.getAwardList());
                //将结果查询出来的结果集存入session
                session.setAttribute("awardList",execution.getAwardList());
            }else {
                map.put("success",false);
                map.put("errMsg","后台异常");
            }
        }else {
            map.put("success",false);
            map.put("errMsg","商铺信息错误");
        }
        return map;
    }
    /**
     * 根据id查询奖品
     * @param awardId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getawardbyid")
    public Map<String,Object> getAwardById(Integer awardId){
        Map<String,Object> map = new HashMap<>();
        //判断该奖品是否存在
        if(isAward(awardId)){
            AwardExecution execution = awardService.getAwardById(awardId);
            if(execution.getState()==AwardStateEnum.SUCCESS.getState()){
                map.put("success",true);
                map.put("award",execution.getAward());
                //将查询出来的奖品信息存入session
                session.setAttribute("currentAward",execution.getAward());
            }else {
                map.put("success",false);
                map.put("errMsg",execution.getStateInfo());
            }
        }else {
            map.put("success",false);
            map.put("errMsg",AwardStateEnum.OFFLINE.getStateInfo());
        }
        return map;
    }

    /**
     * 修改奖品信息
     * @param award
     * @return
     */
    @RequestMapping(value = "/modifyaward",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateAward(Award award){
        Map<String,Object> map = new HashMap<>();
        System.out.println("award:"+award);
        if(!CodeUtil.checkVerifyCode(request)){
            map.put("success",false);
            map.put("errMsg","请输入正确的验证码");
            return map;
        }
        //从session中获取奖品信息
        Award currentAward = (Award) session.getAttribute("currentAward");
        if(currentAward!=null&&currentAward.getAwardId()!=null){
            award.setAwardId(currentAward.getAwardId());
            AwardExecution execution = awardService.updateAward(award);
            if (execution.getState()== AwardStateEnum.SUCCESS.getState()) {
                session.removeAttribute("currentAward");
                map.put("success",true);
                map.put("errMsg",AwardStateEnum.SUCCESS.getStateInfo());
            }else {
                map.put("success",false);
                map.put("errMsg",AwardStateEnum.OFFLINE.getStateInfo());
            }
        }else {
            map.put("success",false);
            map.put("errMsg",AwardStateEnum.EMPTY.getStateInfo());
        }
        return map;
    }

    /**
     * 添加奖品
     * @param award
     * @return
     */
    @RequestMapping(value = "/addaward",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addAward(Award award){
        Map<String,Object> map = new HashMap<>();
        //从session中获取shop
        Shop currentShop = (Shop) session.getAttribute("currentShop");
        if (currentShop!=null&&currentShop.getShopId()!=null) {
            award.setShopId(currentShop.getShopId());
            AwardExecution execution = awardService.addAward(award);
            if (execution.getState()== AwardStateEnum.SUCCESS.getState()) {
                map.put("success",true);
                map.put("errMsg",execution.getStateInfo());
            }else {
                map.put("success",false);
                map.put("errMsg",execution.getStateInfo());
            }
        }else {
            map.put("success",false);
            map.put("errMsg","商铺信息错误");
        }
        return map;
    }


    /**
     * 上下架奖品
     * @param awardId
     * @param enableStatus
     * @return
     */
    @RequestMapping("/updatestatusaward")
    @ResponseBody
    public Map<String,Object> updateStatusAward(Integer awardId,Integer enableStatus){
        System.out.println("awardId:"+awardId);
        Map<String,Object> map = new HashMap<>();
        if(isAward(awardId)){
            AwardExecution execution = awardService.updateStatusAward(awardId, enableStatus);
            if (execution.getState()== AwardStateEnum.SUCCESS.getState()) {
                map.put("success",true);
                map.put("errMsg",execution.getStateInfo());
            }else if(execution.getState()==AwardStateEnum.ADDED_ERROR.getState()){
                map.put("success",false);
                map.put("errMsg",execution.getStateInfo());
            }else {
                map.put("success",false);
                map.put("errMsg",execution.getStateInfo());
            }
        }else {
            map.put("success",false);
            map.put("errMsg",AwardStateEnum.OFFLINE.getStateInfo());
        }
        return map;
    }
    /**
     * 根据awardId判断是否存在
     * @param awardId
     * @return
     */
    private boolean isAward(Integer awardId){
        //从session中获取awarList
        List<Award> awardList = (List<Award>) session.getAttribute("awardList");
        if(awardList!=null&&awardList.size()>0){
            for (Award award : awardList) {
                if(awardId==award.getAwardId()){
                    return true;
                }
            }
        }
        return false;
    }
}
