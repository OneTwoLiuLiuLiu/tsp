package com.sunyard.frameworkset.plugin.tsp.manager.qo;

import com.sunyard.frameworkset.plugin.tsp.manager.enums.PlanConfigStatusEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.PlanMonitorEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig.JobConfigVo;
import com.sunyard.frameworkset.util.pages.PagingOrder;

import java.util.List;

public class PlanConfigQo extends PagingOrder {
	private String id;

	private String name;

	private String recipients;

	private String recipientsCn;

	private String noticeWay;

	private String period;

	private String isShare;

	private String createTime;

	private String createUser;

	private String createUserCode;

	private String startTime;

	private String startTimeBelow;

	private String startTimeTop;

	private String statusChange;

	public String getStatusChange() {
		PlanConfigStatusEnum[] planConfigStatusEnms = PlanConfigStatusEnum.values ();
		for (PlanConfigStatusEnum planConfigStatusEnum : planConfigStatusEnms) {
			if (planConfigStatusEnum.getCode ().equals (status)) {
				return planConfigStatusEnum.getName ();
			}
		}
		return statusChange;
	}

	public void setStatusChange(String statusChange) {
		this.statusChange = statusChange;
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

	private String endTimeBelow;

	private String endTimeTop;

	public String getStartTimeBelow() {
		return startTimeBelow;
	}

	public void setStartTimeBelow(String startTimeBelow) {
		this.startTimeBelow = startTimeBelow;
	}

	private String startUser;

	private String startUserCode;

	private String stopTime;

	private String stopUser;

	private String stopUserCode;

	private String status;

	private String desc;

	private List<PlanQo> plans;

	private List<JobConfigVo> jobConfig;


	public PlanConfigQo () {
		super ();

	}

	public String getDesc () {
		return desc;
	}

	public void setDesc (String desc) {
		this.desc = desc;
	}

	public List<PlanQo> getPlans () {
		return plans;
	}

	public void setPlans (List<PlanQo> plans) {
		this.plans = plans;
	}

	public List<JobConfigVo> getJobConfig () {
		return jobConfig;
	}

	public void setJobConfig (List<JobConfigVo> jobConfig) {
		this.jobConfig = jobConfig;
	}

	public String getRecipientsCn () {
		return recipientsCn;
	}

	public void setRecipientsCn (String recipientsCn) {
		this.recipientsCn = recipientsCn;
	}


	public String getId () {
		return id;
	}

	public void setId (String id) {
		this.id = id;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getRecipients () {
		return recipients;
	}

	public void setRecipients (String recipients) {
		this.recipients = recipients;
	}

	public String getNoticeWay () {
		return noticeWay;
	}

	public void setNoticeWay (String noticeWay) {
		this.noticeWay = noticeWay;
	}

	public String getPeriod () {
		return period;
	}

	public void setPeriod (String period) {
		this.period = period;
	}

	public String getIsShare () {
		return isShare;
	}

	public void setIsShare (String isShare) {
		this.isShare = isShare;
	}

	public String getCreateTime () {
		return createTime;
	}

	public void setCreateTime (String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser () {
		return createUser;
	}

	public void setCreateUser (String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUserCode () {
		return createUserCode;
	}

	public void setCreateUserCode (String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getStartTime () {
		return startTime;
	}

	public void setStartTime (String startTime) {
		this.startTime = startTime;
	}

	public String getStartUser () {
		return startUser;
	}

	public void setStartUser (String startUser) {
		this.startUser = startUser;
	}


	public String getStartUserCode () {
		return startUserCode;
	}

	public void setStartUserCode (String startUserCode) {
		this.startUserCode = startUserCode;
	}

	public String getStopTime () {
		return stopTime;
	}

	public void setStopTime (String stopTime) {
		this.stopTime = stopTime;
	}

	public String getStopUser () {
		return stopUser;
	}

	public void setStopUser (String stopUser) {
		this.stopUser = stopUser;
	}

	public String getStopUserCode () {
		return stopUserCode;
	}

	public void setStopUserCode (String stopUserCode) {
		this.stopUserCode = stopUserCode;
	}

	public String getStatus () {
		return status;
	}

	public void setStatus (String status) {
		this.status = status;
	}
}
