package com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.job.ExeJobQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.job.JobQo;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExeJobConfigQo extends JobConfigQo {

	private static final long serialVersionUID = 1L;

	private String jobType = "exe";

	private String returnValues;

	private String filePath;


	public String getReturnValues () {
		return returnValues;
	}

	public void setReturnValues (String returnValues) {
		this.returnValues = returnValues;
	}

	public String getJobType () {
		return jobType;
	}

	public String getFilePath () {
		return filePath;
	}

	public void setFilePath (String filePath) {
		this.filePath = filePath;
	}

	public String getIcon () {
		return "ui-icon-exe";
	}

	public JobQo getJob () {
		ExeJobQo ej = new ExeJobQo ();
		BeanUtils.copyProperties (this, ej, new String[]{"id"});
		return ej;
	}

	public List<Map<String, Object>> getSubInfoResult () throws Exception {
		List<Map<String, Object>> rsult = new ArrayList<Map<String, Object>> ();
		Class clazz = this.getClass ();
		Field[] fields = clazz.getDeclaredFields ();
		for (Field field : fields) {
			Map<String, Object> map = new HashMap<String, Object> ();
			JobDesc jobDesc = field.getAnnotation (JobDesc.class);
			if (jobDesc != null) {
				field.setAccessible (true);
				map.put ("text", jobDesc.desc ());
				map.put ("value", field.get (this));
				rsult.add (map);
			}
		}
		return rsult;
	}


}
