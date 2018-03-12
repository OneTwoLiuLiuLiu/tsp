package com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig;

public class PlanJobConfigQo extends JobConfigQo {

	/**
	 *
	 */
	private static final long serialVersionUID = -1771013225922866099L;

	private String jobType = "plan";

	private String callPlanConfigId;

	private String callPlanConfigName;


	public String getIcon () {
		return "ui-icon-plan";
	}

	public String getJobType () {
		return jobType;
	}

	public String getCallPlanConfigId () {
		return callPlanConfigId;
	}

	public void setCallPlanConfigId (String callPlanConfigId) {
		this.callPlanConfigId = callPlanConfigId;
	}

	public String getCallPlanConfigName () {
		return callPlanConfigName;
	}

	public void setCallPlanConfigName (String callPlanConfigName) {
		this.callPlanConfigName = callPlanConfigName;
	}


}
