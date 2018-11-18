package cn.shop.shop.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author 赵铭涛
 * @creation time 2018/11/6 - 9:00
 */
public interface PictureService {
    /**
     * 图片上传
     * @param uploadFile
     * @return
     */
    Map uploadPicture(MultipartFile uploadFile);
}
