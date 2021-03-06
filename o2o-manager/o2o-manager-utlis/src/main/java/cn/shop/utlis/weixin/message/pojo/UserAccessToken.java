package cn.shop.utlis.weixin.message.pojo;
/**
 * 用户授权token
 * @author liusai01
 *
 */
public class UserAccessToken {
	//获取到的凭证
	private String accessToken;
	//凭证有效时间，单位：秒
	private String expiresIn;
	//标识更新令牌，用来获取下一次访问令牌.
	private String refreshToken;
	//该用户在此公众号下的身份标识，对此微信号具有唯一性
	private String openId;
	//表示权限范围
	private String scope;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	@Override
	public String toString() {
		return "accessToken:"+this.getAccessToken()+",openId:"+this.getOpenId();
	}
	
}
