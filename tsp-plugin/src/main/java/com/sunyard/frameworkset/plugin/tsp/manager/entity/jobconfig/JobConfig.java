package com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.dom4j.Element;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Table(name = "tsp_job_config")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("base")
@JsonIgnoreProperties({"planConfig", "infoReuslt", "job"})
public  class JobConfig implements Serializable {


	private static Map<String, Class> jobes = new ConcurrentHashMap<String, Class> ();

	private static final long serialVersionUID = 500557664688465383L;


	@Transient
	private String jobType = "base";

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	private String id;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "run_params", length = 200)
	private String runParams;

	@Column(name = "ignore_err", length = 1)
	private String ignoreErr;

	@Column(name = "retry_cnt")
	private Integer retryCnt = 3;

	@Column(name = "retry_sec")
	private Integer retrySec = 60;


	@Column(name = "create_time", length = 20)
	private String createTime;

	@Column(name = "create_user", length = 20)
	private String createUser;

	@Column(name = "create_user_code", length = 20)
	private String createUserCode;

	@Column(name = "modified_time", length = 20)
	private String modifiedTime;

	@Column(name = "modified_user", length = 20)
	private String modifiedUser;

	@Column(name = "modified_user_code", length = 20)
	private String modifiedUserCode;

	@Column(name = "prev_job_id")
	private String prevJobId;

	@Column(name = "next_job_id")
	private String nextJobId;

	@Column(name = "parent_job_id")
	private String parentJobId;

	@Column(name = "time_format", length = 20)
	private String timeFormat;

	@Column(name = "expect_run_time")
	private Integer expectRunTime;


	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_config_id")
	private PlanConfig planConfig;

	public JobConfig () {
		super ();

	}

	public String getJobType() {
		return jobType;
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





	public String getPrevJobId () {
		return prevJobId;
	}


	public void setPrevJobId (String prevJobId) {
		this.prevJobId = prevJobId;
	}


	public String getNextJobId () {
		return nextJobId;
	}


	public void setNextJobId (String nextJobId) {
		this.nextJobId = nextJobId;
	}


	public String getParentJobId () {
		return parentJobId;
	}


	public void setParentJobId (String parentJobId) {
		this.parentJobId = parentJobId;
	}


	public PlanConfig getPlanConfig () {
		return planConfig;
	}


	public void setPlanConfig (PlanConfig planConfig) {
		this.planConfig = planConfig;
	}

	public String getRunParams () {
		return runParams;
	}

	public void setRunParams (String runParams) {
		this.runParams = runParams;
	}

	public String getIgnoreErr () {
		return ignoreErr;
	}

	public void setIgnoreErr (String ignoreErr) {
		this.ignoreErr = ignoreErr;
	}

	public Integer getRetryCnt () {
		return retryCnt;
	}

	public void setRetryCnt (Integer retryCnt) {
		this.retryCnt = retryCnt;
	}

	public Integer getRetrySec () {
		return retrySec;
	}

	public void setRetrySec (Integer retrySec) {
		this.retrySec = retrySec;
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

	public String getModifiedTime () {
		return modifiedTime;
	}

	public void setModifiedTime (String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getModifiedUser () {
		return modifiedUser;
	}

	public void setModifiedUser (String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getModifiedUserCode () {
		return modifiedUserCode;
	}

	public void setModifiedUserCode (String modifiedUserCode) {
		this.modifiedUserCode = modifiedUserCode;
	}

	public String getTimeFormat () {
		return timeFormat;
	}

	public void setTimeFormat (String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public Integer getExpectRunTime () {
		return expectRunTime;
	}

	public void setExpectRunTime (Integer expectRunTime) {
		this.expectRunTime = expectRunTime;
	}


	/**
	 * 作业的图标
	 *
	 * @return
	 */
	public  String getIcon (){
		return null;
	};

	/**
	 * 转换为Job
	 *
	 * @return
	 */
	public  Job getJob (){
		return null;
	};

	public static void register (String type, Class<? extends JobConfig> clazz) {
		Class cl = jobes.get (type);
		if (cl == null) {
			jobes.put (type, clazz);
		}
	}


	public static Class<? extends JobConfig> getJobConfigClassByType (String type) {
		Class clazz = jobes.get (type);
		if (clazz == null) {
			throw new RuntimeException ("找不到类型为:" + type + "对应的class类");
		}
		return clazz;
	}


	@Override
	public String toString () {
		return "JobConfig [id=" + id + ", name=" + name + ", runParams="
				+ runParams + ", ignoreErr=" + ignoreErr + ", retryCnt="
				+ retryCnt + ", retrySec=" + retrySec + ", createTime="
				+ createTime + ", createUser=" + createUser
				+ ", createUserCode=" + createUserCode + ", modifiedTime="
				+ modifiedTime + ", modifiedUser=" + modifiedUser
				+ ", modifiedUserCode=" + modifiedUserCode + ", prevJobId="
				+ prevJobId + ", nextJobId=" + nextJobId + ", parentJobId="
				+ parentJobId + ", timeFormat=" + timeFormat
				+ ", expectRunTime=" + expectRunTime + ", planConfig="
				+ planConfig + "]";
	}

	public  JobConfig getJobConfigByElement (Element element) throws Exception{
		return null;
	};

}
