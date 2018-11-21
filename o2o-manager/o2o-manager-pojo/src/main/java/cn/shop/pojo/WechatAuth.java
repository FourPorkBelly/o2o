package cn.shop.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class WechatAuth {
    private Integer wechatAuthId;

    private Integer userId;

    private String openId;

    private Date createTime;

    private PersonInfo personInfo;
}