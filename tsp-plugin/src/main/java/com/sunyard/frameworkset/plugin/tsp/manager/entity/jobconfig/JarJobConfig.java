package com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.JarJob;
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
@DiscriminatorValue("jar")
public class JarJobConfig extends JobConfig {

	static {
		register ("jar", JarJobConfig.class);
	}

	@Transient
	private String jobType = "jar";

	@Column(name = "filePath", length = 100)
	private String filePath;

	@Column(name = "initialMemoryValue", length = 10)
	private String initialMemoryValue;

	@Column(name = "maxMemoryValue", length = 10)
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

	public Job getJob () {
		JarJob jj = new JarJob ();
		BeanUtils.copyProperties (this, jj, new String[]{"id"});
		return jj;
	}

	@Override
	public JobConfig getJobConfigByElement (Element element) throws Exception {
		JarJobConfig jobConfig = new JarJobConfig ();

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
