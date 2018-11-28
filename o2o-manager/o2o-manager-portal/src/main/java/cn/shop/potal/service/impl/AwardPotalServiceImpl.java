package cn.shop.potal.service.impl;

import cn.shop.mapper.AwardMapper;
import cn.shop.pojo.Award;
import cn.shop.pojo.AwardExample;
import cn.shop.potal.service.AwardPotalService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AwardPotalServiceImpl implements AwardPotalService {
    @Autowired
    AwardMapper awardMapper;

    /**
     * @Description: 按照店铺id查询店铺积分兑换商品
     * @Author: oy
     * @CreateDate: 2018/11/26 0026 下午 12:03
     */
    @Override
    public List<Award> selectByExample(Integer shopid, String name, Integer pagenum, Integer pagesize) {
        AwardExample awardExample = new AwardExample();
        awardExample.setOrderByClause("priority desc");
        AwardExample.Criteria criteria = awardExample.createCriteria();
        //根据id查询
        criteria.andShopIdEqualTo(shopid);
        //根据名字模糊查询
        if (name != null && !"".equals(name)) {
            criteria.andAwardNameLike("%"+name+"%");
        }
        //分也
        if (pagenum != null && pagenum > -1 && pagesize != null && pagesize > -1) {
            PageHelper.startPage(pagenum, pagesize);
        }
        return awardMapper.selectByExample(awardExample);
    }

    /**
     * @Description: 根据条件查询总行数
     * @Author: oy
     * @CreateDate: 2018/11/26 0026 下午 3:11
     */
    @Override
    public long countByExample(Integer shopid, String name) {
        AwardExample awardExample = new AwardExample();
        AwardExample.Criteria criteria = awardExample.createCriteria();
        //根据id查询
        criteria.andShopIdEqualTo(shopid);
        //根据名字模糊查询
        if (name != null && !"".equals(name)) {
            criteria.andAwardNameLike("%"+name+"%");
        }
        return awardMapper.countByExample(awardExample);
    }
    /**
     * @Description:   按照主键查询奖品
     * @Author:         oy
     * @CreateDate:     2018/11/28 0028 上午 11:00
     */
    @Override
    public Award selectByPrimaryKey(Integer awardId) {
        return awardMapper.selectByPrimaryKey(awardId);
    }
}
