package cn.shop.wechat;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.shop.shop.service.ShopService;
import cn.shop.shop.service.WechatAuthService;
import cn.shop.utlis.weixin.WeiXinUser;
import cn.shop.utlis.weixin.WeiXinUserUtil;
import cn.shop.utlis.weixin.message.pojo.UserAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.shop.dto.ShopExecution;
import cn.shop.dto.WechatAuthExecution;
import cn.shop.pojo.PersonInfo;
import cn.shop.pojo.WechatAuth;
import cn.shop.enums.WechatAuthStateEnum;
import cn.shop.shop.service.PersonInfoService;
import cn.shop.shop.service.ShopAuthMapService;
import cn.shop.shop.service.ShopService;
import cn.shop.shop.service.WechatAuthService;
import cn.shop.utlis.weixin.WeiXinUser;
import cn.shop.utlis.weixin.WeiXinUserUtil;
import cn.shop.utlis.weixin.message.pojo.UserAccessToken;

@Controller
@RequestMapping("wechatlogin")
/**
 * 从微信菜单点击后调用的接口，可以在url里增加参数（role_type）来表明是从商家还是从玩家按钮进来的，依次区分登陆后跳转不同的页面
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf0e81c3bee622d60&redirect_uri=
 * http://o2o.mengyuanzhilan.cn/wechatlogin/logincheck
 * &role_type&response_type=code&scope=snsapi_userinfo
 * 玩家会跳转到index.html页面
 * 商家如果没有注册，会跳转到注册页面，否则跳转到任务管理页面
 * 如果是商家的授权用户登陆，会跳到授权店铺的任务管理页面
 * @author lixiang
 *
 */
public class WeiXinLoginController {

	private static Logger log = LoggerFactory
			.getLogger(WeiXinLoginController.class);

	@Resource
	private PersonInfoService personInfoService;
	@Resource
	private cn.shop.shop.service.WechatAuthService WechatAuthService;

	@Resource
	private ShopService shopService;



	private static final String FRONTEND = "1";
	private static final String SHOPEND = "2";

	@RequestMapping(value = "/logincheck", method = { RequestMethod.GET })
	public String doGet(HttpServletRequest request, HttpServletResponse response) {
		log.debug("weixin login get...");
		//获取微信公众号传输过来的code，通过code课获取access_token，进而获取用户信息
		String code = request.getParameter("code");
		//这个state可以用来传我们自定义的消息，方便程序调用。
		String roleType = request.getParameter("state");
		log.debug("weixin login code:" + code);
		//微信账户信息
		WechatAuth auth = null;
		WeiXinUser user = null;
		String openId = null;
		if (null != code) {
			UserAccessToken token;
			try {
				//通过code获取access_token
				token = WeiXinUserUtil.getUserAccessToken(code);
				log.debug("weixin login token:" + token.toString());
				//通过token获取accessToken
				String accessToken = token.getAccessToken();
				//通过token获取openId
				openId = token.getOpenId();
				//通过access_toKen和openId获取用户昵称等信息
				user = WeiXinUserUtil.getUserInfo(accessToken, openId);
				log.debug("weixin login user:" + user.toString());
				//将openID存入session
				request.getSession().setAttribute("openId", openId);
				//从数据库获取微信账户信息
				auth = WechatAuthService.getWechatAuthByOpenId(openId);
			} catch (IOException e) {
				log.error("error in getUserAccessToken or getUserInfo or findByOpenId: "
						+ e.toString());
				e.printStackTrace();
			}
		}
		log.debug("weixin login success.");
		System.out.println("weixin login success.");
		log.debug("login role_type:" + roleType);
		System.out.println("login role_type:" + roleType);
		//前面获取到openID后，可以通过他去数据库判断该微信号是否存在
		//如果不存在则进行创建，存在直接登录
		if (FRONTEND.equals(roleType)) {
			//获取用户信息
			PersonInfo personInfo = WeiXinUserUtil
					.getPersonInfoFromRequest(user);
			//判断是否为空
			if (auth == null) {
				auth = new WechatAuth();
				auth.setOpenId(openId);
				auth.setPersonInfo(personInfo);
				WechatAuthExecution we = WechatAuthService.addWechatAuth(auth);
				if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
					return null;
				}
			}
			personInfo = personInfoService.getPersonInfoById(auth.getUserId());
			request.getSession().setAttribute("user", personInfo);
			return "frontend/index";
		}


		if (SHOPEND.equals(roleType)) {
			PersonInfo personInfo = null;
			WechatAuthExecution we = null;
			if (auth == null) {
				auth = new WechatAuth();
				auth.setOpenId(openId);
				personInfo = WeiXinUserUtil.getPersonInfoFromRequest(user);
				personInfo.setShopOwnerFlag(1);
				auth.setPersonInfo(personInfo);
				we = WechatAuthService.addWechatAuth(auth);
				if (we.getState() != WechatAuthStateEnum.SUCCESS.getState()) {
					return null;
				}
			}
			personInfo = personInfoService.getPersonInfoById(auth.getUserId());
			request.getSession().setAttribute("user", personInfo);
			ShopExecution se = shopService.getByEmployeeId(personInfo
					.getUserId());
			request.getSession().setAttribute("user", personInfo);
			if (se.getShopList() == null || se.getShopList().size() <= 0) {
				return "shop/shoplist";
			} else {
				request.getSession().setAttribute("shopList", se.getShopList());
				return "shop/shoplist";
			}
		}
		return null;
	}
}
