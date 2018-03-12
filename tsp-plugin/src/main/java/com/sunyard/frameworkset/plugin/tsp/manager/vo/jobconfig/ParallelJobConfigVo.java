package com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.ParallelJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.ParallelJobVo;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParallelJobConfigVo extends JobConfigVo {

	public static final String JOBNAME = "并行";

	private String jobType = "parallel";

	public String getIcon () {
		return "ui-icon-parallel";
	}

	public String getJobType () {
		return jobType;
	}

	@Override
	public JobVo getJob () {
		ParallelJobVo pj = new ParallelJobVo ();
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
		ParallelJobConfig parallelJobConfig = new ParallelJobConfig ();
		String name = element.attribute ("name").getValue ();
		String createTime = DateUtil.getCurrDate ("yyyy-MM-dd HH:mm:ss");
		parallelJobConfig.setName (name);
		parallelJobConfig.setCreateTime (createTime);
		return parallelJobConfig;
	}
}
