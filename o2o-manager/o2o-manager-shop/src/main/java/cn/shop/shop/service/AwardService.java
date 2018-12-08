package cn.shop.shop.service;

import cn.shop.dto.AwardExecution;
import cn.shop.pojo.Award;

import java.util.List;

/**
 * 奖品信息
 * @author 赵铭涛
 * @creation time 2018/11/22 - 13:46
 */
public interface AwardService {
    /**
     *根据shopId查找
     * @return
     */
    AwardExecution getAwardListByShopId(Integer shopId,Integer pageIndex,Integer pageSize);

    /**
     * 添加奖品
     * @param award
     * @return
     */
    AwardExecution addAward(Award award);

    /**
     * 根据id查询奖品
     * @param awardId
     * @return
     */
    AwardExecution getAwardById(Integer awardId);

    /**
     * 修改奖品
     * @param award
     * @return
     */
    AwardExecution updateAward(Award award);

    /**
     * 奖品上下架
     * @param awardId
     * @param enableStatus
     * @return
     */
    AwardExecution updateStatusAward(Integer awardId,Integer enableStatus);

    /**
     * 删除奖品
     * @param awardId
     * @return
     */
    AwardExecution deleteAward(Integer awardId);
}
