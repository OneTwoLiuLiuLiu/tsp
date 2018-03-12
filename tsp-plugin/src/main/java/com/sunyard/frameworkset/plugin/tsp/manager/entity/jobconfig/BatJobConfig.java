package com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.BatJob;
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
@DiscriminatorValue("bat")
public class BatJobConfig extends JobConfig {

	@Transient
	private String jobType = "bat";
	@Column
	private String filePath;

	static {
		register ("bat", BatJobConfig.class);
	}

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
	public Job getJob () {
		BatJob bj = new BatJob ();
		BeanUtils.copyProperties (this, bj, new String[]{"id"});
		return bj;
	}


	@Override
	public JobConfig getJobConfigByElement (Element element) throws Exception {
		BatJobConfig jobConfig = new BatJobConfig ();

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
