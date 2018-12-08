package cn.shop.shop.service.impl;

import cn.shop.dto.AwardExecution;
import cn.shop.enums.AwardStateEnum;
import cn.shop.mapper.AwardMapper;
import cn.shop.pojo.Award;
import cn.shop.pojo.AwardExample;
import cn.shop.shop.service.AwardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author zmt
 * @date 2018/12/8 - 23:34
 */
@Service
public class AwardServiceImpl implements AwardService {
    @Autowired
    private AwardMapper awardMapper;
    /**
     * 根据shopid获取奖品列表
     */
    @Override
    public AwardExecution getAwardListByShopId(Integer shopId,Integer pageIndex,Integer pageSize) {
        AwardExecution execution = new AwardExecution();
        try {
            //创建查询条件
            AwardExample example = new AwardExample();
            AwardExample.Criteria criteria = example.createCriteria();
            criteria.andShopIdEqualTo(shopId);
            PageHelper.startPage(pageIndex,pageSize);
            List<Award> awards = awardMapper.selectByExample(example);
            PageInfo pageInfo = new PageInfo(awards);
            execution.setAwardList(awards);
            execution.setState(AwardStateEnum.SUCCESS.getState());
            return execution;
        }catch (Exception e){
            e.printStackTrace();
            return new AwardExecution(AwardStateEnum.INNER_ERROR);
        }
    }
    /**
     * 添加奖品
     * @param award
     * @return
     */
    @Override
    public AwardExecution addAward(Award award) {
        try {
            award.setLastEditTime(new Date());
            award.setCreateTime(new Date());
            award.setEnableStatus(1);
            int insert = awardMapper.insert(award);
            if(insert>0){
                return new AwardExecution(AwardStateEnum.SUCCESS);
            }else{
                return new AwardExecution(AwardStateEnum.INNER_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new AwardExecution(AwardStateEnum.INNER_ERROR);
        }
    }
    /**
     * 根据id查询奖品
     * @param awardId
     * @return
     */
    @Override
    public AwardExecution getAwardById(Integer awardId) {
        AwardExecution execution = new AwardExecution();
        try {
            Award award = awardMapper.selectByPrimaryKey(awardId);
            if(award!=null){
                execution.setAward(award);
                execution.setState(AwardStateEnum.SUCCESS.getState());
                return execution;
            }else {
                return new AwardExecution(AwardStateEnum.EMPTY);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new AwardExecution(AwardStateEnum.INNER_ERROR);
        }
    }
    /**
     * 修改奖品
     * @param award
     * @return
     */
    @Override
    public AwardExecution updateAward(Award award) {
        award.setLastEditTime(new Date());
        try {
            int i = awardMapper.updateByPrimaryKeySelective(award);
            if(i>0){
                return new AwardExecution(AwardStateEnum.SUCCESS);
            }else{
                return new AwardExecution(AwardStateEnum.INNER_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new AwardExecution(AwardStateEnum.INNER_ERROR);
        }
    }
    /**
     * 奖品上下架
     * @param awardId
     * @param enableStatus
     * @return
     */
    @Override
    public AwardExecution updateStatusAward(Integer awardId, Integer enableStatus) {
        if(enableStatus==1){
            if(!isStatusAward(awardId)){
                new AwardExecution(AwardStateEnum.ADDED_ERROR);
            }
        }
        try {
            Award award = new Award();
            award.setAwardId(awardId);
            award.setEnableStatus(enableStatus);
            int i = awardMapper.updateByPrimaryKeySelective(award);
            if(i>0){
                return new AwardExecution(AwardStateEnum.SUCCESS);
            }else{
                return new AwardExecution(AwardStateEnum.INNER_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new AwardExecution(AwardStateEnum.INNER_ERROR);
        }
    }
    /**
     * 删除奖品
     * @param awardId
     * @return
     */
    @Override
    public AwardExecution deleteAward(Integer awardId) {
        try {
            int i = awardMapper.deleteByPrimaryKey(awardId);
            if(i>0){
                return new AwardExecution(AwardStateEnum.SUCCESS);
            }else{
                return new AwardExecution(AwardStateEnum.INNER_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new AwardExecution(AwardStateEnum.INNER_ERROR);
        }
    }

    /**
     * 判断是否可以上架
     * @param awardId
     * @return
     */
    private boolean isStatusAward(Integer awardId){
        Award award = awardMapper.selectByPrimaryKey(awardId);
        //当前时间往后加一天
        Date current = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(current);
        calendar.add(calendar.DATE, 1);
        current = calendar.getTime();
        return current.after(award.getExpireTime());
    }
}
