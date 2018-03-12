package com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst;


public class HttpJobInst extends JobInst{
			
	private String type="http";
	
	private String httpUrl;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHttpUrl() {
		return httpUrl;
	}

	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
}
