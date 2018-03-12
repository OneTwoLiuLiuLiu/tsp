package com.sunyard.frameworkset.plugin.tsp.manager.vo;

import java.io.Serializable;
import java.util.List;

public class PlanInstanceVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7648248960243422559L;

	/**
	 * 完成
	 */
	public static final String COMPLETE = "2";

	/**
	 * 暂停
	 */
	public static final String PAUSE = "0";

	/**
	 * 运行中
	 */
	public static final String RUNNING = "1";

	/**
	 * 结束
	 */
	public static final String END = "3";
	
	/**
	 * 失败
	 */
	public static final String ERROR = "4";

	private String id;

	private Integer successJobNum;

	private Integer failJobNum;

	private Integer passJobNum;

	private Integer runningJobNum;

	private Integer leftJobNum;

	private String status;

	private String endUser;

	private String endUserCode;

	private String startTime;

	private String endTime;

	private String batchNo;

	private PlanVo planVo;

	public PlanVo getPlanVo() {
		return planVo;
	}

	public void setPlanVo(PlanVo planVo) {
		this.planVo = planVo;
	}

	private String parentPlanInstId;

	private String runPlanId;

	private List<PlanInstParamVo> params;


	public String getId() {
		return id;
	}

	public Integer getSuccessJobNum() {
		return successJobNum;
	}

	public Integer getFailJobNum() {
		return failJobNum;
	}

	public Integer getPassJobNum() {
		return passJobNum;
	}

	public Integer getRunningJobNum() {
		return runningJobNum;
	}

	public Integer getLeftJobNum() {
		return leftJobNum;
	}

	public String getStatus() {
		return status;
	}

	public String getEndUser() {
		return endUser;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSuccessJobNum(Integer successJobNum) {
		this.successJobNum = successJobNum;
	}

	public void setFailJobNum(Integer failJobNum) {
		this.failJobNum = failJobNum;
	}

	public void setPassJobNum(Integer passJobNum) {
		this.passJobNum = passJobNum;
	}

	public void setRunningJobNum(Integer runningJobNum) {
		this.runningJobNum = runningJobNum;
	}

	public void setLeftJobNum(Integer leftJobNum) {
		this.leftJobNum = leftJobNum;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}


	public String getParentPlanInstId() {
		return parentPlanInstId;
	}

	public void setParentPlanInstId(String parentPlanInstId) {
		this.parentPlanInstId = parentPlanInstId;
	}

	public String getEndUserCode() {
		return endUserCode;
	}

	public void setEndUserCode(String endUserCode) {
		this.endUserCode = endUserCode;
	}

	public String getRunPlanId() {
		return runPlanId;
	}

	public void setRunPlanId(String runPlanId) {
		this.runPlanId = runPlanId;
	}

	
	
	
	
	public List<PlanInstParamVo> getParams() {
		return params;
	}

	public void setParams(List<PlanInstParamVo> params) {
		this.params = params;
	}




}
