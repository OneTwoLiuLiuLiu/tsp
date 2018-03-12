package com.sunyard.frameworkset.plugin.tsp.manager.qo;

import com.sunyard.frameworkset.util.pages.PagingOrder;

import java.io.Serializable;
import java.util.List;

public class PlanInstanceQo extends PagingOrder {

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

	private PlanQo plan;

	private String parentPlanInstId;

	private String runPlanId;

	private List<PlanInstParamQo> params;

	public PlanInstanceQo () {

	}

	public PlanInstanceQo (String id) {
		super();
		this.id = id;
	}

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

	public PlanQo getPlan() {
		return plan;
	}

	public void setPlan(PlanQo plan) {
		this.plan = plan;
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

	
	
	
	
	public List<PlanInstParamQo> getParams() {
		return params;
	}

	public void setParams(List<PlanInstParamQo> params) {
		this.params = params;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((endUser == null) ? 0 : endUser.hashCode());
		result = prime * result
				+ ((failJobNum == null) ? 0 : failJobNum.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((leftJobNum == null) ? 0 : leftJobNum.hashCode());
		result = prime * result
				+ ((passJobNum == null) ? 0 : passJobNum.hashCode());
		result = prime * result
				+ ((runningJobNum == null) ? 0 : runningJobNum.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((successJobNum == null) ? 0 : successJobNum.hashCode());
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
		PlanInstanceQo other = (PlanInstanceQo) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (endUser == null) {
			if (other.endUser != null)
				return false;
		} else if (!endUser.equals(other.endUser))
			return false;
		if (failJobNum == null) {
			if (other.failJobNum != null)
				return false;
		} else if (!failJobNum.equals(other.failJobNum))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (leftJobNum == null) {
			if (other.leftJobNum != null)
				return false;
		} else if (!leftJobNum.equals(other.leftJobNum))
			return false;
		if (passJobNum == null) {
			if (other.passJobNum != null)
				return false;
		} else if (!passJobNum.equals(other.passJobNum))
			return false;
		if (runningJobNum == null) {
			if (other.runningJobNum != null)
				return false;
		} else if (!runningJobNum.equals(other.runningJobNum))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (successJobNum == null) {
			if (other.successJobNum != null)
				return false;
		} else if (!successJobNum.equals(other.successJobNum))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlanInstanceVo [id=" + id + ", successJobNum=" + successJobNum
				+ ", failJobNum=" + failJobNum + ", passJobNum=" + passJobNum
				+ ", runningJobNum=" + runningJobNum + ", leftJobNum="
				+ leftJobNum + ", status=" + status + ", endUser=" + endUser
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
