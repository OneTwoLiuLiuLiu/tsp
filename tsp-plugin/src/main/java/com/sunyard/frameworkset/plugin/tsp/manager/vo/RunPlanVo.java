package com.sunyard.frameworkset.plugin.tsp.manager.vo;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.RunPlanStatusEnum;

import java.io.Serializable;


/**
 * 手工运行计划的记录表
 * @author whj
 *
 */
public class RunPlanVo implements Serializable{
	
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
	
	
	
	/**
	 * 需要运行的计划
	 */
	private PlanConfig planConfig;
	
	/**
	 * 开始时间
	 */
	private String startTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	
	/**
	 * 状态
	 */
	private String status;

	private String statuscn;

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

	public String getStatuscn() {
		if(RunPlanStatusEnum.RUN.getCode().equals(status)){
			return RunPlanStatusEnum.RUN.getName();
		}else if(RunPlanStatusEnum.STOP.getCode().equals(status)){
			return RunPlanStatusEnum.STOP.getName();
		}
		return statuscn;
	}

	public void setStatuscn(String statuscn) {
		this.statuscn = statuscn;
	}
}
