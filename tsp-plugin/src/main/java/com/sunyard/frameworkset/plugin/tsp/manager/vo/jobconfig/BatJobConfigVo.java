package com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.BatJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.BatJobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BatJobConfigVo extends JobConfigVo {

	private String jobType = "bat";

	@JobDesc(desc = "文件路径", isNullAble = false)
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

	@Override
	public JobVo getJob () {
		BatJobVo bj = new BatJobVo ();
		BeanUtils.copyProperties (this, bj, new String[]{"id"});
		return bj;
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
		BatJobConfig jobConfig;

		//公有的  8
		String type = element.attribute ("type").getValue ();
		jobConfig = (BatJobConfig) ElementToJobConfig.getJobConfigByElement (element, type);

		//bat文件独特的属性
		String filePath = element.attribute ("filePath").getValue ();


		jobConfig.setFilePath (filePath);
		//验证参数是否合适
		JobPropertyIsNullAble.jobPropertyIsNullAble (jobConfig, type);
		return jobConfig;
	}

}
