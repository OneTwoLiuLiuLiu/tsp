package com.sunyard.frameworkset.plugin.tsp.manager.core.service.init.impl;

import com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.QuartzManager;
import com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.job.JobPoolMonitorJob;
import com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.job.RunPlanMonitorJob;
import com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.job.RunningJobMonitorJob;
import com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.job.WaittingJobMonitorJob;
import com.sunyard.frameworkset.plugin.tsp.manager.core.service.init.InitService;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobServerConfigService;
import com.sunyard.frameworkset.plugin.tsp.manager.service.SystemParamsConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
public class InitServiceImpl implements InitService {

	private Logger logger = LoggerFactory.getLogger(InitServiceImpl.class);

	@Autowired(required = false)
	private QuartzManager quartzManager;

	@Autowired(required = false)
	private SystemParamsConfigService systemParamConfigService;

	@Autowired(required = false)
	private JobServerConfigService jobServerConfigService;

	public void init() {
		initQuartzJob();
	}

	/**
	 * 初始化监控线程
	 */
	private void initQuartzJob() {
		 //定时器异常时 启动强制删除以下job
		 /*
		quartzManager.removeJob(WaittingJobMonitorJob.JOBNAME);
		quartzManager.removeJob(JobPoolMonitorJob.JOBNAME);
		quartzManager.removeJob(RunPlanMonitorJob.JOBNAME);
		quartzManager.removeJob(RunningJobMonitorJob.JOBNAME);
		*/
		if (quartzManager.echeckExistsxits(WaittingJobMonitorJob.JOBNAME)) {
			quartzManager.removeJob(WaittingJobMonitorJob.JOBNAME);
		}

		if (quartzManager.echeckExistsxits(JobPoolMonitorJob.JOBNAME)) {
			quartzManager.removeJob(JobPoolMonitorJob.JOBNAME);
		}
		if (quartzManager.echeckExistsxits(RunPlanMonitorJob.JOBNAME)) {
			quartzManager.removeJob(RunPlanMonitorJob.JOBNAME);
		}
		if (quartzManager.echeckExistsxits(RunningJobMonitorJob.JOBNAME)) {
			quartzManager.removeJob(RunningJobMonitorJob.JOBNAME);
		}

		quartzManager.addJob(RunningJobMonitorJob.class,
				RunningJobMonitorJob.JOBNAME, "0/5 * * * * ?",
				new HashMap<String, Object>());

		quartzManager.addJob(JobPoolMonitorJob.class,
				JobPoolMonitorJob.JOBNAME, "0/5 * * * * ?",
				new HashMap<String, Object>());

		quartzManager.addJob(WaittingJobMonitorJob.class,
				WaittingJobMonitorJob.JOBNAME, "0/5 * * * * ?",
				new HashMap<String, Object>());

		quartzManager.addJob(RunPlanMonitorJob.class,
				RunPlanMonitorJob.JOBNAME, "0/5 * * * * ?",
				new HashMap<String, Object>());


	}

}
