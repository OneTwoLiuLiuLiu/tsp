package com.sunyard.frameworkset.plugin.tsp.manager.qo;

import com.sunyard.frameworkset.util.pages.PagingOrder;

public class PlanConfigParamQo extends PagingOrder {


	private String id;

	/**
	 * 计划实例号
	 */
	private String planConfigId;
	
	
	/**
	 * 参数名称
	 * 
	 */
	private String paramName;
	
	/**
	 * 参数值
	 */
	private String paramValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public String getPlanConfigId() {
		return planConfigId;
	}

	public void setPlanConfigId(String planConfigId) {
		this.planConfigId = planConfigId;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	
	
}
