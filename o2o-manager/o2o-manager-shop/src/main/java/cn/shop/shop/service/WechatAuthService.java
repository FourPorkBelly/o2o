package cn.shop.shop.service;

import cn.shop.dto.WechatAuthExecution;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.shop.dto.WechatAuthExecution;
import cn.shop.pojo.WechatAuth;

public interface WechatAuthService {

	/**
	 * 
	 * @param openId
	 * @return
	 */
	WechatAuth getWechatAuthByOpenId(String openId);

	/**
	 * 
	 * @param wechatAuth
	 * @param
	 * @return
	 * @throws RuntimeException
	 */
	WechatAuthExecution addWechatAuth(WechatAuth wechatAuth) throws RuntimeException;

}
