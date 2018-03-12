package com.sunyard.frameworkset.plugin.tsp.manager.vo;

import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;

import java.io.Serializable;
import java.util.List;

public class PlanVo implements Serializable {

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

	private PlanConfigVo planConfigVo;

	public PlanConfigVo getPlanConfigVo() {
		return planConfigVo;
	}

	public void setPlanConfigVo(PlanConfigVo planConfigVo) {
		this.planConfigVo = planConfigVo;
	}

	private List<JobVo> jobVos;

	public List<PlanInstanceVo> getPlanInstVos() {
		return planInstVos;
	}

	public void setPlanInstVos(List<PlanInstanceVo> planInstVos) {
		this.planInstVos = planInstVos;
	}

	public List<JobVo> getJobVos() {
		return jobVos;
	}

	public void setJobVos(List<JobVo> jobVos) {
		this.jobVos = jobVos;
	}

	private List<PlanInstanceVo> planInstVos;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
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
				+ version + ", planConfig=" + planConfigVo + "]";
	}
	
	
}
