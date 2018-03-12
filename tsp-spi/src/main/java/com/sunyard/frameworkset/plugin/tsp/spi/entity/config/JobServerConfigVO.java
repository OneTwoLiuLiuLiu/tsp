package com.sunyard.frameworkset.plugin.tsp.spi.entity.config;

import java.util.Arrays;
import java.util.Map;

public class JobServerConfigVO {
	
	private Integer maxThreadNum;
	
	private String[] runJobTypes;
	
	private Map<String, String> runClasses;
	
	private Integer  freeMemory;
	
	private Integer freeCPU;
	
	/**
	 * 历史工作日志最大的保存时间
	 */
	private Integer maxHistoryDay;
	
	
	
	public Integer getMaxHistoryDay() {
		return maxHistoryDay;
	}

	public void setMaxHistoryDay(Integer maxHistoryDay) {
		this.maxHistoryDay = maxHistoryDay;
	}

	public Integer getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(Integer freeMemory) {
		this.freeMemory = freeMemory;
	}

	public Integer getFreeCPU() {
		return freeCPU;
	}

	public void setFreeCPU(Integer freeCPU) {
		this.freeCPU = freeCPU;
	}

	public Integer getMaxThreadNum() {
		return maxThreadNum;
	}

	public void setMaxThreadNum(Integer maxThreadNum) {
		this.maxThreadNum = maxThreadNum;
	}

	public String[] getRunJobTypes() {
		return runJobTypes;
	}

	public void setRunJobTypes(String[] runJobTypes) {
		this.runJobTypes = runJobTypes;
	}

	public Map<String, String> getRunClasses() {
		return runClasses;
	}

	public void setRunClasses(Map<String, String> runClasses) {
		this.runClasses = runClasses;
	}

	@Override
	public String toString() {
		return "JobServerConfigVO [maxThreadNum=" + maxThreadNum
				+ ", runJobTypes=" + Arrays.toString(runJobTypes)
				+ ", runClasses=" + runClasses + ", freeMemory=" + freeMemory
				+ ", freeCPU=" + freeCPU + ", maxHistoryDay=" + maxHistoryDay
				+ "]";
	}


	
	
	
}
