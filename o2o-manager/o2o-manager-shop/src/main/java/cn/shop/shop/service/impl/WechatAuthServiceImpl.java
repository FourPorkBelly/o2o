package cn.shop.shop.service.impl;

import java.util.Date;
import java.util.List;

import cn.shop.dto.WechatAuthExecution;
import cn.shop.enums.WechatAuthStateEnum;
import cn.shop.pojo.PersonInfo;
import cn.shop.pojo.WechatAuth;
import cn.shop.pojo.WechatAuthExample;
import cn.shop.shop.service.WechatAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.shop.mapper.WechatAuthMapper;
import cn.shop.mapper.PersonInfoMapper;

/**
 * 微信相关
 */
@Service
public class WechatAuthServiceImpl implements WechatAuthService {
	private static Logger log = LoggerFactory
			.getLogger(WechatAuthServiceImpl.class);
	@Autowired
	private WechatAuthMapper wechatAuthMapper;
	@Autowired
	private PersonInfoMapper personInfoMapper;

	/**
	 * 通过openid获取微信账号信息
	 * @param openId
	 * @return
	 */
	@Override
	public WechatAuth getWechatAuthByOpenId(String openId) {
	    //创建查询条件通过openid查询
		WechatAuthExample example = new WechatAuthExample();
        WechatAuthExample.Criteria criteria = example.createCriteria();
        criteria.andOpenIdEqualTo(openId);
        WechatAuth wechatAuth = null;
        //执行查询
        try {
            List<WechatAuth> wechatAuthList = wechatAuthMapper.selectByExample(example);
            wechatAuth = wechatAuthList.get(0);
        }catch (Exception e){
			System.out.println("该用户不存在");
        }
		System.out.println("return wechatAuth");
        return wechatAuth;
	}

    /**
     * 添加微信账号信息
     * @param wechatAuth
     * @return
     * @throws RuntimeException
     */
	@Override
	public WechatAuthExecution addWechatAuth(WechatAuth wechatAuth) throws RuntimeException {
		System.out.println("wechatAuth:"+wechatAuth);
	    //判断信息是否为空
		if (wechatAuth == null || wechatAuth.getOpenId() == null) {
			return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
		}
		//不为空则补全信息
		try {
			wechatAuth.setCreateTime(new Date());
			if (wechatAuth.getPersonInfo() != null
					&& wechatAuth.getPersonInfo().getUserId() == null) {
				try {
				    //初始化用户信息
				    //创建时间
					wechatAuth.getPersonInfo().setCreateTime(new Date());
					//修改时间
					wechatAuth.getPersonInfo().setLastEditTime(new Date());
					//客户标记
					wechatAuth.getPersonInfo().setCustomerFlag(1);
					//
					wechatAuth.getPersonInfo().setShopOwnerFlag(1);
					//管理员
					wechatAuth.getPersonInfo().setAdminFlag(0);
					//身份
					wechatAuth.getPersonInfo().setEnableStatus(1);
					//用户信息表
					PersonInfo personInfo = wechatAuth.getPersonInfo();
					//添加用户信息
					int effectedNum = personInfoMapper
							.insertUserId(personInfo);
					wechatAuth.setUserId(personInfo.getUserId());
					if (effectedNum <= 0) {
						throw new RuntimeException("添加用户信息失败");
					}
				} catch (Exception e) {
				    //添加用户信息失败抛出异常
                    e.printStackTrace();
					log.debug("insertPersonInfo error:" + e.toString());
					throw new RuntimeException("insertPersonInfo error: "
							+ e.getMessage());
				}
			}
			//微信信息创建
			int effectedNum = wechatAuthMapper.insert(wechatAuth);
			if (effectedNum <= 0) {
				throw new RuntimeException("帐号创建失败");
			} else {
				return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS,
						wechatAuth);
			}
		} catch (Exception e) {
			log.debug("insertWechatAuth error:" + e.toString());
			throw new RuntimeException("insertWechatAuth error: "
					+ e.getMessage());
		}
	}
}
