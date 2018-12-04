package cn.shop.utlis.weixin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import cn.shop.pojo.PersonInfo;
import cn.shop.utlis.weixin.message.pojo.UserAccessToken;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

public class WeiXinUserUtil {

	/*@Value("${WECHAT_APPID}")*/
	private static String WECHAT_APPID = "wx8c3a841c6efd97e9";
	/*@Value("${WECHAT_APPSECRET}")*/
	private static String WECHAT_APPSECRET = "d058758c0b2f23e2431435be1689b683";

	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void getCode() throws UnsupportedEncodingException {
		// String codeUrl =
		// "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf0e81c3bee622d60&redirect_uri="
		// + URLEncoder.encode("www.cityrun.com", "utf-8")
		// +
		// "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
	}

	public static UserAccessToken getUserAccessToken(String code)
			throws IOException {
		/*
		* https://api.weixin.qq.com/sns/oauth2/access_token?secret=e5d0a582b49fb9a3f667e38d4b48abba&appid=wxf0be675cc70f0cc1&grant_type=authorization_code&code=06159AFe1mvmNr0mlvFe1vTzFe159AFI
		* */
		Properties pro = new Properties();
		pro.load(WeiXinUserUtil.class.getClassLoader().getResourceAsStream(
				"weixin.properties"));
		//测试号信息里的appId
		/*String appId = DESUtils
				.getDecryptString(pro.getProperty("weixinappid"));*/
		String appId =WECHAT_APPID;
		log.debug("appId:" + appId);
		//测试号信息里的appsecret
		/*String appsecret = DESUtils.getDecryptString(pro
				.getProperty("weixinappsecret"));*/
		String appsecret = WECHAT_APPSECRET;
				log.debug("secret:" + appsecret);
		//根据传入的code，拼接出访问微信定义好的接口的URL
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appId + "&secret=" + appsecret + "&code=" + code
				+ "&grant_type=authorization_code&connect_redirect=1";
		String urlaccess_token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appsecret+"&code="+code;
		System.out.println("URL:"+url);
		System.out.println("urlaccess_token:"+urlaccess_token);
		JSONObject jsonObjectaccess_token = WeixinUtil.httpsRequest(urlaccess_token, "GET", null);
		JSONObject jsonObject = WeixinUtil.httpsRequest(url, "GET", null);
		log.debug("userAccessToken:" + jsonObject.toString());
		//相应URL发送请求获取token json字符串
		String accessToken = jsonObjectaccess_token.getString("access_token");
		System.out.println("accessToken:"+accessToken);
		if (null == accessToken) {
			log.debug("获取用户accessToken失败。");
			return null;
		}
		System.out.println("getUserAccessToken jsonObject:"+jsonObject);
		UserAccessToken token = new UserAccessToken();
		token.setAccessToken(accessToken);
		token.setExpiresIn(jsonObject.getString("expires_in"));
		token.setOpenId(jsonObject.getString("openid"));
		token.setRefreshToken(jsonObject.getString("refresh_token"));
		token.setScope(jsonObject.getString("scope"));
		return token;
	}

	public static WeiXinUser getUserInfo(String accessToken, String openId) {
		/*String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ accessToken + "&openid=" + openId + "&lang=zh_CN";*/
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openId;
		System.out.println("url:"+url);
		JSONObject jsonObject = WeixinUtil.httpsRequest(url, "GET", null);
		System.out.println("getUserInfo jsonObject:"+jsonObject);
		WeiXinUser user = new WeiXinUser();
		String openid = jsonObject.getString("openid");
		if (openid == null) {
			log.debug("获取用户信息失败。");
			return null;
		}
		user.setOpenId(openid);
		user.setNickName(jsonObject.getString("nickname"));
		user.setSex(jsonObject.getInt("sex"));
		user.setProvince(jsonObject.getString("province"));
		user.setCity(jsonObject.getString("city"));
		user.setCountry(jsonObject.getString("country"));
		user.setHeadimgurl(jsonObject.getString("headimgurl"));
		user.setPrivilege(null);
		// user.setUnionid(jsonObject.getString("unionid"));
		return user;
	}

	public static boolean validAccessToken(String accessToken, String openId) {
		String url = "https://api.weixin.qq.com/sns/auth?access_token="
				+ accessToken + "&openid=" + openId+"&connect_redirect=1 ";
		JSONObject jsonObject = WeixinUtil.httpsRequest(url, "GET", null);
		int errcode = jsonObject.getInt("errcode");
		if (errcode == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static PersonInfo getPersonInfoFromRequest(WeiXinUser user) {
		PersonInfo personInfo = new PersonInfo();
		personInfo.setName(user.getNickName());
		personInfo.setGender(user.getSex() + "");
		personInfo.setProfileImg(user.getHeadimgurl());
		personInfo.setEnableStatus(1);
		return personInfo;
	}
}
