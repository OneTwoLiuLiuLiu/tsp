package com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStageJobConfigQo extends JobConfigQo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String jobType="datastage";
	

	private String dsProjectName;

	private String dsJobName;

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

	public String getJobType() {
		return jobType;
	}
	
	public String getIcon(){
		return "ui-icon-datasage";
	}

	
	@Override
	public String toString() {
		return "DataStageJobVo [jobType=" + jobType + ", dsProjectName="
				+ dsProjectName + ", dsJobName=" + dsJobName
				+ ", getRunParams()=" + getRunParams() + "]";
	}
	public List<Map<String, Object>> getSubInfoResult() throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Class clazz = this.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Map<String, Object> map = new HashMap<String, Object>();
			JobDesc job = field.getAnnotation(JobDesc.class);
			if (job != null) {
				field.setAccessible(true);
				map.put("text", job.desc());
				map.put("value", field.get(this));		
				result.add(map);
			}
		}
		return result;
	}


}
