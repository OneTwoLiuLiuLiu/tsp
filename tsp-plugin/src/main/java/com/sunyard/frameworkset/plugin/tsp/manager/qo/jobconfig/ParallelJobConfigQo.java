package com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig;

public class ParallelJobConfigQo extends JobConfigQo {

	public static final String JOBNAME = "并行";

	private String jobType = "parallel";

	public String getIcon () {
		return "ui-icon-parallel";
	}

	public String getJobType () {
		return jobType;
	}


}
