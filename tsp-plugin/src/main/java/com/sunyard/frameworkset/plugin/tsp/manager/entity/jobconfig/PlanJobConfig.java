package com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.PlanJob;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("plan")
public class PlanJobConfig extends JobConfig {

	/**
	 *
	 */
	private static final long serialVersionUID = -1771013225922866099L;

	@Transient
	private String jobType = "plan";

	@Column(name = "callPlanConfigId", length = 50)
	private String callPlanConfigId;

	@Column(name = "callPlanName", length = 50)
	private String callPlanConfigName;

	static {
		register ("plan", PlanJobConfig.class);
	}

	public String getIcon () {
		return "ui-icon-plan";
	}

	public String getJobType () {
		return jobType;
	}

	public String getCallPlanConfigId () {
		return callPlanConfigId;
	}

	public void setCallPlanConfigId (String callPlanConfigId) {
		this.callPlanConfigId = callPlanConfigId;
	}

	public String getCallPlanConfigName () {
		return callPlanConfigName;
	}

	public void setCallPlanConfigName (String callPlanConfigName) {
		this.callPlanConfigName = callPlanConfigName;
	}

	@Override
	public Job getJob () {
		PlanJob pj = new PlanJob ();
		BeanUtils.copyProperties (this, pj, new String[]{"id"});
		return pj;
	}

	@Override
	public JobConfig getJobConfigByElement (Element element) {
		// TODO Auto-generated method stub
		return null;
	}


}
