package com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.ShellJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.ShellJobVo;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShellJobConfigVo extends JobConfigVo {

	private String jobType = "shell";

	@JobDesc(desc = "文件路径", isNullAble = false)
	private String filePath;

	@JobDesc(desc = "正确的返回值", isNullAble = false)
	private String returnValues;


	public String getReturnValues () {
		return returnValues;
	}

	public void setReturnValues (String returnValues) {
		this.returnValues = returnValues;
	}

	public String getFilePath () {
		return filePath;
	}

	public void setFilePath (String filePath) {
		this.filePath = filePath;
	}


	public String getIcon () {
		return "ui-icon-shell";
	}

	public String getJobType () {
		return jobType;
	}


	@Override
	public String toString () {
		return "ShellJobConfig [filePath=" + filePath + "]";
	}

	@Override
	public JobVo getJob () {
		ShellJobVo pj = new ShellJobVo ();
		BeanUtils.copyProperties (this, pj, new String[]{"id"});
		return pj;
	}

	public List<Map<String, Object>> getInfoReuslt () throws Exception {
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
		result.addAll (super.getInfoReuslt ());
		return result;
	}

	@Override
	public List<Map<String, Object>> getSubInfoResult () throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>> ();
		return result;
	}

	@Override
	public JobConfig getJobConfigByElement (Element element) throws Exception {
		ShellJobConfig jobConfig;

		//公有的  8
		String type = element.attribute ("type").getValue ();
		jobConfig = (ShellJobConfig) ElementToJobConfig.getJobConfigByElement (element, type);

		//shell文件独特的属性
		String filePath = element.attribute ("filePath").getValue ();
		String returnValues = element.attribute ("returnValues").getValue ();

		jobConfig.setFilePath (filePath);
		jobConfig.setReturnValues (returnValues);

		//验证参数是否合适
		JobPropertyIsNullAble.jobPropertyIsNullAble (jobConfig, type);
		return jobConfig;
	}
}
