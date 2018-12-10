package cn.shop.utlis;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QRCodeUtil {
	public static BitMatrix generateQRCodeStream(String url,
			HttpServletResponse resp) {
		//给响应添加头部信息，主要是告诉浏览器返回的是图片流
		resp.setHeader("Cache-Control", "no-store");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/png");
		//设置图片的文字编码以及内边框距
		Map<EncodeHintType, Object> hints = new HashMap<>();
		//设置编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		//设置内边框距
		hints.put(EncodeHintType.MARGIN, 0);

		BitMatrix bitMatrix;

		try {
			//设置参数
			//参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
			bitMatrix = new MultiFormatWriter().encode(url,
					BarcodeFormat.QR_CODE, 300, 300, hints);
		} catch (WriterException e) {
			e.printStackTrace();
			return null;
		}
		return bitMatrix;
	}
}
