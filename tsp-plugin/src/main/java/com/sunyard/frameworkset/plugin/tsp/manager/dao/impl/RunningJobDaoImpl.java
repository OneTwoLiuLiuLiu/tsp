package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.RunningJobDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunningJob;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.JobMonitorEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.RunningJobQo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mhy on 2016/12/30.
 */
@Repository
public class RunningJobDaoImpl extends JpaBaseDaoImpl<RunningJob,String,RunningJobQo> implements RunningJobDao{
    @Override
    public List<RunningJob> getByPlanInstIdAndStatus(String planInstId, String status) {
        String hql = "from RunningJob where planInstId=? and status=?";
        return this.findByQueryString(hql,planInstId,status);
    }

    @Override
    public RunningJob findByCallPlanInstIdAndPlanInstId(String callPlanInstId, String planInstId) {
        return this.findBySingle("from RunningJob where callPlanInstId=? and planInstId=?",callPlanInstId,planInstId);
    }

    @Override
    public RunningJob findByPlanInstIdAndJobId(String planInstId, String jobId) {
        return this.findBySingle("from RunningJob where jobId=? and planInstId=?",planInstId,jobId);
    }

    @Override
    public List<RunningJob> findJobsByStatus(String status) {
        return this.findByQueryString("from RunningJob where status =?",status);
    }

    @Override
    public RunningJob findJobsBylanInstIdAndJobIdAndStatus(String planInstId, String jobId) {
        return  this.findBySingle("from RunningJob where planInstId=? and jobId=? and status =?",planInstId,jobId, JobMonitorEnum.RUNERRER);

    }

    @Override
    public int updateRuningJob(RunningJob job) {
        int result=this.executeUpdate("update RunningJob as obj set obj.lastModifyTime = ? ,obj.status=? , obj.lastModifyIUser=? where obj.id = ?",job.getLastModifyTime() , job.getStatus(),job.getLastModifyIUser(),job.getId());
        return result;
    }
}
