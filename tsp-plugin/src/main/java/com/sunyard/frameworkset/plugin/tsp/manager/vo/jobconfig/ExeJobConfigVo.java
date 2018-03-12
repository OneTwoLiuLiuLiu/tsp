package com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.ExeJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.ExeJobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExeJobConfigVo extends JobConfigVo {

	private static final long serialVersionUID = 1L;

	private String jobType = "exe";

	@JobDesc(desc = "返回值类型", isNullAble = false)
	private String returnValues;

	@JobDesc(desc = "文件路径", isNullAble = false)
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

	public JobVo getJob () {
		ExeJobVo ej = new ExeJobVo ();
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

	@Override
	public JobConfig getJobConfigByElement (Element element) throws Exception {

		ExeJobConfig jobConfig;

		//公有的  8
		String type = element.attribute ("type").getValue ();
		jobConfig = (ExeJobConfig) ElementToJobConfig.getJobConfigByElement (element, type);
		//exe文件独特的属性
		String returnValues = element.attribute ("returnValues").getValue ();
		String filePath = element.attribute ("filePath").getValue ();


		jobConfig.setReturnValues (returnValues);
		jobConfig.setFilePath (filePath);

		//验证参数是否合适
		JobPropertyIsNullAble.jobPropertyIsNullAble (jobConfig, type);
		return jobConfig;
	}
}
