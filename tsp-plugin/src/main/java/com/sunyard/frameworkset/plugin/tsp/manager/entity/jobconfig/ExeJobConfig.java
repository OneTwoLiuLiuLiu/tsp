package com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.ExeJob;
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
@DiscriminatorValue("exe")
public class ExeJobConfig extends JobConfig {
	static {
		register ("exe", ExeJobConfig.class);
	}

	private static final long serialVersionUID = 1L;

	@Transient
	private String jobType = "exe";

	@Column(name = "returnValues", length = 20)
	private String returnValues;

	@Column(name = "filePath", length = 100)
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

	public Job getJob () {
		ExeJob ej = new ExeJob ();
		BeanUtils.copyProperties (this, ej, new String[]{"id"});
		return ej;
	}

	@Override
	public JobConfig getJobConfigByElement (Element element) throws Exception {

		ExeJobConfig jobConfig = new ExeJobConfig ();

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
