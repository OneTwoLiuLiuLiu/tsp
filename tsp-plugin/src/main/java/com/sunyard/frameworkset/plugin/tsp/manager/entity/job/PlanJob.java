package com.sunyard.frameworkset.plugin.tsp.manager.entity.job;


import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@DiscriminatorValue("plan")
public class PlanJob extends Job {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4381382790210839099L;

	@Transient
	protected String jobType = "plan";

	@Column(name = "callPlanConfigId", length = 50)
	@JobDesc(desc = "调用计划ID")
	private String callPlanConfigId;

	@Column(name = "callPlanName", length = 50)
	@JobDesc(desc = "调用计划名称")
	private String callPlanConfigName;

	public String getJobType() {
		return jobType;
	}

	public String getCallPlanConfigId() {
		return callPlanConfigId;
	}

	public void setCallPlanConfigId(String callPlanConfigId) {
		this.callPlanConfigId = callPlanConfigId;
	}

	public String getCallPlanConfigName() {
		return callPlanConfigName;
	}

	public void setCallPlanConfigName(String callPlanConfigName) {
		this.callPlanConfigName = callPlanConfigName;
	}

	@Override
	public String getIcon() {
		return "ui-icon-plan";
	}

	@Override
	public List<Map<String, Object>> getSubInfoResult() throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Class clazz = this.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Map<String, Object> map = new HashMap<String, Object>();
			JobDesc job = field.getAnnotation(JobDesc.class);
			if (job != null) {
				field.setAccessible(true);
				map.put("text", job.desc());
				map.put("value", field.get(this));
				result.add(map);
			}
		}
		return result;
	}

}
