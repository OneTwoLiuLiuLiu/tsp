package com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst;

public class JarJobInst extends JobInst{
		
	private String type="jar";
	
	private String filePath;
	
	private String initialMemoryValue;
	
	private String maxMemoryValue;
	
	public String getType() {
		return type;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getInitialMemoryValue() {
		return initialMemoryValue;
	}

	public void setInitialMemoryValue(String initialMemoryValue) {
		this.initialMemoryValue = initialMemoryValue;
	}

	public String getMaxMemoryValue() {
		return maxMemoryValue;
	}

	public void setMaxMemoryValue(String maxMemoryValue) {
		this.maxMemoryValue = maxMemoryValue;
	}
}
