package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tsp_jobserver_config")
public class JobServerConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4330918830875105936L;
	
	/**
	 * 正常状态
	 */
	public static final String NORMAL="1";
	
	/**
	 * 断开状态
	 */
	public static final String DISCONNECT="0";

	/**
	 * 不启用状态
	 */
	public static final String DISABLE="0";
	
	/**
	 * 启用状态
	 */
	public static final String ABLE="1";
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name = "hostname", length = 100)
	private String hostname;

	@Column(name = "max_run_num")
	private Integer maxRunNum=50;

	@Column(name = "min_free_memory")
	private Integer minFreeMemory=10;

	@Column(name = "min_free_cpu")
	private Integer minFreeCpu=20;
	@Column(name = "max_history_day")
	private Integer maxHistoryDay=5;
	
	/**
	 * 作业服务器是否可用
	 * 0：表示不可用
	 * 1：表示可用
	 */
	@Column(name = "status", length = 1)
	private String status;

	/**
	 * 多个类型用英文逗号分隔
	 */
	@Column(name = "run_job_type")
	private String runJobType;

	/**
	 * 个人建议不需要，由字典翻译即可
	 */
	@Deprecated
	@Column(name = "run_job_type_cn")
	private String runJobTypeCn;
	
	
	
	public JobServerConfig() {

	}

	public JobServerConfig(String id) {
		super();
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getRunJobTypeCn() {
		return runJobTypeCn;
	}

	public void setRunJobTypeCn(String runJobTypeCn) {
		this.runJobTypeCn = runJobTypeCn;
	}

	public Integer getMaxHistoryDay() {
		return maxHistoryDay;
	}

	public void setMaxHistoryDay(Integer maxHistoryDay) {
		this.maxHistoryDay = maxHistoryDay;
	}

	public String getId() {
		return id;
	}

	public String getHostname() {
		return hostname;
	}

	public Integer getMaxRunNum() {
		return maxRunNum;
	}

	public Integer getMinFreeMemory() {
		return minFreeMemory;
	}

	public Integer getMinFreeCpu() {
		return minFreeCpu;
	}

	public String getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setMaxRunNum(Integer maxRunNum) {
		this.maxRunNum = maxRunNum;
	}

	public void setMinFreeMemory(Integer minFreeMemory) {
		this.minFreeMemory = minFreeMemory;
	}

	public void setMinFreeCpu(Integer minFreeCpu) {
		this.minFreeCpu = minFreeCpu;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRunJobType() {
		return runJobType;
	}

	public void setRunJobType(String runJobType) {
		this.runJobType = runJobType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hostname == null) ? 0 : hostname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((maxRunNum == null) ? 0 : maxRunNum.hashCode());
		result = prime * result
				+ ((minFreeCpu == null) ? 0 : minFreeCpu.hashCode());
		result = prime * result
				+ ((minFreeMemory == null) ? 0 : minFreeMemory.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobServerConfig other = (JobServerConfig) obj;
		if (hostname == null) {
			if (other.hostname != null)
				return false;
		} else if (!hostname.equals(other.hostname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (maxRunNum == null) {
			if (other.maxRunNum != null)
				return false;
		} else if (!maxRunNum.equals(other.maxRunNum))
			return false;
		if (minFreeCpu == null) {
			if (other.minFreeCpu != null)
				return false;
		} else if (!minFreeCpu.equals(other.minFreeCpu))
			return false;
		if (minFreeMemory == null) {
			if (other.minFreeMemory != null)
				return false;
		} else if (!minFreeMemory.equals(other.minFreeMemory))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JobServiceConfig [id=" + id + ", hostname=" + hostname
				+ ", maxRunNum=" + maxRunNum + ", minFreeMemory="
				+ minFreeMemory + ", minFreeCpu=" + minFreeCpu + ", status="
				+ status + "]";
	}

}
