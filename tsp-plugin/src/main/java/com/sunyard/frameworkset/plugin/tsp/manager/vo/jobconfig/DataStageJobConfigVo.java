package com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.DataStageJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.DataStageJobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStageJobConfigVo extends JobConfigVo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected String jobType = "datastage";


	@JobDesc(desc = "DS工程名称", isNullAble = false)
	private String dsProjectName;

	private String dsJobName;

	public String getDsProjectName () {
		return dsProjectName;
	}

	public void setDsProjectName (String dsProjectName) {
		this.dsProjectName = dsProjectName;
	}

	public String getDsJobName () {
		return dsJobName;
	}

	public void setDsJobName (String dsJobName) {
		this.dsJobName = dsJobName;
	}

	public String getJobType () {
		return jobType;
	}

	public String getIcon () {
		return "ui-icon-datasage";
	}

	@Override
	public JobVo getJob () {
		DataStageJobVo dsj = new DataStageJobVo ();
		BeanUtils.copyProperties (this, dsj, new String[]{"id"});
		return dsj;
	}

	@Override
	public String toString () {
		return "DataStageJobVo [jobType=" + jobType + ", dsProjectName="
				+ dsProjectName + ", dsJobName=" + dsJobName
				+ ", getRunParams()=" + getRunParams () + "]";
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
		DataStageJobConfig jobConfig;

		//公有的  8
		String type = element.attribute ("type").getValue ();
		jobConfig = (DataStageJobConfig) ElementToJobConfig.getJobConfigByElement (element, type);

		//DatsStage 独特的属性
		String dsProjectName = element.attribute ("dsProjectName").getValue ();
		String dsJobName = element.attribute ("dsJobName").getValue ();

		jobConfig.setDsJobName (dsJobName);
		jobConfig.setDsProjectName (dsProjectName);

		//验证参数是否合适
		JobPropertyIsNullAble.jobPropertyIsNullAble (jobConfig, type);
		return jobConfig;
	}

}
