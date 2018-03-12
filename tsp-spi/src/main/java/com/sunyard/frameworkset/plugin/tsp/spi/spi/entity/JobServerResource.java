package com.sunyard.frameworkset.plugin.tsp.spi.spi.entity;

import java.io.Serializable;

public class JobServerResource implements Serializable{

	private static final long serialVersionUID = 5636304229449456444L;

	private Integer freeThreadNum=0;
	
	private boolean isEnoughResource=false;

	public Integer getFreeThreadNum() {
		return freeThreadNum;
	}

	public void setFreeThreadNum(Integer freeThreadNum) {
		this.freeThreadNum = freeThreadNum;
	}

	public boolean isEnoughResource() {
		return isEnoughResource;
	}

	public void setEnoughResource(boolean isEnoughResource) {
		this.isEnoughResource = isEnoughResource;
	}
	
}
