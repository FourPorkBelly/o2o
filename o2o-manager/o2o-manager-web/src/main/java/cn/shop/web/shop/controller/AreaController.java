package cn.shop.web.shop.controller;

import cn.shop.pojo.Area;
import cn.shop.shop.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author zmt
 * @date 2018/11/16 - 3:49
 */
@Controller
public class AreaController {
    @Autowired
    private AreaService areaService;

    @RequestMapping("/test")
    public String testAreaList(){
        List<Area> list = areaService.testAreaList();
        for (Area area : list) {
            System.out.println(area.getAreaName());
        }
        return "index";
    }
}
