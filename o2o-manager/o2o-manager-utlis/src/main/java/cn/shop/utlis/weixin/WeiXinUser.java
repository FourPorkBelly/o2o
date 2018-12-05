package cn.shop.utlis.weixin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
@Getter
@Setter
@ToString
public class WeiXinUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	//用户昵称
	private String openId;
	//性别
	private String nickName;
	//省份
	private int sex;
	//城市
	private String province;
	//区
	private String city;
	//头像图片地址
	private String country;
	//语言
	private String headimgurl;
	//用户权限
	private String privilege;

	private String unionid;
}
