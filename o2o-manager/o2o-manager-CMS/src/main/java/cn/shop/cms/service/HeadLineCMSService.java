package cn.shop.cms.service;

import cn.shop.dto.HeadLineExecution;
import cn.shop.pojo.AeadLine;
import cn.shop.pojo.ShopCategory;

import java.util.List;

/**
 * @author yzg
 * @date 2018/12/9 - 23:37
 */
public interface HeadLineCMSService {
    /**
     * 查询头条信息
     * @return
     */
    List<AeadLine> queryHeadLine();

    /**
     * 根据id获得头条信息
     * @param headLineById
     * @return
     */
    AeadLine getHeadLineById(int headLineById);

    /**
     * 修改头条信息
     * @param aeadLine
     * @return
     */
    int updateHeadLine(AeadLine aeadLine);
}
