package com.sunyard.frameworkset.plugin.tsp.manager.vo;

import java.io.Serializable;

public class PlanInstParamVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5579425679902925083L;

	private String id;

	/**
	 * 计划实例号
	 */

	private PlanInstanceVo planInst;


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

	public PlanInstanceVo getPlanInst() {
		return planInst;
	}

	public void setPlanInst(PlanInstanceVo planInst) {
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
