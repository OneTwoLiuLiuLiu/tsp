package com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.ShellJob;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;


@Entity
@DiscriminatorValue("shell")
public class ShellJobConfig extends JobConfig {


	static {
		register ("shell", ShellJobConfig.class);
	}

	@Transient
	private String jobType = "shell";
	@Column
	private String filePath;

	@Column
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
	public Job getJob () {
		ShellJob pj = new ShellJob ();
		BeanUtils.copyProperties (this, pj, new String[]{"id"});
		return pj;
	}


	@Override
	public JobConfig getJobConfigByElement (Element element) throws Exception {
		ShellJobConfig jobConfig = new ShellJobConfig ();

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
