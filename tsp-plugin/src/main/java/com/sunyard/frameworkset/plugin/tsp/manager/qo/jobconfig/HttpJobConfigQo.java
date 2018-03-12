package com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.job.HttpJobQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.job.JobQo;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpJobConfigQo extends JobConfigQo {

	private String jobType = "http";


	private String httpUrl;


	public String getHttpUrl () {
		return httpUrl;
	}

	public void setHttpUrl (String httpUrl) {
		this.httpUrl = httpUrl;
	}

	public String getIcon () {
		return "ui-icon-http";
	}

	public String getJobType () {
		return jobType;
	}

	public JobQo getJob () {
		HttpJobQo hj = new HttpJobQo ();
		BeanUtils.copyProperties (this, hj, new String[]{"id"});
		return hj;
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
