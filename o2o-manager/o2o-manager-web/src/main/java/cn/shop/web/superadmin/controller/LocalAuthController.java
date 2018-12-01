package cn.shop.web.superadmin.controller;

import cn.shop.cms.service.LocalService;
import cn.shop.mapper.PersonInfoMapper;
import cn.shop.pojo.LocalAuth;
import cn.shop.pojo.PersonInfo;
import cn.shop.pojo.PersonInfoExample;
import cn.shop.pojo.Shop;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yzg
 * @date 2018/11/26 - 15:16
 */
@Controller
@RequestMapping("/superadmin")
public class LocalAuthController {
    @Autowired
    private LocalService localService;
    @Autowired
    private PersonInfoMapper personInfoMapper;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    /**
     * 管理员登录
     */
    @ResponseBody
    @RequestMapping(value = "/loginAdmin",method = RequestMethod.POST)
    public Map<String,Object> adminLogin(LocalAuth localAuth){
        Map<String,Object> map = new HashMap<>();
        if (localAuth!=null){
            LocalAuth user=localService.login(localAuth);
            if (user!=null){
                System.out.println("成功");
                request.getSession().setAttribute("user",user);
                map.put("user",user);
                map.put("msg","y");
            }else {
                map.put("msg","失败");
            }
        }
        return map;
    }

    /**
     * 得到当前用户名以及头像
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getLoginName")
    public Map<String,Object> getLoginName(){
        Map<String,Object> map=new HashMap<>();
//        从session得到登录的账号
        LocalAuth user=(LocalAuth)request.getSession().getAttribute("user");
        String loginName=user.getUserName();
//        得到用户对象
        PersonInfo personInfo = personInfoMapper.selectByPrimaryKey(user.getUserId());
        try {
//            将账号名，用户头像放入map
            map.put("userName",loginName);
            map.put("img",personInfo.getProfileImg());
        }catch (Exception e){
            map.put("loginName","***");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping(value = "/outLogin")
    public String outLogin(){
        request.getSession().removeAttribute("user");
        return "/superadmin/login";
    }
    @RequestMapping("/queryPersonList")
    @ResponseBody
    public String queryPerson(PersonInfo personInfo, @RequestParam(value = "page", required = false)Integer pageIndex,@RequestParam(value = "limit", required = false) Integer pageSize){
        response.setContentType("text/html;charset=utf-8");
        PersonInfoExample example=new PersonInfoExample();
        PersonInfoExample.Criteria criteria=example.createCriteria();
        if (personInfo!=null){
            if (personInfo.getName()!=null){
                criteria.andNameLike(personInfo.getName());
            }
        }
        try {
            PrintWriter out=response.getWriter();
            List<PersonInfo> personInfoList = localService.queryPersonInfo(personInfo,pageIndex,pageSize);
            JSONArray jsonArray = new JSONArray();
        for (PersonInfo s:personInfoList){
                if (s!=null){
                    JSONObject jsonObject = new JSONObject();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String createTime = formatter.format(s.getCreateTime());


                    jsonObject.put("userId",s.getUserId());
                    jsonObject.put("name",s.getName());
                    if (s.getBirthday()==null){
                        jsonObject.put("birthday","未填写");
                    }else {
                        jsonObject.put("birthday", s.getBirthday());
                    }
                    if (s.getGender().equals("1")){
                        jsonObject.put("gender","男");
                    }else{
                        jsonObject.put("gender","女");
                    }
                    if (s.getPhone()==null){
                        jsonObject.put("phone","未填写手机");
                    }else {
                        jsonObject.put("phone",s.getPhone());
                    }
                    if (s.getEmail()==null){
                        jsonObject.put("email","未填写邮箱");
                    }else {
                        jsonObject.put("email",s.getEmail());
                    }

                    if (s.getAdminFlag()==1){
                        jsonObject.put("flag","管理员");
                    }else if (s.getShopOwnerFlag()==1){
                        jsonObject.put("flag","店家");
                    }else {
                        jsonObject.put("flag","客户");
                    }

                    jsonObject.put("create_time",createTime);

                    if (s.getEnableStatus()==1){
                        jsonObject.put("status","可用");
                    }else {
                        jsonObject.put("status","禁用");
                    }
                    jsonArray.add(jsonObject);
                }

            }
            int count=personInfoList.size();
            String jso="{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+jsonArray.toString()+"}";
            out.print(jso);
            System.out.println(jso);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
