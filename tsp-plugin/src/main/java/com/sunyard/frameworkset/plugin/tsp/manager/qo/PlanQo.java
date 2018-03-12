package com.sunyard.frameworkset.plugin.tsp.manager.qo;

import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import com.sunyard.frameworkset.util.pages.PagingOrder;

import java.util.List;

public class PlanQo extends PagingOrder {

	/**
		 * 
		 */
	private static final long serialVersionUID = -3835101754157927561L;
	
	/**
	 * 通知方式：短信
	 */
	public static final String MESSAGE="1";
		
	/**
	 * 通知方式：邮件
	 */
	public static final String MAIL="2";
	
	/**
	 * 短信和邮件
	 */
	public static final String MAILANDMESSAGE="3";

	private String id;

	private String name;

	private String recipients;

	private String recipientsCn;

	private String noticeWay;

	private String period;

	private String deployTime;

	private String deployUser;

	private String deployUserCode;

	private Integer version;

	private PlanConfigQo planConfig;

	private List<JobVo> jobs;

	private List<PlanInstanceQo> planInsts;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public String getNoticeWay() {
		return noticeWay;
	}

	public void setNoticeWay(String noticeWay) {
		this.noticeWay = noticeWay;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getDeployTime() {
		return deployTime;
	}

	public void setDeployTime(String deployTime) {
		this.deployTime = deployTime;
	}

	public String getDeployUser() {
		return deployUser;
	}

	public void setDeployUser(String deployUser) {
		this.deployUser = deployUser;
	}

	public String getDeployUserCode() {
		return deployUserCode;
	}

	public void setDeployUserCode(String deployUserCode) {
		this.deployUserCode = deployUserCode;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public PlanConfigQo getPlanConfig() {
		return planConfig;
	}

	public void setPlanConfig(PlanConfigQo planConfig) {
		this.planConfig = planConfig;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<PlanInstanceQo> getPlanInsts() {
		return planInsts;
	}

	public void setPlanInsts(List<PlanInstanceQo> planInsts) {
		this.planInsts = planInsts;
	}

	public List<JobVo> getJobs() {
		return jobs;
	}

	public void setJobs(List<JobVo> jobs) {
		this.jobs = jobs;
	}

	public String getRecipientsCn() {
		return recipientsCn;
	}

	public void setRecipientsCn(String recipientsCn) {
		this.recipientsCn = recipientsCn;
	}

	@Override
	public String toString() {
		return "PlanVo [id=" + id + ", name=" + name + ", recipients="
				+ recipients + ", noticeWay=" + noticeWay + ", period="
				+ period + ", deployTime=" + deployTime + ", version="
				+ version + ", planConfig=" + planConfig + "]";
	}
	
	
}
