package cn.shop.web.superadmin.controller;

import cn.shop.cms.service.HeadLineCMSService;
import cn.shop.pojo.AeadLine;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author yzg
 * @date 2018/12/10 - 0:00
 */
@Controller
@RequestMapping("headline")
public class HeadLineCMSController {
    @Autowired
    HeadLineCMSService service;
    @Autowired
    HttpServletResponse response;
    @ResponseBody
    @RequestMapping(value = "/queryHeadLine")
    public String queryHeadLine(){
        response.setContentType("text/html;charset=utf-8");
        List<AeadLine> list = service.queryHeadLine();
        JSONArray jsonArray=new JSONArray();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (AeadLine aeadLine:list){
            JSONObject object=new JSONObject();
            object.put("lineId",aeadLine.getLineId());
            object.put("lineName",aeadLine.getLineName());
        }
        return "";
    }
}
