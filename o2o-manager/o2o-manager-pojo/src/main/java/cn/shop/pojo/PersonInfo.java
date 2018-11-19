package cn.shop.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class PersonInfo {
    private Integer userId;

    private String name;

    private Date birthday;

    private String gender;

    private String phone;

    private String email;

    private String profileImg;

    private Integer customerFlag;

    private Integer shopOwnerFlag;

    private Integer adminFlag;

    private Date createTime;

    private Date lastEditTime;

    private Integer enableStatus;
}