package cn.shop.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Getter
@Setter
@ToString
public class Award {
    private Integer awardId;

    private String awardName;

    private String awardDesc;

    private String awardImg;

    private Integer point;

    private Integer priority;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date expireTime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date lastEditTime;

    private Integer enableStatus;

    private Integer shopId;

    private PersonInfo personInfo;
}