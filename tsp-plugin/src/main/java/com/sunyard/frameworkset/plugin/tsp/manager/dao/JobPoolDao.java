package com.sunyard.frameworkset.plugin.tsp.manager.dao;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobPool;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobPoolQo;
import com.sunyard.frameworkset.core.dao.BaseDao;

import java.util.List;

/**
 * 作业池 dao 接口
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
public interface JobPoolDao extends BaseDao<JobPool, String, JobPoolQo> {
    public List<JobPool> findByPlanInstIdAndJobId(String planInstId, String jobId);
    /**
     * 根据是否分配查找jobPool
     */
    List<JobPool> finJobsByIfTake(String ifTake);

    /**
     * 锁jobPool
     */
    void lock(JobPool jobPool);
}
