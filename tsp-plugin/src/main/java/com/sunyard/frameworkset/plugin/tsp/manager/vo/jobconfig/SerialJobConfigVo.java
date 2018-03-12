package com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.SerialJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.SerialJobVo;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SerialJobConfigVo extends JobConfigVo {

	public static final String JOBNAME = "串行";

	private String jobType = "serial";

	public String getIcon () {
		return "ui-icon-serial";
	}


	public String getJobType () {
		return jobType;
	}

	@Override
	public JobVo getJob () {
		SerialJobVo pj = new SerialJobVo ();
		BeanUtils.copyProperties (this, pj, new String[]{"id"});
		return pj;
	}

	@Override
	public List<Map<String, Object>> getSubInfoResult () throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>> ();
		return result;
	}


	@Override
	public JobConfig getJobConfigByElement (Element element) {
		SerialJobConfig serialJobConfig = new SerialJobConfig ();
		String name = element.attribute ("name").getValue ();
		String createTime = DateUtil.getCurrDate ("yyyy-MM-dd HH:mm:ss");
		serialJobConfig.setName (name);
		serialJobConfig.setCreateTime (createTime);
		return serialJobConfig;
	}
}
