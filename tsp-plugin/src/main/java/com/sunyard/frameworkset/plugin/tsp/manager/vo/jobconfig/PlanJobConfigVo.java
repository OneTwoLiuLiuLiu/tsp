package com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.PlanJobVo;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanJobConfigVo extends JobConfigVo {

	/**
	 *
	 */
	private static final long serialVersionUID = -1771013225922866099L;

	private String jobType = "plan";

	@JobDesc(desc = "调用计划ID")
	private String callPlanConfigId;

	@JobDesc(desc = "调用计划名称")
	private String callPlanConfigName;

	static {
		register ("plan", PlanJobConfigVo.class);
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
	public JobVo getJob () {
		PlanJobVo pj = new PlanJobVo ();
		BeanUtils.copyProperties (this, pj, new String[]{"id"});
		return pj;
	}

	@Override
	public List<Map<String, Object>> getSubInfoResult () throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>> ();
		Class clazz = this.getClass ();
		Field[] fields = clazz.getDeclaredFields ();
		for (Field field : fields) {
			Map<String, Object> map = new HashMap<String, Object> ();
			JobDesc job = field.getAnnotation (JobDesc.class);
			if (job != null) {
				field.setAccessible (true);
				map.put ("text", job.desc ());
				map.put ("value", field.get (this));
				result.add (map);
			}
		}
		return result;
	}

	@Override
	public JobConfig getJobConfigByElement (Element element) {
		// TODO Auto-generated method stub
		return null;
	}


}
