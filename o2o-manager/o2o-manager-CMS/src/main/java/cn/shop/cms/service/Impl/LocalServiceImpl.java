package cn.shop.cms.service.Impl;

import cn.shop.cms.service.LocalService;
import cn.shop.dto.PersonInfoExecution;
import cn.shop.dto.ShopExecution;
import cn.shop.enums.ShopStateEnum;
import cn.shop.mapper.LocalAuthMapper;
import cn.shop.mapper.PersonInfoMapper;
import cn.shop.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yzg
 * @date 2018/11/26 - 14:59
 */
@Service
public class LocalServiceImpl implements LocalService {
    @Autowired
    private LocalAuthMapper localAuthMapper;
    @Autowired
    private PersonInfoMapper personInfoMapper;
    /**
     * 管理员登录
     * @param localAuth
     * @return
     */
    @Override
    public LocalAuth login(LocalAuth localAuth) {
        LocalAuth user=localAuthMapper.adminlogin(localAuth);
        if (user!=null){
            return user;
        }else {
            return null;
        }
    }

    /**
     * 查询用户信息
     * @param personInfo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<PersonInfo> queryPersonInfo(PersonInfo personInfo, Integer pageIndex, Integer pageSize) {//
        PersonInfoExample example = new PersonInfoExample();
        PersonInfoExample.Criteria criteria = example.createCriteria();
        if (personInfo!=null){
            if (personInfo.getName()!=null&&!personInfo.getName().equals("")){
                criteria.andNameLike("%"+personInfo.getName()+"%");
            }
        }
        PersonInfoExecution personInfoExecution=new PersonInfoExecution();
        PageHelper.startPage(pageIndex,pageSize);
        List<PersonInfo> personInfoList=personInfoMapper.selectByExample(null);
        PageInfo pageInfo = new PageInfo(personInfoList);
        if (personInfoList!=null&&personInfoList.size()>0) {
            personInfoExecution.setPersonInfoList(personInfoList);
            personInfoExecution.setCount((int)pageInfo.getTotal());
        }else {
            personInfoExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return personInfoList;
    }
}
