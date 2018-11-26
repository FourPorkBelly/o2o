import cn.shop.portal.service.SMSService;
import cn.shop.portal.service.impl.SMSServiceImpl;

/**
 * @author 赵铭涛
 * @creation time 2018/11/26 - 14:32
 */
public class TestSMS {
    public static void main(String[] args) {
        SMSService smsService = new SMSServiceImpl();
        smsService.verifySMS("15197001326");
    }
}
