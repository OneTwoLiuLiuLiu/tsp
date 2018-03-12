package com.sunyard.frameworkset.plugin.tsp.spi.spi.entity;

import java.io.Serializable;

public class ResultMessage implements Serializable{


	private static final long serialVersionUID = 1L;

	/**
	 * runningjob中的ID
	 */
	private String runjobId;
	
	/**
	 * 需要返回的结果信息
	 */
	private Object result;
	

	/**
	 * 成功的标志
	 */
	private Boolean success=true;


	public String getRunjobId() {
		return runjobId;
	}


	public void setRunjobId(String runjobId) {
		this.runjobId = runjobId;
	}


	public Object getResult() {
		return result;
	}


	public void setResult(Object result) {
		this.result = result;
	}


	public Boolean getSuccess() {
		return success;
	}


	public void setSuccess(Boolean success) {
		this.success = success;
	}	
}
