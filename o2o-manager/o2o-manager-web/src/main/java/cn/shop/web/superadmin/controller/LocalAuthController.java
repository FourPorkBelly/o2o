package cn.shop.web.superadmin.controller;

import cn.shop.cms.service.LocalService;
import cn.shop.dto.PersonInfoExecution;
import cn.shop.mapper.PersonInfoMapper;
import cn.shop.mapper.ShopMapper;
import cn.shop.pojo.LocalAuth;
import cn.shop.pojo.PersonInfo;
import cn.shop.pojo.Shop;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private ShopMapper shopMapper;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    /**
     * 管理员登录
     * @param localAuth
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loginAdmin",method = RequestMethod.POST)
    public Map<String,Object> adminLogin(LocalAuth localAuth){
        Map<String,Object> map = new HashMap<>();
//        接收对象如果不为空执行登录
        if (localAuth!=null){
//            执行查询结果
            LocalAuth user=localService.login(localAuth);
//            如果查询结果不为空则登录成功
            if (user!=null){
//                登录成功后将管理员对象存入session以及map
                request.getSession().setAttribute("user",user);
                map.put("user",user);
//                返回执行信息
                map.put("msg","yes");
            }else {
                map.put("msg","n");
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
        String loginName="";
        if (user!=null){
            loginName=user.getUserName();
            //得到用户对象
            PersonInfo personInfo = personInfoMapper.selectByPrimaryKey(user.getUserId());

            try {
//            将账号名，用户头像放入map
                map.put("msg","success");
                map.put("userName",loginName);

                map.put("img",personInfo.getProfileImg());
            }catch (Exception e){
                map.put("msg","n");
                map.put("loginName","未登录");
                e.printStackTrace();
            }
        }else {
            map.put("msg","n");
            map.put("msg","错误");
        }

        return map;
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping(value = "/outLogin")
    public String outLogin(){
//        从session移除user
        request.getSession().removeAttribute("user");
        return "/superadmin/login";
    }

    /**
     * 查询用户
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/queryPersonList",method = RequestMethod.GET,produces={"application/json;charset=utf-8"})
    @ResponseBody
    public String queryPerson(String name, int page,int limit){
        System.out.println(name+"name");
        response.setContentType("text/html;charset=utf-8");
        PersonInfo personInfo=new PersonInfo();
        if (name!=null){
            personInfo.setName(name);
        }
            PersonInfoExecution personInfoExecution = localService.queryPersonInfo(personInfo, page, limit);
            List<PersonInfo> personInfoList=personInfoExecution.getPersonInfoList();
            JSONArray jsonArray = new JSONArray();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (PersonInfo s:personInfoList){
                if (s!=null){
                    JSONObject jsonObject = new JSONObject();
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
            int count=personInfoExecution.getCount();
            String jso="{\"code\":0,\"msg\":\"\",\"count\":"+count+",\"data\":"+jsonArray.toString()+"}";
        return jso;
    }

    /**
     * 禁用用户
     * @param userid
     * @return
     */
    @RequestMapping(value = "/disableUser",method = RequestMethod.GET,produces={"application/json;charset=utf-8"})
    @ResponseBody
    public String disableUser(int userid){
        response.setContentType("text/html;charset=utf-8");
        PersonInfo personInfo=new PersonInfo();
//        将用户id存入personinfo对象
        personInfo.setUserId(userid);
//        设置用户状态为0不可用
        personInfo.setEnableStatus(0);
//        设置用户最后修改时间
        personInfo.setLastEditTime(new Date());
        int i=localService.disableUser(personInfo);
        if (i>0){
            return "y";
        }
        return "n";
    }
    /**
     * 启用用户
     * @param userid
     * @return
     */
    @RequestMapping(value = "/enableUser",method = RequestMethod.GET,produces={"application/json;charset=utf-8"})
    @ResponseBody
    public String enableUser(int userid){
        response.setContentType("text/html;charset=utf-8");
        PersonInfo personInfo=new PersonInfo();
//        将用户id存入personinfo对象
        personInfo.setUserId(userid);
//        设置用户状态为1可用
        personInfo.setEnableStatus(1);
//        设置用户最后修改时间
        personInfo.setLastEditTime(new Date());
//        返回启用结果
        int i=localService.disableUser(personInfo);
        if (i>0){
            return "y";
        }
        return "n";
    }
}
