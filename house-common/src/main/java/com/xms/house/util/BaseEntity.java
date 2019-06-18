package com.xms.house.util;

import java.io.Serializable;

public class BaseEntity implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private Boolean master=false;

	public Boolean getMaster() {
		return master;
	}

	public void setMaster(Boolean master) {
		this.master = master;
	}
}
