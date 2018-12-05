package cn.shop.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class UserProductMap {
    private Integer userProductId;

    private Integer userId;

    private Integer productId;

    private Integer shopId;

    private String userName;

    private String productName;

    private Date createTime;

    private Integer point;

    private PersonInfo user;

    private Product product;

    private Shop shop;

    private PersonInfo operator;
}