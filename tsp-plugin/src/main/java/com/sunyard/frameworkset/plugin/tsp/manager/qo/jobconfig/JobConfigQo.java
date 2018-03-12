package com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.util.pages.PagingOrder;

public class JobConfigQo extends PagingOrder {


	private String jobType = "base";

	private String id;

	private String name;

	private String runParams;

	private String ignoreErr;

	private Integer retryCnt = 3;

	private Integer retrySec = 60;

	private String createTime;

	private String createUser;

	private String createUserCode;

	private String modifiedTime;

	private String modifiedUser;

	private String modifiedUserCode;

	private String prevJobId;

	private String nextJobId;

	private String parentJobId;

	private String timeFormat;

	private Integer expectRunTime;

	private PlanConfig planConfig;

	private String planConfigId;

	public String getPlanConfigId () {
		return planConfigId;
	}

	public void setPlanConfigId (String planConfigId) {
		this.planConfigId = planConfigId;
	}

	public JobConfigQo () {
		super ();

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


	public void setJobType (String jobType) {
		this.jobType = jobType;
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


}
