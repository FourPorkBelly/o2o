package cn.shop.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 微信登录实体类
 */
@Getter
@Setter
@ToString
public class WechatAuth {
    //主键id
    private Integer wechatAuthId;
    //用户id
    private Integer userId;
    //微信获取用户信息的凭证，对于公众号具有唯一性
    private String openId;
    //创建时间
    private Date createTime;
    //用户信息
    private PersonInfo personInfo;
}