package com.sunyard.frameworkset.plugin.tsp.manager.service;

import com.sunyard.frameworkset.core.service.BaseService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.job.JobQo;

import java.util.List;
import java.util.Map;

/**
 * Created by mhy on 2017/1/3.
 */
public interface JobService extends BaseService<Job, String, JobQo> {
	/**
	 * 判断指定计划下面除了翻牌任务之外有没有完成
	 */
	List<Job> getJob(String planInstId);
	Integer getAccountGroupByPid (String planId);

	/**
	 * 根据parentJobId查找
	 *
	 * @param parentJobId
	 * @return
	 */
	List<Job> findByParentJobId (String parentJobId);

	/**
	 * 根据parentJobId 并且 PrevJobIdIsNull 查找
	 *
	 * @param parentJobId
	 * @return
	 */
	Job findByParentJobIdAndPrevJobIdIsNull (String parentJobId);


	/**
	 * 根据parentJobId planId 并且 PrevJobIdIsNull 查找
	 *
	 * @param parentJobId
	 * @param planId
	 * @return
	 */
	Job findByParentJobIdPlanIdAndPrevJobIdIsNull (String parentJobId, String planId);


	/**
	 * 根据plan查找
	 *
	 * @param plan
	 * @return
	 */
	Job findByPlan (Plan plan);

	/**
	 * 查找
	 *
	 * @param planInstId
	 * @param parentJobId
	 * @param status
	 * @return
	 */
	List<Map<String, Object>> findByPlanInstIdParentJobIdAndStatus (String planInstId, String parentJobId, String status);

	void releaseJob (Plan plan, List<JobConfig> list, String createUser, String createUserCode);

	List findbyConfigId (String configId);
}
