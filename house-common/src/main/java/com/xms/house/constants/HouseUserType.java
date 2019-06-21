package com.xms.house.constants;

public enum HouseUserType {

	//售卖1 收藏2
	SALE(1),BOOKMARK(2);
	
	public final Integer value;
	
	private HouseUserType(Integer value){
		this.value = value;
	}
}
