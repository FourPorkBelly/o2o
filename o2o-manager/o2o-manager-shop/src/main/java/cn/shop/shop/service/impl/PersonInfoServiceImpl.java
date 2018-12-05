package cn.shop.shop.service.impl;

import cn.shop.dto.PersonInfoExecution;
import cn.shop.enums.PersonInfoStateEnum;
import cn.shop.mapper.PersonInfoMapper;
import cn.shop.pojo.PersonInfo;
import cn.shop.pojo.PersonInfoExample;
import cn.shop.shop.service.PersonInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户信息
 * @author 赵铭涛
 * @creation time 2018/11/21 - 9:10
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {
    @Autowired
    PersonInfoMapper personInfoMapper;

    /**
     *根据id获取用户信息
     * @param userId
     * @return
     */
    @Override
    public PersonInfo getPersonInfoById(Integer userId) {
        return personInfoMapper.selectByPrimaryKey(userId);
    }
    /**
     *根据条件查询用户信息 分页
     * @param personInfo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PersonInfoExecution getPersonInfoList(PersonInfo personInfo, int pageIndex, int pageSize) {
        //添加查询条件
        PersonInfoExample example = new PersonInfoExample();
        PersonInfoExample.Criteria criteria = example.createCriteria();
        //客户标识
        if(personInfo.getCustomerFlag()!=null){
            criteria.andCustomerFlagEqualTo(personInfo.getCustomerFlag());
        }
        //店主标识
        if(personInfo.getShopOwnerFlag()!=null){
            criteria.andShopOwnerFlagEqualTo(personInfo.getShopOwnerFlag());
        }
        //管理员标识
        if(personInfo.getAdminFlag()!=null){
            criteria.andAdminFlagEqualTo(personInfo.getAdminFlag());
        }
        //用户名模糊查询
        if(personInfo.getName()!=null){
            criteria.andNameLike("'%"+personInfo.getName()+"%'");
        }
        //
        if (personInfo.getEnableStatus()!=null){
            criteria.andEnableStatusEqualTo(personInfo.getEnableStatus());
        }

        //执行查询
        //分页
        PageHelper.startPage(pageIndex,pageSize);
        //执行查询
        List<PersonInfo> list = personInfoMapper.selectByExample(example);
        //获取分页后的信息
        PageInfo pageInfo = new PageInfo(list);
        //创建返回结果
        PersonInfoExecution se = new PersonInfoExecution();
        if(list!=null&&list.size()>0){
            //成功放入结果集和总数
            se.setPersonInfoList(list);
            se.setCount((int)pageInfo.getTotal());
        }else {
            //没有结果返回
            se.setState(PersonInfoStateEnum.INNER_ERROR.getState());
        }
        return se;
    }
    /**
     *添加用户信息
     * @param personInfo
     * @return
     */
    @Override
    public PersonInfoExecution addPersonInfo(PersonInfo personInfo) {
        //判断传入的数据是否为空
        if (personInfo!=null) {
            //如果为空返回错误信息
            return new PersonInfoExecution(PersonInfoStateEnum.INNER_ERROR);
        }else {
            try {
                //插入数据
                int insert = personInfoMapper.insert(personInfo);
                //判断是否成功
                if(insert<=0){
                    //返回错误信息
                    return new PersonInfoExecution(PersonInfoStateEnum.INNER_ERROR);
                }else {
                    //创建成功查询并返回结果
                    PersonInfo personInfo1 = personInfoMapper.selectByPrimaryKey(insert);
                    return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS,personInfo);
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("添加用户信息出错"+e.getMessage());
            }
        }
    }
    /**
     *修改用户信息
     * @param personInfo
     * @return
     */
    @Override
    public PersonInfoExecution updatePersonInfo(PersonInfo personInfo) {
        //判断是否为空
        if (personInfo == null || personInfo.getUserId() == null) {
            //为空返回错误信息
            return new PersonInfoExecution(PersonInfoStateEnum.EMPTY);
        }else {
            try {
                //修改信息
                int i = personInfoMapper.updateByPrimaryKeySelective(personInfo);
                if (i<=0) {
                    //修改失败返回错误信息
                    return new PersonInfoExecution(PersonInfoStateEnum.INNER_ERROR);
                }else {
                    //修改成功
                    PersonInfo personInfo1 = personInfoMapper.selectByPrimaryKey(i);
                    return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS,personInfo1);
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("修改用户信息失败:"+e.getMessage());
            }
        }
    }
}
