package cn.shop.web.shop.controller;

import cn.shop.shop.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 赵铭涛
 * @creation time 2018/11/6 - 10:15
 */
@Controller
@RequestMapping("/shop/update")
public class PictureController {
    @Autowired
    private PictureService pictureService;

    /**
     * 上传图片
     * @param uploadFile
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Map uploda( MultipartFile uploadFile){
        //调用图片上传
        Map map = pictureService.uploadPicture(uploadFile);
        //返回结果
        return map;
    }
}
