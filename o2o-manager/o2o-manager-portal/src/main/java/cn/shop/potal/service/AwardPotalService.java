package cn.shop.potal.service;

import cn.shop.pojo.Award;
import cn.shop.pojo.AwardExample;

import java.util.List;

public interface AwardPotalService {
    List<Award> selectByExample(Integer shopid,String name,Integer pagenum,Integer pagesize);
    long countByExample(Integer shopid,String name);
    Award selectByPrimaryKey(Integer awardId);
}
