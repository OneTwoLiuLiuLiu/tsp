package com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.DataStageJob;
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
@DiscriminatorValue("datastage")
public class DataStageJobConfig extends JobConfig {

	static {
		register ("datastage", DataStageJobConfig.class);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	protected String jobType = "datastage";


	@Column
	private String dsProjectName;

	@Column
	@JobDesc(desc = "DS作业名称", isNullAble = false)
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
		return "ui-icon-datastage";
	}

	@Override
	public Job getJob () {
		DataStageJob dsj = new DataStageJob ();
		BeanUtils.copyProperties (this, dsj, new String[]{"id"});
		return dsj;
	}

	@Override
	public String toString () {
		return "DataStageJob [jobType=" + jobType + ", dsProjectName="
				+ dsProjectName + ", dsJobName=" + dsJobName
				+ ", getRunParams()=" + getRunParams () + "]";
	}

	@Override
	public JobConfig getJobConfigByElement (Element element) throws Exception {
		DataStageJobConfig jobConfig = new DataStageJobConfig ();

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
