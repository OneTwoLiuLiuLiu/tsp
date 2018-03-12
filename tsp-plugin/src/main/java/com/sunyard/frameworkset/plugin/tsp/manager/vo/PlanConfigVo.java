package com.sunyard.frameworkset.plugin.tsp.manager.vo;

import com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig.JobConfigVo;

import java.io.Serializable;
import java.util.List;

public class PlanConfigVo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3835101754157927561L;


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

	private String startUser;

	private String stopTime;

	private String stopUser;

	private String stopUserCode;

	private String status;

	private String desc;

	private String startKind;

	private List<PlanVo> plans;

	private List<JobConfigVo> jobConfigVos;


	public PlanConfigVo () {
		super ();

	}

	public String getStartKind() {
		return startKind;
	}

	public void setStartKind(String startKind) {
		this.startKind = startKind;
	}

	public String getDesc () {
		return desc;
	}

	public void setDesc (String desc) {
		this.desc = desc;
	}

	public List<PlanVo> getPlans () {
		return plans;
	}

	public void setPlans (List<PlanVo> plans) {
		this.plans = plans;
	}

	public List<JobConfigVo> getJobConfigVos () {
		return jobConfigVos;
	}

	public void setJobConfigVos (List<JobConfigVo> jobConfig) {
		this.jobConfigVos = jobConfig;
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


	public String getIsShare () {
		return isShare;
	}

	public String getStatus () {
		return status;
	}

	public void setStatus (String status) {
		this.status = status;
	}

	public String getCreateUserCode () {
		return createUserCode;
	}

	public void setCreateUserCode (String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getStopUserCode () {
		return stopUserCode;
	}

	public void setStopUserCode (String stopUserCode) {
		this.stopUserCode = stopUserCode;
	}


	public String getRecipients () {
		return recipients;
	}

	public void setRecipients (String recipients) {
		this.recipients = recipients;
	}
}
