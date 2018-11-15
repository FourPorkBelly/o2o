package cn.shop.shop.service.impl;

import cn.shop.mapper.AreaMapper;
import cn.shop.pojo.Area;
import cn.shop.shop.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zmt
 * @date 2018/11/16 - 3:44
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaMapper areaMapper;
    @Override
    public List<Area> testAreaList() {
        return areaMapper.selectByExample(null);
    }
}
