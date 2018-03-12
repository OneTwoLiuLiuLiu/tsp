package com.sunyard.frameworkset.plugin.tsp.manager.service;


import com.sunyard.frameworkset.core.service.BaseService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanInstanceQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanMonitorVo;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserVo;
import com.sunyard.frameworkset.util.pages.PageList;

import java.util.List;

public interface PlanInstanceService extends BaseService<PlanInstance,String,PlanInstanceQo>{

	List<PlanInstance> queryPlanInstance(String planName, String startTimeOne,
			String endTimeOne, String startTimeTwo, String endTimeTwo,
			String startBatchNo, String endBatchNo, String status);
	PageList<PlanMonitorVo> queryPlanMonitor(PlanMonitorQo planMonitorQo);
	int endPlanInstance(String planInstId,UserVo userVo);
	int runPlanInstance(String planInstId);
	int pausePlan(String planInstId);
	PlanInstance getPlanInstMinTimeAndPlanId( String planId);
	int updateStatusAndTime(PlanInstance planInstance);
}
