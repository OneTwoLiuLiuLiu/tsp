package com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.job.JarJobQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.job.JobQo;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JarJobConfigQo extends JobConfigQo {

	private String jobType = "jar";

	private String filePath;

	private String initialMemoryValue;

	private String maxMemoryValue;

	public String getFilePath () {
		return filePath;
	}

	public void setFilePath (String filePath) {
		this.filePath = filePath;
	}

	public String getInitialMemoryValue () {
		return initialMemoryValue;
	}

	public void setInitialMemoryValue (String initialMemoryValue) {
		this.initialMemoryValue = initialMemoryValue;
	}

	public String getMaxMemoryValue () {
		return maxMemoryValue;
	}

	public void setMaxMemoryValue (String maxMemoryValue) {
		this.maxMemoryValue = maxMemoryValue;
	}

	public String getIcon () {
		return "ui-icon-java";
	}

	public String getJobType () {
		return jobType;
	}

	public JobQo getJob () {
		JarJobQo jj = new JarJobQo ();
		BeanUtils.copyProperties (this, jj, new String[]{"id"});
		return jj;
	}

	public List<Map<String, Object>> getSubInfoResult () throws Exception {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>> ();
		Class clazz = this.getClass ();
		Field[] fields = clazz.getDeclaredFields ();
		for (Field field : fields) {
			Map<String, Object> map = new HashMap<String, Object> ();
			JobDesc job = field.getAnnotation (JobDesc.class);
			if (job != null) {
				field.setAccessible (true);
				map.put ("text", job.desc ());
				map.put ("value", field.get (this));
				lists.add (map);
			}
		}
		return lists;
	}


}
