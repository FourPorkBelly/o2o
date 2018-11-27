package cn.shop.utlis.weixin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import cn.shop.pojo.PersonInfo;
import cn.shop.utlis.weixin.message.pojo.UserAccessToken;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.shop.pojo.PersonInfo;
import cn.shop.utlis.DESUtils;
import cn.shop.utlis.weixin.message.pojo.UserAccessToken;

public class WeiXinUserUtil {

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
		Properties pro = new Properties();
		pro.load(WeiXinUserUtil.class.getClassLoader().getResourceAsStream(
				"weixin.properties"));
		//测试号信息里的appId
		/*String appId = DESUtils
				.getDecryptString(pro.getProperty("weixinappid"));*/
		String appId ="wxf0be675cc70f0cc1";
		log.debug("appId:" + appId);
		//测试号信息里的appsecret
		/*String appsecret = DESUtils.getDecryptString(pro
				.getProperty("weixinappsecret"));*/
		String appsecret = "e5d0a582b49fb9a3f667e38d4b48abba";
				log.debug("secret:" + appsecret);
		//根据传入的code，拼接出访问微信定义好的接口的URL
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appId + "&secret=" + appsecret + "&code=" + code
				+ "&grant_type=authorization_code";
		JSONObject jsonObject = WeixinUtil.httpsRequest(url, "GET", null);
		log.debug("userAccessToken:" + jsonObject.toString());
		//相应URL发送请求获取token json字符串
		String accessToken = jsonObject.getString("access_token");
		if (null == accessToken) {
			log.debug("获取用户accessToken失败。");
			return null;
		}
		UserAccessToken token = new UserAccessToken();
		token.setAccessToken(accessToken);
		token.setExpiresIn(jsonObject.getString("expires_in"));
		token.setOpenId(jsonObject.getString("openid"));
		token.setRefreshToken(jsonObject.getString("refresh_token"));
		token.setScope(jsonObject.getString("scope"));
		return token;
	}

	public static WeiXinUser getUserInfo(String accessToken, String openId) {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ accessToken + "&openid=" + openId + "&lang=zh_CN";
		JSONObject jsonObject = WeixinUtil.httpsRequest(url, "GET", null);
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
				+ accessToken + "&openid=" + openId;
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
