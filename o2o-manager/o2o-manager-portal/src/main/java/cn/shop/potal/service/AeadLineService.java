package cn.shop.potal.service;

import cn.shop.pojo.AeadLine;
import cn.shop.pojo.AeadLineExample;

import java.util.List;
/**
 * @Description:    头条信息
 * @Author:         oy
 * @CreateDate:     2018/11/21 0021 上午 9:24
 */
public interface AeadLineService {
    //查询所有头条信息
    List<AeadLine> queryAeadLine(AeadLine aeadLine);
}
