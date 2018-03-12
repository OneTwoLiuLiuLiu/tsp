package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 手工运行计划的记录表
 * @author whj
 *
 */
@Entity
@Table(name = "tsp_run_plan")
public class RunPlan implements Serializable{
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
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
	
	
	
/*	*//**
	 * 需要运行的计划
	 *//*
	@Column(name = "plan_config_id", length = 40)
	private String planConfigId;*/

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_config_id")
	private PlanConfig planConfig;

	/**
	 * 开始时间
	 */
	@Column(name = "start_time", length = 20)
	private String startTime;
	
	/**
	 * 结束时间
	 */
	@Column(name = "end_time", length = 20)
	private String endTime;
	
	/**
	 * 状态
	 */
	@Column(name = "status", length = 1)
	private String status;

	public PlanConfig getPlanConfig() {
		return planConfig;
	}

	public void setPlanConfig(PlanConfig planConfig) {
		this.planConfig = planConfig;
	}

	/*public String getPlanConfigId() {
		return planConfigId;
	}

	public void setPlanConfigId(String planConfigId) {
		this.planConfigId = planConfigId;
	}*/

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
	
	
	
}
