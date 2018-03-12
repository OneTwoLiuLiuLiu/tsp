package com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.SerialJob;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;


@Entity
@DiscriminatorValue("serial")
public class SerialJobConfig extends JobConfig {


	static {
		register ("serial", SerialJobConfig.class);
	}

	public static final String JOBNAME = "串行";
	@Transient
	private String jobType = "serial";

	public String getIcon () {
		return "ui-icon-serial";
	}


	public String getJobType () {
		return jobType;
	}

	@Override
	public Job getJob () {
		SerialJob pj = new SerialJob ();
		BeanUtils.copyProperties (this, pj, new String[]{"id"});
		return pj;
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
