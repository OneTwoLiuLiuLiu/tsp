package com.sunyard.frameworkset.plugin.tsp.manager.dao;

import com.sunyard.frameworkset.core.dao.BaseDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunningJob;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.RunningJobQo;

import java.util.List;

/**
 * Created by mhy on 2016/12/30.
 */
public interface RunningJobDao extends BaseDao<RunningJob,String,RunningJobQo>{
    List<RunningJob> getByPlanInstIdAndStatus(String planInstId,String status);

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

    List<RunningJob> findJobsByStatus(String status);

    RunningJob findJobsBylanInstIdAndJobIdAndStatus(String planInstId,String jobId);
    int updateRuningJob(RunningJob job);
}
