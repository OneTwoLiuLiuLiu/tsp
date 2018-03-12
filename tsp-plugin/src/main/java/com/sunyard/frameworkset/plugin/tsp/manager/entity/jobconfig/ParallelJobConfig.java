package com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.ParallelJob;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;


@Entity
@DiscriminatorValue("parallel")
public class ParallelJobConfig extends JobConfig {

	public static final String JOBNAME = "并行";

	static {
		register ("parallel", ParallelJobConfig.class);
	}

	@Transient
	private String jobType = "parallel";

	public String getIcon () {
		return "ui-icon-parallel";
	}

	public String getJobType () {
		return jobType;
	}

	@Override
	public Job getJob () {
		ParallelJob pj = new ParallelJob ();
		BeanUtils.copyProperties (this, pj, new String[]{"id"});
		return pj;
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
