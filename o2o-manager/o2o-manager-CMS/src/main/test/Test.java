import cn.shop.cms.service.ShopServicecms;
import cn.shop.dto.ShopExecution;
import cn.shop.mapper.ShopMapper;
import cn.shop.pojo.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @author yzg
 * @date 2018/11/28 - 8:35
 */
@Controller
public class Test {
    @Autowired
    private ShopServicecms shopServicecms;
    @Autowired
    private ShopMapper shopMapper;
    @org.junit.Test
    public void testQueryShop(){
//        ShopExecution shopExecution=shopServicecms.queryShopList(null,1,99);
        ShopExecution shopExecution = shopServicecms.queryShopList(null, 1, 20);
        List<Shop> shopList=shopExecution.getShopList();

        for (Shop shop:shopList){
            System.out.println(shop.getShopName());
        }
    }
}
