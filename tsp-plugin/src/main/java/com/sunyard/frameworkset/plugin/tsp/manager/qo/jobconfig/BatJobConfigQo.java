package com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BatJobConfigQo extends JobConfigQo {

	private String jobType = "bat";

	private String filePath;

	public String getFilePath () {
		return filePath;
	}

	public void setFilePath (String filePath) {
		this.filePath = filePath;
	}


	public String getIcon () {
		return "ui-icon-bat";
	}

	public String getJobType () {
		return jobType;
	}


	@Override
	public String toString () {
		return "BatJobConfig [filePath=" + filePath + "]";
	}


	public List<Map<String, Object>> getSubInfoResult () throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>> ();
		Class clazz = this.getClass ();
		Field[] fields = clazz.getDeclaredFields ();
		for (Field field : fields) {
			Map<String, Object> map = new HashMap<String, Object> ();
			JobDesc job = field.getAnnotation (JobDesc.class);
			if (job != null) {
				field.setAccessible (true);
				map.put ("text", job.desc ());
				map.put ("value", field.get (this));
				result.add (map);
			}
		}
		return result;
	}
}
