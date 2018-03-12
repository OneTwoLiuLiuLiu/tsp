package com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.job;

import com.sunyard.frameworkset.core.InstanceFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.service.PlanScheduleService;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import org.quartz.*;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class FdapStartJob implements Job{

	public static final String PLANCONIFGID="planConfigId";

	private PlanScheduleService planScheduleService;

	public FdapStartJob () {
		this.planScheduleService= InstanceFactory.getInstance(PlanScheduleService.class);
	}
	
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String planConfigId=context.getJobDetail().getKey().getName();
		String batchNo = DateUtil.getCurrDate("yyyy-MM-dd");
		planScheduleService.startFdapPlan(planConfigId,null,batchNo,null);
	}

}
