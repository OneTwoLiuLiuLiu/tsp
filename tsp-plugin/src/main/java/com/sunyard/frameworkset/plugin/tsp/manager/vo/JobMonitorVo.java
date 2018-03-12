package com.sunyard.frameworkset.plugin.tsp.manager.vo;

import com.sunyard.frameworkset.plugin.tsp.manager.enums.RunningJobEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;

/**
 * Created by Misaki on 2016/12/30.
 */
public class JobMonitorVo {
	private String id;
	private String pName;
	private String jName;
	private String startTime;
	private String endTime;
	private String status;
	private String runHostname;
	private String runTime;
	private String jobId;
	private String planInstId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getjName() {
		return jName;
	}

	public void setjName(String jName) {
		this.jName = jName;
	}

	public String getStartTime () {
		return startTime;
	}

	public void setStartTime (String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime () {
		return endTime;
	}

	public void setEndTime (String endTime) {
		this.endTime = endTime;
	}

	public String getStatus () {
		RunningJobEnum[] runningJobEnums = RunningJobEnum.values ();
		for (RunningJobEnum runningJobEnum : runningJobEnums) {
			if (runningJobEnum.getCode ().equals (status)) {
				return runningJobEnum.getName ();
			}
		}
		return status;
	}

	public void setStatus (String status) {
		this.status = status;
	}

	public String getRunHostname () {
		return runHostname;
	}

	public void setRunHostname (String runHostname) {
		this.runHostname = runHostname;
	}

	public String getRunTime () {
		try{
			long st = DateUtil.strToDate (startTime, "yyyy-MM-dd HH:mm:ss")
					.getTime ();
			long et = DateUtil.strToDate (endTime, "yyyy-MM-dd HH:mm:ss")
					.getTime ();
			long seconds = (et - st) / 1000;
			runTime = "00:00:00";
			if (seconds > 0) {
				long hour = seconds / 3600;
				long minute = (seconds - hour * 3600) / 60;
				long second = (seconds - hour * 3600 - minute * 60);
				String h = hour / 10 > 0 ? "" + hour : "0" + hour;
				String m = minute / 10 > 0 ? "" + minute : "0" + minute;
				String ss = second / 10 > 0 ? "" + second : "0" + second;
				runTime = h + ":" + m + ":" + ss;
			}
		}catch (Exception e){
			return null;
		}
		return runTime;
	}

	public void setRunTime (String runTime) {
		this.runTime = runTime;
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

}
