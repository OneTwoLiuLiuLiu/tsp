package com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.job;

import com.sunyard.frameworkset.core.InstanceFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.service.JobMonitorService;
import org.quartz.*;


/**
 * 监控runPlan表
 * @author whj
 *
 */

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class RunPlanMonitorJob implements Job{

	public static final String JOBNAME="RunPlanMonitorJob";
	private JobMonitorService jobMonitorService;
	
	public RunPlanMonitorJob() {
		this.jobMonitorService = InstanceFactory
				.getInstance(JobMonitorService.class);
	}
	
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		jobMonitorService.runPlanMonitor();
	}

	
	
}
