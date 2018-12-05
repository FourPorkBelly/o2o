package cn.shop.utlis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

public class SMSUtil {
	/**
	 * 请求地址urla
	 */
	public static final String URL = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";

	/**
	 * 开发者注册后系统自动生成的账号，可在官网登录后查看
	 */
	public static final String ACCOUNT_SID = "67ae47652761470d9c7f9a87082680dc";

	/**
	 * 开发者注册后系统自动生成的TOKEN，可在官网登录后查看
	 */
	public static final String AUTH_TOKEN = "f6b1c6a30af6404887cb4782f6e7a40c";

	/**
	 * 响应数据类型, JSON或XML
	 */
	public static final String RESP_DATA_TYPE = "json";

	/**
	 * 构造通用参数timestamp、sig和respDataType
	 *
	 * @return
	 */
	public static String createCommonParam() {
		// 时间戳
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		// 签名
		//md5加密
		String sig = DigestUtils.md5Hex(ACCOUNT_SID + AUTH_TOKEN + timestamp);
		return "&timestamp=" + timestamp + "&sig=" + sig + "&respDataType="
				+ RESP_DATA_TYPE;

	}
	// 生成一个随机字符串
	public static String generateCheckCode(int n) {
		String chars = "0123456789";
		//String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] rands = new char[n];
		Random rd = new Random();
		for (int i = 0; i < n; i++) {
			// 0-35之间的一个随机数
			int rand = rd.nextInt(chars.length());
			rands[i] = chars.charAt(rand);
		}
		return new String(rands);
	}

	/**
	 * post请求
	 *
	 * @param url
	 *            功能和操作
	 * @param body
	 *            要post的数据
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, String body) throws IOException {
		String result = "";
		OutputStreamWriter out = null;
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			// 设置连接参数
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(20000);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 提交数据
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(body);
			out.flush();
			// 读取返回数据
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line = "";
			boolean firstLine = true; // 读第一行不加换行符
			while ((line = in.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
				} else {
					result += System.lineSeparator();
				}
				result += line;
			}
		} finally {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
		}
		return result;
	}

	/**
	 * 发送验证码通知短信
	 *
	 * @param to
	 *            指发给谁，手机号,如:1359875489
	 * @param code
	 *            随机验证码
	 */
	public static String send(String to, String code) throws IOException {
		String smsContent = "【o2o校园商铺】您的验证码为"+code+"，请于5分钟内正确输入，如非本人操作，请忽略此短信。";
		String tmpSmsContent = null;
		tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
		String body = "accountSid=" + ACCOUNT_SID + "&to=" + to
				+ "&smsContent=" + tmpSmsContent + createCommonParam();
		// 提交请求
		String result = post(URL, body);
		System.out.println("result==>"+result);
		return result;
	}
}
