package com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst;

public class ShellJobInst extends JobInst{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String type="shell";
	
	private String filePath;

	private String returnValues;
	
	
	
	public String getReturnValues() {
		return returnValues;
	}

	public void setReturnValues(String returnValues) {
		this.returnValues = returnValues;
	}

	public String getType() {
		return type;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
	@Override
	public String toString() {
		String s = "[filePath:"+filePath+"  runParams:"+this.getRunParams()+"]";
		return s;
	}
}
