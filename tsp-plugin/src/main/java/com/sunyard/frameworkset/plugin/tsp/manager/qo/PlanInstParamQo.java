package com.sunyard.frameworkset.plugin.tsp.manager.qo;

import com.sunyard.frameworkset.util.pages.PagingOrder;

public class PlanInstParamQo extends PagingOrder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5579425679902925083L;

	private String id;

	/**
	 * 计划实例号
	 */

	private PlanInstanceQo planInst;


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

	public PlanInstanceQo getPlanInst() {
		return planInst;
	}

	public void setPlanInst(PlanInstanceQo planInst) {
		this.planInst = planInst;
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
