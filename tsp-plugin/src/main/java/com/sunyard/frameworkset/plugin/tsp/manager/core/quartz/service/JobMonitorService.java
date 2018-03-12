package com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.service;

import com.sunyard.frameworkset.core.service.BaseService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobMonitor;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobMonitorQo;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2016/12/29 0029 上午 10:33
 * version $Id: JpaJobServerConfigService.java, v 0.1 Exp $
 */
public interface JobMonitorService extends BaseService<JobMonitor,String,JobMonitorQo> {

    /**
     * 监控作业池表
     */
     void jobPoolMonitor();

    /**
     * 监控runplan表
     */
     void runPlanMonitor();

    /**
     * 监控runningJob
     */
     void runningJobMonitor();

    /**
     * 监控作业等待表
     */
     void waittingJobMonitor();

    /**
     * 重新运行作业
     * @param qo
     * @return
     */
     boolean reRunJob(JobMonitorQo qo);

    /**
     * 强制通过作业
     */
    boolean manualPass(String id);
}
