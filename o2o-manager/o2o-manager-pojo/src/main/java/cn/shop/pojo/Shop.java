package cn.shop.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Getter
@Setter
@ToString
public class Shop {
    private Integer shopId;

    private Integer ownerId;

    private Integer areaId;

    private Integer shopCategoryId;

    private Integer parentCategoryId;

    private String shopName;

    private String shopDesc;

    private String shopAddr;

    private String phone;

    private String shopImg;

    private Double longitude;

    private Double latitude;

    private Integer priority;
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd") //出参
    private Date createTime;
//    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd") //出参
    private Date lastEditTime;

    private Integer enableStatus;

    private String advice;

    // 位置
    private Area area;
    //用户信息
    private PersonInfo owner;
    //店铺类型
    private ShopCategory shopCategory;

}