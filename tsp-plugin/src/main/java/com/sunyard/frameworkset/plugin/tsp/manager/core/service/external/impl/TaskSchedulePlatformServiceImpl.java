package com.sunyard.frameworkset.plugin.tsp.manager.core.service.external.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.core.service.external.TaskSchedulePlatformService;
import com.sunyard.frameworkset.plugin.tsp.manager.service.PlanConfigService;
import com.sunyard.frameworkset.plugin.tsp.manager.service.PlanScheduleService;
import com.sunyard.frameworkset.plugin.tsp.manager.service.PlanMonitorService;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.Plan;
import org.springframework.beans.factory.annotation.Autowired;



public class TaskSchedulePlatformServiceImpl implements TaskSchedulePlatformService {
	
	@Autowired(required=false)
	private PlanScheduleService planScheduleService;
	
	@Autowired(required=false)
	private PlanConfigService planConfigService;
	
	@Autowired(required=false)
	private PlanMonitorService planStatusService;

	public String StartPlan(String planId, String batchNo,Map<String, String> params) {
		return planScheduleService.startPlanByApp(planId, batchNo, params);
	}

	public List<Plan> getPlans() {
		List<PlanConfig> pcs=planConfigService.findAll();
		List<Plan> plans = new ArrayList<Plan>();
		Plan plan = null;
		for (PlanConfig planConfig : pcs) {
			plan = new Plan();
			plan.setPlanId(planConfig.getId());
			plan.setPlanName(planConfig.getName());
			plans.add(plan);
		}
		return plans;
	}

	public Plan getPlanById(String id) {
		PlanConfig planconfig= this.planConfigService.findById(id);
		if(planconfig == null){
			return null;
		}
		Plan plan  = new Plan();
		plan.setPlanId(planconfig.getId());
		plan.setPlanName(planconfig.getName());
		return plan;
	}

	public String getPlanInstStatus(String planInstId) {
		return planStatusService.getPlanInstStatus(planInstId);
	}

}
