package com.sunyard.frameworkset.plugin.tsp.manager.service.impl;

import com.sunyard.frameworkset.core.service.BaseServiceImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.JobDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.job.JobQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobService;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mhy on 2017/1/3.
 */
@Service
public class JobServiceImpl extends BaseServiceImpl<Job, String, JobQo> implements JobService {

	@Autowired
	JobDao jobDao;

	@Override
	public List<Job> getJob(String planInstId) {
		return jobDao.getJob(planInstId);
	}

	@Override
	public Integer getAccountGroupByPid (String planId) {
		return jobDao.getAccountByPid(planId);
	}

	@Override
	public List<Job> findByParentJobId (String parentJobId) {
		return jobDao.findByParentJobId(parentJobId);
	}

	@Override
	public Job findByParentJobIdAndPrevJobIdIsNull (String parentJobId) {
		return jobDao.findByParentJobIdAndPrevJobIdIsNull(parentJobId);
	}

	@Override
	public Job findByParentJobIdPlanIdAndPrevJobIdIsNull (String parentJobId, String planId) {
		return jobDao.findByParentJobIdPlanIdAndPrevJobIdIsNull(parentJobId, planId);
	}

	@Override
	public Job findByPlan (Plan plan) {
		return jobDao.findByPlan(plan);
	}

	@Override
	public List<Map<String, Object>> findByPlanInstIdParentJobIdAndStatus (String planInstId, String parentJobId, String status) {
		return jobDao.findByPlanInstIdParentJobIdAndStatus(planInstId, parentJobId, status);
	}

	@Override
	public void releaseJob (Plan plan, List<JobConfig> list, String createUser, String createUserCode) {
		List<Job> lists = new ArrayList<Job> ();
		Map<String, String> map = new HashMap<String, String> ();
		for (JobConfig jobConfig : list) {
			Job job = jobConfig.getJob ();
			job.setPlan (plan);
			job.setCreateTime (DateUtil.getCurrDate ("yyyy-MM-dd HH:mm:ss"));
			job.setCreateUser (createUser);
			job.setCreateUserCode (createUserCode);
			job.setJobConfig(jobConfig);
			this.create (job);
			lists.add (job);
			map.put (jobConfig.getId (), job.getId ());
		}
		for (Job job1 : lists) {
			String nextJobConfigId = job1.getNextJobId ();
			String preJobConfigId = job1.getPrevJobId ();
			String parentJobConfig = job1.getParentJobId ();
			if (nextJobConfigId == null) {
				job1.setNextJobId (null);
			} else {
				job1.setNextJobId (map.get (nextJobConfigId));
			}
			if (preJobConfigId == null) {
				job1.setPrevJobId (null);
			} else {
				job1.setPrevJobId (map.get (preJobConfigId));
			}
			if ("root".equals (parentJobConfig)) {
				job1.setParentJobId ("root");
			} else {
				job1.setParentJobId (map.get (parentJobConfig));
			}
			this.update (job1);
		}
	}

	@Override
	public List findbyConfigId (String configId) {
		return jobDao.findbyConfigId (configId);
	}

}
