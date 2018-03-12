package com.sunyard.frameworkset.plugin.tsp.manager.service;

import com.sunyard.frameworkset.core.service.BaseService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunningJob;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.RunningJobQo;

import java.util.List;

/**
 * Created by mhy on 2016/12/30.
 */
public interface RunningJobService extends BaseService<RunningJob,String,RunningJobQo>{
    List<RunningJob> getAllByPlanInstIdAndStatus(String planInstId,String status);

    /**
     * 根据callPlanInstId和planInstId 查找
     * @param callPlanInstId
     * @param planInstId
     * @return
     */
    RunningJob findByCallPlanInstIdAndPlanInstId(String callPlanInstId,String planInstId);


    /**
     * 根据planInstId和jobId查询
     * @param planInstId
     * @param jobId
     * @return
     */
    RunningJob findByPlanInstIdAndJobId(String planInstId,String jobId);

    /**
     * 查找状态为完成的Jobs
     * @return
     */
    List<RunningJob> findCompleteJobs();

    RunningJob findByPlanInstIdAndJobIdAndSatus(String planInstId,String jobId);

    int updateRunStatus(RunningJob job);
}
