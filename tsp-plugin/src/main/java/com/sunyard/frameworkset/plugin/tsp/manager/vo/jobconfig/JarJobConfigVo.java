package com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JarJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JarJobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JarJobConfigVo extends JobConfigVo {

	private String jobType = "jar";

	@JobDesc(desc = "文件路径", isNullAble = false)
	private String filePath;

	@JobDesc(desc = "虚拟机内存初始值", isNullAble = false)
	private String initialMemoryValue;

	@JobDesc(desc = "虚拟机内存最大值", isNullAble = false)
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

	public JobVo getJob () {
		JarJobVo jj = new JarJobVo ();
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

	@Override
	public JobConfig getJobConfigByElement (Element element) throws Exception {
		JarJobConfig jobConfig;

		//公有的  8
		String type = element.attribute ("type").getValue ();
		jobConfig = (JarJobConfig) ElementToJobConfig.getJobConfigByElement (element, type);
		//jar文件独特的  3
		String filePath = element.attribute ("filePath").getValue ();
		String initialMemoryValue = element.attribute ("initialMemoryValue").getValue ();
		String maxMemoryValue = element.attribute ("maxMemoryValue").getValue ();


		jobConfig.setFilePath (filePath);
		jobConfig.setInitialMemoryValue (initialMemoryValue);
		jobConfig.setMaxMemoryValue (maxMemoryValue);
		//验证参数是否合适
		JobPropertyIsNullAble.jobPropertyIsNullAble (jobConfig, type);
		return jobConfig;
	}

}
