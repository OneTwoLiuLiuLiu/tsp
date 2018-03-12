package com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.HttpJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.HttpJobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpJobConfigVo extends JobConfigVo {

	private String jobType = "http";


	@JobDesc(desc = "http的Url", isNullAble = false)
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

	public JobVo getJob () {
		HttpJobVo hj = new HttpJobVo ();
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

	@Override
	public JobConfig getJobConfigByElement (Element element) throws Exception {
		HttpJobConfig jobConfig;

		//公有的  8
		String type = element.attribute ("type").getValue ();
		jobConfig = (HttpJobConfig) ElementToJobConfig.getJobConfigByElement (element, type);

		//http独特的属性
		String httpUrl = element.attribute ("httpUrl").getValue ();
		jobConfig.setHttpUrl (httpUrl);

		//验证参数是否合适
		JobPropertyIsNullAble.jobPropertyIsNullAble (jobConfig, type);
		return jobConfig;
	}
}
