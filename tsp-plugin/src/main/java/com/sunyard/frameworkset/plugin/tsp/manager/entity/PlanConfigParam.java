package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tsp_planConfig_params")
public class PlanConfigParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5579425679902925083L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	/**
	 * 计划实例号
	 */
	@Column(name="plan_config_Id",length=40)
	private String planConfigId;
	
	
	/**
	 * 参数名称
	 * 
	 */
	@Column(name="param_name",length=100)
	private String paramName;
	
	/**
	 * 参数值
	 */
	@Column(name="param_value")
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
		planConfigId = planConfigId;
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
