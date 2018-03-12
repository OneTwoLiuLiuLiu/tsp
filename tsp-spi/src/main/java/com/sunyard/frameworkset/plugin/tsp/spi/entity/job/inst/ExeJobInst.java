package com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst;


public class ExeJobInst extends JobInst{
	
	private String type="exe";
	
	private String filePath;
	
	private String returnValues;
	
	public String getType() {
		return type;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getReturnValues() {
		return returnValues;
	}

	public void setReturnValues(String returnValues) {
		this.returnValues = returnValues;
	}
	
	
	
}
