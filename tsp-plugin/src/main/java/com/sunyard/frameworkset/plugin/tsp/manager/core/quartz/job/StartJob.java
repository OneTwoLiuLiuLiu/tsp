package com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.job;


import com.sunyard.frameworkset.core.InstanceFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanMonitor;
import com.sunyard.frameworkset.plugin.tsp.manager.service.*;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import org.quartz.*;
import org.quartz.Job;



@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class StartJob implements Job{

	public static final String PLANCONIFGID="planConfigId";
	
	private PlanScheduleService planScheduleService;



	public StartJob() {
		this.planScheduleService= InstanceFactory.getInstance(PlanScheduleService.class);
	}
	
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String planConfigId=context.getJobDetail().getKey().getName();
		String batchNo = DateUtil.getCurrDate("yyyy-MM-dd");
		planScheduleService.excutePlan(planConfigId,batchNo);

	}

}
