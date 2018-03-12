package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.JobPoolDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobPool;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobPoolQo;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

/**
 * 作业池 jdbc实现类
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Repository
public class JpaJobPoolDaoImpl  extends JpaBaseDaoImpl<JobPool,String,JobPoolQo> implements JobPoolDao{
    @Override
    public List<JobPool> findByPlanInstIdAndJobId(String planInstId, String jobId) {
        return  this.findByQueryString("from JobPool where planInstId=? and jobId=?",planInstId,jobId);
    }

    @Override
    public List<JobPool> finJobsByIfTake(String ifTake) {
        return this.findByQueryString("from JobPool where status=?",ifTake);
    }

    @Override
    public void lock(JobPool jobPool) {
        this.getEntityManager().lock(jobPool, LockModeType.PESSIMISTIC_READ);
    }
}
