package com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig;

public class ShellJobConfigQo extends JobConfigQo {

	private String jobType = "shell";

	private String filePath;

	private String returnValues;


	public String getReturnValues () {
		return returnValues;
	}

	public void setReturnValues (String returnValues) {
		this.returnValues = returnValues;
	}

	public String getFilePath () {
		return filePath;
	}

	public void setFilePath (String filePath) {
		this.filePath = filePath;
	}


	public String getIcon () {
		return "ui-icon-shell";
	}

	public String getJobType () {
		return jobType;
	}


	@Override
	public String toString () {
		return "ShellJobConfig [filePath=" + filePath + "]";
	}


}
