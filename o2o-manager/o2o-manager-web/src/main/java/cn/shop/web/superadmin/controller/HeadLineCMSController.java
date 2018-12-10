package cn.shop.web.superadmin.controller;

import cn.shop.cms.service.HeadLineCMSService;
import cn.shop.pojo.AeadLine;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yzg
 * @date 2018/12/10 - 0:00
 */
@Controller
@RequestMapping("/headline")
public class HeadLineCMSController {
    @Autowired
    HeadLineCMSService service;
    @Autowired
    HttpServletResponse response;
    @Autowired
    HttpServletRequest request;

    /**
     * 查询头条图片
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryHeadLine", produces = "application/json; charset=utf-8")
    public String queryHeadLine(){
        response.setContentType("text/html;charset=utf-8");
//        得到headline集合
        List<AeadLine> list = service.queryHeadLine();
//        新建JSONArray对象存放json数据
        JSONArray jsonArray=new JSONArray();
//        转换日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (AeadLine aeadLine:list){
//            新建jsonobject对象
            JSONObject object=new JSONObject();
//            将lineId，lineName，lineImg，priority，lastEditTime放入json对象
            object.put("lineId",aeadLine.getLineId());
            object.put("lineName",aeadLine.getLineName());
            object.put("lineImg",aeadLine.getLineImg());
            object.put("priority",aeadLine.getPriority());
            object.put("lastEditTime",formatter.format(aeadLine.getLastEditTime()));
//            将jsonobject对象放入jsonarray
            jsonArray.add(object);
        }
        String jso="{\"code\":0,\"msg\":\"\",\"count\":"+list.size()+",\"data\":"+jsonArray.toString()+"}";
        return jso;
    }

    /**
     * 得到HeadLine对象
     * @param headLineId
     * @return
     */
    @RequestMapping(value = "/getAeadLineById",method = RequestMethod.GET)
    public String getAeadLineById(int headLineId){
        response.setContentType("text/html;charset=utf-8");
//        根据id得到HeadLine对象
        AeadLine aeadLine=service.getHeadLineById(headLineId);
//        将对象存入session
        request.getSession().setAttribute("aeadLine",aeadLine);
        return "/superadmin/updateHeadLine";
    }

    /**
     * 将AeadLine赋值给弹出层
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/setHeadLine",method = RequestMethod.GET,produces = "text/html;charset=UTF-8")
    public String setHeadLine(){
//        从session得到HeadLine对象
        AeadLine aeadLine = (AeadLine)request.getSession().getAttribute("aeadLine");
//        新建json对象并将Headline对象的值赋给json（lineId，lineName，img，priority）
        JSONObject object=new JSONObject();
        object.put("lineId",aeadLine.getLineId());
        object.put("lineName",aeadLine.getLineName());
        object.put("img",aeadLine.getLineImg());
        object.put("priority",aeadLine.getPriority());
//        转换日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        object.put("lastEditTime",formatter.format(aeadLine.getLastEditTime()));
        return object.toString();
    }

    /**
     * 修改HeadLine
     * @param aeadLine
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateHeadLine")
    public Map<String,Object> updateHeadLine(AeadLine aeadLine){
        Map<String,Object> map=new HashMap<>();
//        得到返回结果
        int i = service.updateHeadLine(aeadLine);
        if (i>0){
            map.put("msg","修改成功");
        }else {
            map.put("msg","修改成功");
        }
        return map;
    }
}
