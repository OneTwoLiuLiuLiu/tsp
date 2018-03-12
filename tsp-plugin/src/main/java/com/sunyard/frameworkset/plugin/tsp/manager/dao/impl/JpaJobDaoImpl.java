package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.JobDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.HistoryJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunningJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.job.JobQo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by mhy on 2017/1/3.
 */
@Repository
public class JpaJobDaoImpl extends JpaBaseDaoImpl<Job,String,JobQo> implements JobDao {
    @Override
    public List<Job> getJob(String planInstId) {
        return findByQueryString("select id from TSP_JOB where  type='task' and TSP_JOB.PARENT_JOB_ID='root'   and type <> 'turnover' "
                        + "and plan_id=(select plan_id from tsp_Plan_Instance where id=?) "
                        + "and id  not in (select job_id from TSP_HISTORY_JOB where   plan_inst_id=? and STATUS=? "
                        + "UNION select job_id from tsp_running_job where  plan_Inst_Id=? and STATUS=?)  ",
                planInstId, planInstId, HistoryJob.COMPLE, planInstId,
                RunningJob.COMPLE);
    }

    @Override
    public int getAccountByPid(String pid) {
        String hql = "select count(Job) from Job where Job.Plan.id = ?";
        return this.count(hql, pid);
    }

    @Override
    public List<Job> findByParentJobId(String parentJobId) {
        return this.findByQueryString("from Job where  parentJobId=?", parentJobId);
    }

    @Override
    public Job findByParentJobIdAndPrevJobIdIsNull(String parentJobId) {
        return this.findBySingle("from Job where prevJobId is null and parentJobId=?", parentJobId);
    }

    @Override
    public Job findByParentJobIdPlanIdAndPrevJobIdIsNull(String parentJobId, String planId) {
        return this.findBySingle("from Job where parentJobId=? and plan.id=? and prevJobId is null", parentJobId,planId);
    }

    @Override
    public Job findByPlan(Plan plan) {
        return this.findBySingle("from Job where plan=?",plan);
    }

    @Override
    public List<Map<String, Object>> findByPlanInstIdParentJobIdAndStatus(String planInstId, String parentJobId, String status) {
        List list=this.getEntityManager().createNativeQuery("select TSP_JOB.id from TSP_JOB left join TSP_HISTORY_JOB on TSP_JOB.ID = TSP_HISTORY_JOB.JOB_ID  and TSP_HISTORY_JOB.PLAN_INST_ID=? where TSP_JOB.PARENT_JOB_ID=? and (TSP_HISTORY_JOB.STATUS<>? or TSP_HISTORY_JOB.STATUS is NULL) ")
                .setParameter(0,planInstId)
                .setParameter(1,parentJobId)
                .setParameter(2,status).getResultList();

        return list;
    }

    @Override
    public List findbyConfigId(String configId) {
        List list=this.getEntityManager().createNativeQuery("select job.id from TSP_JOB job where job.JOB_CONFIG_ID = ?")
                .setParameter(1,configId).getResultList();
        return list;
    }

}
