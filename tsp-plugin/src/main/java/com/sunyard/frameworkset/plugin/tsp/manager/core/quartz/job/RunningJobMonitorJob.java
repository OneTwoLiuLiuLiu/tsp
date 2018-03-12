package com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.job;

import com.sunyard.frameworkset.core.InstanceFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.service.JobMonitorService;
import org.quartz.*;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class RunningJobMonitorJob implements Job{
	
	public static final String JOBNAME="RunningJobMonitorJob";
	
	private JobMonitorService jobMonitorService;
	
	public RunningJobMonitorJob() {
		this.jobMonitorService= InstanceFactory.getInstance(JobMonitorService.class);
	}

	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		jobMonitorService.runningJobMonitor();
	}

}
