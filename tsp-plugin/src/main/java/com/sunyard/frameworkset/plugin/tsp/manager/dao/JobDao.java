package com.sunyard.frameworkset.plugin.tsp.manager.dao;

import com.sunyard.frameworkset.core.dao.BaseDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.job.JobQo;

import java.util.List;
import java.util.Map;

/**
 * Created by mhy on 2017/1/3.
 */
public interface JobDao extends BaseDao<Job,String,JobQo> {

    /*
     根据 planInstId子查询
     */

    List<Job> getJob(String planInstId);
    int getAccountByPid(String pid);


    /**
     * 根据parentJobId查找
     * @param parentJobId
     * @return
     */
    List<Job> findByParentJobId(String parentJobId);


    /**
     * 根据parentJobId查找 并且 PrevJobIdIsNull
     * @param parentJobId
     * @return
     */
    Job findByParentJobIdAndPrevJobIdIsNull(String parentJobId);

    /**
     * 根据parentJobId planId 并且 PrevJobIdIsNull 查找
     * @param parentJobId
     * @param planId
     * @return
     */
    Job findByParentJobIdPlanIdAndPrevJobIdIsNull(String parentJobId,String planId);


    /**
     * 根据plan查找
     * @param plan
     * @return
     */
    Job findByPlan(Plan plan);


    /**
     * 查找
     * @param planInstId
     * @param parentJobId
     * @param status
     * @return
     */
    List<Map<String, Object>> findByPlanInstIdParentJobIdAndStatus(String planInstId,String  parentJobId,String status);

    List findbyConfigId(String configId);
}
