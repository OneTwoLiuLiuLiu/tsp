package com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.service.impl;

import com.sunyard.frameworkset.core.service.BaseServiceImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.JobPoolManager;
import com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.service.JobMonitorService;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.JobMonitorDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.*;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.*;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserAndOrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2016/12/29 0029 上午 10:33
 * version $Id: JpaJobServerConfigServiceImpl.java, v 0.1 Exp $
 */
@Service
@Transactional
public class JobMonitorServiceImpl extends BaseServiceImpl<JobMonitor,String,JobMonitorQo> implements JobMonitorService {
    private Logger logger = LoggerFactory
            .getLogger(JobMonitorServiceImpl.class);
    @Autowired
    private JobMonitorDao jobMonitorDao;


    @Autowired
    private WaittingJobService waittingJobService;

    @Autowired
    private PlanScheduleService planScheduleService;

    @Autowired
    private JobPoolService jobPoolService;

    @Autowired
    private JobPoolManager jobPoolManager;

    @Autowired
    private RunningJobService runningJobService;
    @Autowired
    private UserAndOrgService userAndOrgService;
    @Autowired
    private ReRunningJobService reRunningJobService;



    @Override
    public void jobPoolMonitor() {
        logger.info("==================开始扫描jobpool表=======================");
        //List<JobPool> jobpools = (List<JobPool>) this.jpaDaoImpl.findByHql("from JobPool where status=?", JobPool.NOTAKE);//获取还没有被分配的任务
        List<JobPool> jobpools = jobPoolService.findNotakeJobs();
        for (JobPool jobPool : jobpools) {
            try {
                jobPoolManager.runJob(jobPool);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        }
        logger.info("==================扫描jobpool表结束=======================");

    }

    @Override
    public void runPlanMonitor() {
        return;
    }

    @Override
    public void runningJobMonitor() {
        logger.info("===================扫描runningJob表=============================");
//        String hql = "from RunningJob runningjob where runningjob.status =?";
//        List<RunningJob> runningJobs = (List<RunningJob>) jpaDaoImpl.findByHql(hql, RunningJob.COMPLE);
        List<RunningJob> runningJobs = runningJobService.findCompleteJobs();
        for (RunningJob runningJob : runningJobs) {
            try{
                planScheduleService.completeJob(runningJob);
            }catch (Exception e) {
                logger.error(e.getMessage(),e);
            }

        }
        logger.info("===================扫描runningJob表结束=============================");

    }

    @Override
    public void waittingJobMonitor() {
        logger.info("==================开始扫描waittingjob表=======================");
        List<WaittingJob> waittingJobs = waittingJobService.findAll();
        for(WaittingJob waittingJob : waittingJobs){
            try{
                JobPool jobPool = new JobPool();
                String jobId = waittingJob.getJobId();
                String planInstId = waittingJob.getPlanInstId();
                if (planScheduleService.isRunAble(jobId, planInstId)) {
                    jobPool.setCreateTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
                    jobPool.setJobId(waittingJob.getJobId());
                    jobPool.setPlanInstId(waittingJob.getPlanInstId());
                    jobPoolService.create(jobPool);
                    waittingJobService.delete(waittingJob);
                }
            }catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
        }
        logger.info("==================扫描waittingjob表结束=======================");
        return;
    }

    public boolean reRunJob(JobMonitorQo qo){
        //重新运行，往作业池表中增加一条新数据
        JobPool jobPool = new JobPool ();
        jobPool.setCreateTime (DateUtil.getCurrDate ("yyyy-MM-dd HH:mm:ss"));
        jobPool.setJobId (qo.getJobId());
        jobPool.setPlanInstId (qo.getPlanInstId());
        jobPoolService.create(jobPool);
        RunningJob runningJob=runningJobService.findByPlanInstIdAndJobIdAndSatus(qo.getPlanInstId(),qo.getJobId());
        if (runningJob!= null) {
            ReRunningJob reRunningJob = new ReRunningJob ();
            reRunningJob.setId(runningJob.getId());
            BeanUtils.copyProperties (runningJob, reRunningJob, new String[]{"id"});
            reRunningJob.setCreateTime (DateUtil.dataToStr (new Date(),
                    "yyyy-MM-dd HH:mm:ss"));
            reRunningJob.setCreateUser (userAndOrgService.getCurrentUser ().getName ());
            reRunningJobService.create (reRunningJob);
            runningJobService.delete (runningJob);
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean manualPass(String  id) {
        RunningJob runningJob = runningJobService.findById (id);
        if(runningJob!=null){
            runningJob.setLastModifyTime (DateUtil.dataToStr (new Date (),
                    "yyyy-MM-dd HH:mm:ss"));
            runningJob.setLastModifyIUser (userAndOrgService.getCurrentUser ().getName ());
            runningJob.setStatus (RunningJob.COMPLE);
            int result=runningJobService.updateRunStatus(runningJob);
            if(result!=0){
                return true;
            }
        }
        return false;

    }
}
