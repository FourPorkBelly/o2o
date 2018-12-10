package cn.shop.cms.service.Impl;

import cn.shop.cms.service.LocalService;
import cn.shop.dto.PersonInfoExecution;
import cn.shop.dto.ShopExecution;
import cn.shop.enums.PersonInfoStateEnum;
import cn.shop.enums.ShopStateEnum;
import cn.shop.mapper.LocalAuthMapper;
import cn.shop.mapper.PersonInfoMapper;
import cn.shop.mapper.ShopMapper;
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
    @Autowired
    private ShopMapper shopMapper;
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
     * @return
     */
    @Override
    public PersonInfoExecution queryPersonInfo(PersonInfo personInfo, int page, int limit) {
//        PersonInfoExample example=new PersonInfoExample();
//        PersonInfoExample.Criteria criteria= example.createCriteria();
//        if (personInfo!=null) {
//            if (personInfo.getName() != null){
//                criteria.andNameLike(personInfo.getName());
//            }
//        }
        PersonInfoExecution personInfoExecution=new PersonInfoExecution();
        PageHelper.startPage(page,limit);
        List<PersonInfo> list=personInfoMapper.queryUserInfo(personInfo);
        PageInfo pageInfo = new PageInfo(list);
        if (list!=null&&list.size()>0) {
            personInfoExecution.setPersonInfoList(list);
            personInfoExecution.setCount((int)(pageInfo.getTotal()));
        }else {
            personInfoExecution.setState(PersonInfoStateEnum.INNER_ERROR.getState());
        }
        return personInfoExecution;
    }

    /**
     * 禁用用户
     * @param personInfo
     * @return
     */
    @Override
    public int disableUser(PersonInfo personInfo) {

        return personInfoMapper.updateByPrimaryKeySelective(personInfo);
    }

    /**
     * 得到用户状态
     * @param userid
     * @return
     */
    @Override
    public int getUserEnable(int userid) {
        PersonInfo demo=personInfoMapper.selectByPrimaryKey(userid);
        if (demo!=null){
            return demo.getEnableStatus();
        }
        return 0;
    }

    @Override
    public int getUserCount() {
        return personInfoMapper.selectByExample(null).size();
    }

    @Override
    public int getShopCount() {
        return shopMapper.selectByExample(null).size();
    }

}
