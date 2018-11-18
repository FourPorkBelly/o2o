package cn.shop.web.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zmt
 * @date 2018/11/18 - 0:45
 */
@Controller
public class PageController {

    /**
     * 打开首页
     * @return
     */
    @RequestMapping("/")
    public String showIndex(){
        return "frontend/index";
    }
    @RequestMapping("/{page}/{html}")
    public String pageindex(@PathVariable("page")String page,@PathVariable("html")String html){
        return page+"/"+html;
    }
}
