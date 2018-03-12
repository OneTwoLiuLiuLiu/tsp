package com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.job;


import com.sunyard.frameworkset.core.InstanceFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.service.JobMonitorService;
import org.quartz.*;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class JobPoolMonitorJob  implements Job{

	public static final String JOBNAME="JobPoolMonitorJob";
	
	private JobMonitorService jobMonitorService;
	
	public JobPoolMonitorJob() {
		this.jobMonitorService= InstanceFactory.getInstance(JobMonitorService.class);
	}

	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		jobMonitorService.jobPoolMonitor();
	}

}
