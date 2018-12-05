package cn.shop.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@ToString
@Getter
@Setter
public class ShopAuthMap {
    private Integer shopAuthId;

    private Integer employeeId;

    private Integer shopId;

    private String name;

    private String title;

    private Integer titleFlag;

    private Date createTime;

    private Date lastEditTime;

    private Integer enableStatus;
    //用户信息
    private PersonInfo employee;
    //商铺
    private Shop shop;

}