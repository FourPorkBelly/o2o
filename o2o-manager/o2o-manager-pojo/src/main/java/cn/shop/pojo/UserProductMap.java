package cn.shop.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Getter
@Setter
@ToString
public class UserProductMap {
    private Integer userProductId;

    private Integer userId;

    private Integer productId;

    private Integer shopId;

    private String userName;

    private String productName;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;

    private Integer point;

    private PersonInfo user;

    private Product product;

    private Shop shop;

    private PersonInfo operator;
    /**
     * 星期
     */
    private String weekCreateTime;
    /**
     * 作为判断用的
     */
    private String isCreateTime;

    public void setWeekCreateTime(String weekCreateTime) {
        String week = weekCreateTime.substring(weekCreateTime.length()-3,weekCreateTime.length());
        this.weekCreateTime = week;
    }
}