package com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst;

public class BatJobInst extends JobInst{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String type="bat";
	
	private String filePath;

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
