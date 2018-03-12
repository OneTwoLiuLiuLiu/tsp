package com.sunyard.frameworkset.plugin.tsp.manager.service.impl;

import com.sunyard.frameworkset.core.service.BaseServiceImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.JobPoolDao;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobPoolService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobPool;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobPoolQo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作业池 service
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Service
public class JobPoolServiceImpl extends BaseServiceImpl<JobPool, String, JobPoolQo> implements JobPoolService {

    @Autowired
    private JobPoolDao jobPoolDao;

    @Override
    public List<JobPool> findByPlanInstIdAndJobId(String planInstId, String jobId) {
        return jobPoolDao.findByPlanInstIdAndJobId(planInstId, jobId);
    }

    @Override
    public List<JobPool> findNotakeJobs() {
        return jobPoolDao.finJobsByIfTake(JobPool.NOTAKE);
    }

    @Override
    public void lock(JobPool jobPool) {
        jobPoolDao.lock(jobPool);
    }
}
