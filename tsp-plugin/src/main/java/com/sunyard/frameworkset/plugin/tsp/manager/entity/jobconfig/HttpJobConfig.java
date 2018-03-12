package com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.HttpJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("http")
public class HttpJobConfig extends JobConfig {

	static {
		register ("http", HttpJobConfig.class);
	}

	@Transient
	private String jobType = "http";


	@Column(name = "httpUrl", length = 120)
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

	public Job getJob () {
		HttpJob hj = new HttpJob ();
		BeanUtils.copyProperties (this, hj, new String[]{"id"});
		return hj;
	}

	@Override
	public JobConfig getJobConfigByElement (Element element) throws Exception {
		HttpJobConfig jobConfig = new HttpJobConfig ();

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
