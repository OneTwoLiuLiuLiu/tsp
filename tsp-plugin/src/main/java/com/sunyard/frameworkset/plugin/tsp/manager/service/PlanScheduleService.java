package com.sunyard.frameworkset.plugin.tsp.manager.service;


import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunningJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;

import java.util.List;
import java.util.Map;


/**
 * 计划调度服务
 * @author Administrator
 *
 */
public interface PlanScheduleService {

	/**
	 * 根据上一个作业的Id获取下一级的所有子作业
	 * @param nextjobId
	 * @return
	 */
	public List<Job> getNextJobs(String nextjobId) throws TaskSchedulingPlatformException;
	
	
	/**
	 * 判断当前作业的前置条件是否已经完成
	 * @param jobId
	 * @return
	 */
	public boolean isRunAble(String jobId, String planInstId) throws TaskSchedulingPlatformException;

	/**
	 * 启动计划
	 * @param planConfigId
	 * @param parentInstId 父计划实例Id
	 * @param batchNo 运行的批次号,一般为索要运行的业务日期时间
	 * @param runPlanId 手工运行记录Id
	 * @return 计划实例的Id
	 */
	public String startPlan(String planConfigId, String parentInstId, String batchNo, String runPlanId) throws TaskSchedulingPlatformException;


    String startWaitPlan(Plan plan);

	void excutePlan(String planConfigId,String batchNo);



	 /** 启动计划
	 * @param planConfigId
	 * @param parentInstId 父计划实例Id
	 * @param batchNo 运行的批次号,一般为索要运行的业务日期时间
	 * @param runPlanId 手工运行记录Id
	 * @return 计划实例的Id
	 */
	public void startFdapPlan (String planConfigId, String parentInstId, String batchNo, String runPlanId) throws TaskSchedulingPlatformException;
	
	/**
	 * 用程序调用开启计划
	 * @param planConfigId
	 * @param batchNo
	 * @param params
	 * @return
	 */
	public String startPlanByApp(String planConfigId, String batchNo, Map<String, String> params);
	
	
	/**
	 * 从quartz中移除一个调度计划
	 * @param planConfigId
	 * @return
	 */
	public boolean removePlanConfigFromQuartz(String planConfigId) throws TaskSchedulingPlatformException;


	/**
	 * 向quartz中部署一个调度计划
	 * @param planConfigId
	 * @return
	 */
	public boolean deployPlanConfigToQuartz(String planConfigId) throws TaskSchedulingPlatformException;	/**


	/** 向quartz中部署一个fdap调度计划
	 * @param planConfigId
	 * @return
	 */
	public boolean deployFdapPlanConfigToQuartz(String planConfigId) throws TaskSchedulingPlatformException;
	
	/**
	 * 更新调度周期
	 * @param plan
	 * @return
	 * @throws TaskSchedulingPlatformException
	 */
	public boolean updatePlanPeriod(Plan plan)throws TaskSchedulingPlatformException;
	
	/**
	 * 停止计划
	 * @param planConfigId
	 * @param isStopInst 是否停止正在运行中的实例,true表示停止正在运行中的计划实例,flase表示不停止正在运行中的计划实例
	 * @return
	 */
	public boolean stopPlan(String planConfigId, Boolean isStopInst) throws TaskSchedulingPlatformException;
	
	/**
	 * 暂停计划
	 * @param planId
	 * @return
	 */
	public boolean suspendPlanInst(String planId) throws TaskSchedulingPlatformException;
	
	/**
	 * 结束计划
	 * @param planId
	 * @return
	 */
	public boolean stopPlanInst(String planId) throws TaskSchedulingPlatformException;
	
	/**
	 * 完成Job之后
	 * @param planInstId
	 * @param jobId
	 * @throws TaskSchedulingPlatformException
	 */
	public void afterCompleteJob(String planInstId, String jobId) throws TaskSchedulingPlatformException;
	
	/**
	 * 完成Job
	 * @param runningJob
	 */
	public void completeJob(RunningJob runningJob);
}
