package com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst;

public class DataStageInst extends JobInst{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dsProjectName;
	
	private String dsJobName;
	
	private String type="datastage";
	
	
	@Override
	public String getType() {
		return type;
	}

	public String getDsProjectName() {
		return dsProjectName;
	}

	public void setDsProjectName(String dsProjectName) {
		this.dsProjectName = dsProjectName;
	}

	public String getDsJobName() {
		return dsJobName;
	}

	public void setDsJobName(String dsJobName) {
		this.dsJobName = dsJobName;
	}

	
	
    	
}
