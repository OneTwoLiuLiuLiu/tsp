package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tsp_plan")
public class Plan implements Serializable {

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
		
	
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "recipients", length = 100)
	private String recipients;

	@Column(name = "recipientsCn", length = 100)
	private String recipientsCn;
	
	
	@Column(name = "notice_way", length = 1)
	private String noticeWay;

	@Column(name = "period", length = 100)
	private String period;

	@Column(name = "deploy_time", length = 20)
	private String deployTime;
	
	@Column(name = "deploy_user", length = 20)
	private String deployUser;
	
	@Column(name = "deploy_user_code",length = 20)
	private String deployUserCode;

	@Column(name = "version", length = 20)
	private Integer version;

	@Column(name="start_kind", length = 1)
	private String startKind;

	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="plan_config_id")
	private PlanConfig planConfig;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="plan")
	private List<Job> jobs;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="plan")
	private List<PlanInstance> planInsts;
	
	public String getId() {
		return id;
	}

	public String getStartKind() {
		return startKind;
	}

	public void setStartKind(String startKind) {
		this.startKind = startKind;
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

	public PlanConfig getPlanConfig() {
		return planConfig;
	}

	public void setPlanConfig(PlanConfig planConfig) {
		this.planConfig = planConfig;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public List<PlanInstance> getPlanInsts() {
		return planInsts;
	}

	public void setPlanInsts(List<PlanInstance> planInsts) {
		this.planInsts = planInsts;
	}

	public List<Job> getJobs() {
		return jobs;
	}

	public void setJobs(List<Job> jobs) {
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
		return "Plan [id=" + id + ", name=" + name + ", recipients="
				+ recipients + ", noticeWay=" + noticeWay + ", period="
				+ period + ", deployTime=" + deployTime + ", version="
				+ version + ", planConfig=" + planConfig + "]";
	}
	
	
}
