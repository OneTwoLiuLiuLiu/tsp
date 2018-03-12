package com.sunyard.frameworkset.plugin.tsp.manager.qo;

import com.sunyard.frameworkset.plugin.tsp.manager.enums.RunningJobEnum;
import com.sunyard.frameworkset.util.pages.PagingOrder;

import java.util.List;

/**
 * Created by Misaki on 2016/12/30.
 */
public class JobMonitorQo extends PagingOrder {
	private String id;
	private String pName;
	private String jobType;
	private String startBeforeTime;
	private String startEndTime;
	private String endBeforeTime;
	private String endEndTime;
	private String status;
	private String jobId;
	private String planInstId;
	private String runHostname;
	private String statusChange;
	private List jobIds;

	public String getStatusChange() {
		RunningJobEnum[] runningJobEnums = RunningJobEnum.values ();
		for (RunningJobEnum runningJobEnum : runningJobEnums) {
			if (runningJobEnum.getCode ().equals (status)) {
				return runningJobEnum.getName ();
			}
		}
		return statusChange;
	}

	public void setStatusChange(String statusChange) {
		this.statusChange = statusChange;
	}

	public String getRunHostname() {
		return runHostname;
	}

	public void setRunHostname(String runHostname) {
		this.runHostname = runHostname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpName () {
		return pName;
	}

	public void setpName (String pName) {
		this.pName = pName;
	}

	public String getStartBeforeTime () {
		return startBeforeTime;
	}

	public void setStartBeforeTime (String startBeforeTime) {
		this.startBeforeTime = startBeforeTime;
	}

	public String getStartEndTime () {
		return startEndTime;
	}

	public void setStartEndTime (String startEndTime) {
		this.startEndTime = startEndTime;
	}

	public String getEndBeforeTime () {
		return endBeforeTime;
	}

	public void setEndBeforeTime (String endBeforeTime) {
		this.endBeforeTime = endBeforeTime;
	}

	public String getEndEndTime () {
		return endEndTime;
	}

	public void setEndEndTime (String endEndTime) {
		this.endEndTime = endEndTime;
	}

	public String getStatus () {
		return status;
	}

	public void setStatus (String status) {
		this.status = status;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getPlanInstId() {
		return planInstId;
	}

	public void setPlanInstId(String planInstId) {
		this.planInstId = planInstId;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public List getJobIds() {
		return jobIds;
	}

	public void setJobIds(List jobIds) {
		this.jobIds = jobIds;
	}
}
