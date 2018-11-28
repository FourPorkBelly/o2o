package cn.shop.potal.service;

import cn.shop.pojo.Area;
import cn.shop.pojo.AreaExample;

import java.util.List;

public interface AreaPotalService {
    /**
     * @Description:    查询所有街道
     * @Author:         oy
     * @CreateDate:     2018/11/21 0021 下午 4:41
     */
    List<Area> selectByAreaExample();
}
