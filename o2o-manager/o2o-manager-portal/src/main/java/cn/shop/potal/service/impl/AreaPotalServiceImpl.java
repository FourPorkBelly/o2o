package cn.shop.potal.service.impl;

import cn.shop.mapper.AreaMapper;
import cn.shop.pojo.Area;
import cn.shop.pojo.AreaExample;
import cn.shop.potal.service.AreaPotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaPotalServiceImpl implements AreaPotalService {
    @Autowired
    AreaMapper areaMapper;

    /**
     * @Description: 查询所有街道
     * @Author: oy
     * @CreateDate: 2018/11/21 0021 下午 4:42
     */
    @Override
    public List<Area> selectByAreaExample() {
        AreaExample areaExample = new AreaExample();
        AreaExample.Criteria criteria = areaExample.createCriteria();
        List<Area> areas =areaMapper.selectByExample(areaExample);

        return areas;
    }
}
