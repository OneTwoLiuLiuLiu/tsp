package com.sunyard.frameworkset.plugin.tsp.manager.service;

import com.sunyard.frameworkset.core.service.BaseService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobPool;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobPoolQo;

import java.util.List;

/**
 * 作业池 service 接口
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
public interface JobPoolService extends BaseService<JobPool, String, JobPoolQo> {
    /**
     * 根据PlanInstId和JobId查询 JobPool
     * @param planInstId
     * @param jobId
     * @return
     */
    List<JobPool> findByPlanInstIdAndJobId(String planInstId,String jobId);

    /**
     * 查找未分配的jobpool
     * @return
     */
    List<JobPool> findNotakeJobs();

    /**
     * 锁lockPool
     */
    void lock(JobPool jobPool);
}
