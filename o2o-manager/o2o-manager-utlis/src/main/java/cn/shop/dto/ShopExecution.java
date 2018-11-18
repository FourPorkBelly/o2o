package cn.shop.dto;

import java.util.List;

import cn.shop.enums.ShopStateEnum;
import cn.shop.pojo.Shop;
import lombok.Getter;
import lombok.Setter;

/**
 * 封装执行后结果
 */
@Getter
@Setter
public class ShopExecution {

	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	// 店铺数量
	private int count;

	// 操作的shop（增删改店铺的时候用）
	private Shop shop;

	// 获取的shop列表(查询店铺列表的时候用)
	private List<Shop> shopList;

	public ShopExecution() {
	}

	// 失败的构造器
	public ShopExecution(ShopStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 成功的构造器
	public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop;
	}

	// 成功的构造器
	public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shopList;
	}

}