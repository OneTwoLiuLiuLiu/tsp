package com.sunyard.frameworkset.plugin.tsp.manager.service.impl;

import com.sunyard.frameworkset.core.service.BaseServiceImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.RunningJobDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunningJob;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.RunningJobQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.RunningJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mhy on 2016/12/30.
 */
@Service
public class RunningJobServiceImpl extends BaseServiceImpl<RunningJob,String,RunningJobQo> implements RunningJobService{
    @Autowired
    RunningJobDao runningJobDao;

    @Override
    public List<RunningJob> getAllByPlanInstIdAndStatus(String planInstId, String status) {
        return runningJobDao.getByPlanInstIdAndStatus(planInstId,status);
    }

    @Override
    public RunningJob findByCallPlanInstIdAndPlanInstId(String callPlanInstId, String planInstId) {
        return runningJobDao.findByCallPlanInstIdAndPlanInstId(callPlanInstId,planInstId);
    }

    @Override
    public RunningJob findByPlanInstIdAndJobId(String planInstId, String jobId) {
        return runningJobDao.findByPlanInstIdAndJobId(planInstId,jobId);
    }

    @Override
    public List<RunningJob> findCompleteJobs() {
        return runningJobDao.findJobsByStatus(RunningJob.COMPLE);
    }

    @Override
    public RunningJob findByPlanInstIdAndJobIdAndSatus(String planInstId, String jobId) {
        return runningJobDao.findJobsBylanInstIdAndJobIdAndStatus(planInstId,jobId);
    }

    @Override
    public int updateRunStatus(RunningJob job) {
        return runningJobDao.updateRuningJob(job);
    }
}
