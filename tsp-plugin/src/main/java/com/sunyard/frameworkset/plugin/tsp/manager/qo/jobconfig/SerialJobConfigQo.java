package com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig;

public class SerialJobConfigQo extends JobConfigQo {

	public static final String JOBNAME = "串行";

	private String jobType = "serial";

	public String getIcon () {
		return "ui-icon-serial";
	}


	public String getJobType () {
		return jobType;
	}


}
