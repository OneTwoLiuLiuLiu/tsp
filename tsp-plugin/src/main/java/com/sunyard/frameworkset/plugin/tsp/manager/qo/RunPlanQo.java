package com.sunyard.frameworkset.plugin.tsp.manager.qo;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.util.pages.PagingOrder;


/**
 * 手工运行计划的记录表
 * @author whj
 *
 */
public class RunPlanQo extends PagingOrder {
	
	private String id;
	/**
	 * 停止
	 */
	public static final String STOP="0";
	
	/*
	 * 运行
	 */
	public static final String RUN="1";
	
	
	/**
	 * 完成
	 */
	public static final String COMPLETE="2";
	
	
	private String planconfigname;


	/**
	 * 需要运行的计划
	 */
	private PlanConfig planConfig;
	
	/**
	 * 开始时间
	 */
	private String startTime;

	private String startTimeBelow;

	private String startTimeTop;
	
	/**
	 * 结束时间
	 */
	private String endTime;

	private String endTimeBelow;

	private String endTimeTop;

	/**
	 * 状态
	 */
	private String status;

	public PlanConfig getPlanConfig() {
		return planConfig;
	}

	public void setPlanConfig(PlanConfig planConfig) {
		this.planConfig = planConfig;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlanconfigname() {
		return planconfigname;
	}

	public void setPlanconfigname(String planconfigname) {
		this.planconfigname = planconfigname;
	}

	public String getStartTimeTop() {
		return startTimeTop;
	}

	public void setStartTimeTop(String startTimeTop) {
		this.startTimeTop = startTimeTop;
	}

	public String getEndTimeBelow() {
		return endTimeBelow;
	}

	public void setEndTimeBelow(String endTimeBelow) {
		this.endTimeBelow = endTimeBelow;
	}

	public String getEndTimeTop() {
		return endTimeTop;
	}

	public void setEndTimeTop(String endTimeTop) {
		this.endTimeTop = endTimeTop;
	}

	public String getStartTimeBelow() {

		return startTimeBelow;
	}

	public void setStartTimeBelow(String startTimeBelow) {
		this.startTimeBelow = startTimeBelow;
	}


}
