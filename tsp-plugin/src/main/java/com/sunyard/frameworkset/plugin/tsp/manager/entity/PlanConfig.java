package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tsp_plan_config")
public class PlanConfig implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = -3835101754157927561L;
   


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "recipients", length = 100)
	private String recipients;

	public List<RunPlan> getRunPlans() {
		return runPlans;
	}

	public void setRunPlans(List<RunPlan> runPlans) {
		this.runPlans = runPlans;
	}

	@Column(name = "recipientsCn", length = 100)
	private String recipientsCn;

	@Column(name = "notice_way", length = 1)
	private String noticeWay;

	@Column(name = "period", length = 100)
	private String period;

	@Column(name = "is_share", length = 1)
	private String isShare;

	
	@Column(name = "create_time", length = 20)
	private String createTime;
	
	@Column(name = "create_user", length = 20)
	private String createUser;
	
	@Column(name = "create_user_code",length = 20)
	private String createUserCode;

	
	@Column(name = "start_time", length = 20)
	private String startTime;
	
	@Column(name = "start_user", length = 20)
	private String startUser;
	
	@Column(name = "start_user_code",length = 20)
	private String startUserCode;
	
	
	@Column(name = "stop_time",length = 20)
	private String stopTime;

	@Column(name = "stop_user",length = 20)
	private String stopUser;
	
	@Column(name = "stop_user_code",length = 20)
	private String stopUserCode;
	

	@Column(name = "status", length = 1)
	private String status;

	@Column(name="start_kind",length = 1)
	private String startKind;

	@Column(name = "remake", length = 1024)
	private String desc;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="planConfig")
	private List<Plan> plans;

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="planConfig")
	private List<RunPlan> runPlans;

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="planConfig")
	private List<JobConfig> jobConfig;
	
	
	public PlanConfig() {
		super();

	}


	public String getStartKind() {
		return startKind;
	}

	public void setStartKind(String startKind) {
		this.startKind = startKind;
	}

	public String getDesc() {
		return desc;
	}




	public void setDesc(String desc) {
		this.desc = desc;
	}




	public List<Plan> getPlans() {
		return plans;
	}

	public void setPlans(List<Plan> plans) {
		this.plans = plans;
	}

	public List<JobConfig> getJobConfig() {
		return jobConfig;
	}

	public void setJobConfig(List<JobConfig> jobConfig) {
		this.jobConfig = jobConfig;
	}
	
	public String getRecipientsCn() {
		return recipientsCn;
	}

	public void setRecipientsCn(String recipientsCn) {
		this.recipientsCn = recipientsCn;
	}




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

	public String getIsShare() {
		return isShare;
	}

	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartUser() {
		return startUser;
	}

	public void setStartUser(String startUser) {
		this.startUser = startUser;
	}
	
	
	public String getStartUserCode() {
		return startUserCode;
	}

	public void setStartUserCode(String startUserCode) {
		this.startUserCode = startUserCode;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public String getStopUser() {
		return stopUser;
	}

	public void setStopUser(String stopUser) {
		this.stopUser = stopUser;
	}

	public String getStopUserCode() {
		return stopUserCode;
	}

	public void setStopUserCode(String stopUserCode) {
		this.stopUserCode = stopUserCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
