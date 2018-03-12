package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.dto.GenericObjectTranslator;
import com.sunyard.frameworkset.core.dto.ObjectTranslator;
import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.JobPoolManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobPool;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunningJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.ParallelJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.PlanJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.SerialJob;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobPoolQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.*;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.JobPoolVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 作业池 manager
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Component
@Transactional
public class JobPoolManagerImpl extends BaseManagerImpl<JobPoolVo, JobPool, String, JobPoolQo> implements JobPoolManager {
    private static final Logger LOGGER = LoggerFactory.getLogger ( JobPoolManagerImpl.class );
    protected ObjectTranslator< JobPool, JobPoolVo > po2voer = new GenericObjectTranslator< JobPool, JobPoolVo >();

    @Autowired
    private JobPoolService jobPoolService;

    @Autowired
    private PlanScheduleService planScheduleService;

    @Autowired
    private JobService jobService;

    @Autowired
    private PlanInstanceService planInstanceService;

    @Autowired
    private RunningJobService runningJobService;

    @Autowired
    private JobServerConfigService jobServerConfigService;

    @Override
    public void runJob(JobPool jobPool) {
        jobPool = jobPoolService.findById(jobPool.getId());
//        Job job = (Job) this.jpaDaoImpl.findByHqlSingle("from Job where id=?", jobPool.getJobId());
        Job job = jobService.findById(jobPool.getJobId());
//        PlanInstance planInstance=this.jpaDaoImpl.findById(PlanInstance.class, jobPool.getPlanInstId());
        PlanInstance planInstance = planInstanceService.findById(jobPool.getPlanInstId());
        if(job instanceof SerialJob || job instanceof ParallelJob){
            RunningJob rj = new RunningJob();
            rj.setJobId(job.getId());
            rj.setPlanInstId(jobPool.getPlanInstId());
            rj.setRunHostname("localhost");
            rj.setRunLog("");
            rj.setStartTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
            rj.setStatus(RunningJob.RUNNING);
            runningJobService.create(rj);
            jobPoolService.delete(jobPool);
        }else if(job instanceof PlanJob){
            RunningJob rj = new RunningJob();
            rj.setJobId(job.getId());
            rj.setPlanInstId(jobPool.getPlanInstId());
            rj.setRunHostname("localhost");
            rj.setRunLog("");
            rj.setStartTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
            rj.setStatus(RunningJob.RUNNING);
            rj.setCallPlanInstId(planScheduleService.startPlan(((PlanJob)job).getCallPlanConfigId(),rj.getPlanInstId(),planInstance.getBatchNo(),null));
            runningJobService.create(rj);
            jobPoolService.delete(jobPool);
        }else{
            String clientName=jobServerConfigService.getMaxFreeThreadClientName(job.getJobType());
            if(StringUtils.isNotBlank(clientName)){
//                this.jpaDaoImpl.getEntityManager().lock(jobPool, LockModeType.PESSIMISTIC_READ);//缁欒繖涓疄浣撶被娣诲姞鎮茶閿�璁╁叾浠栦簨鍔″浜庣瓑寰呯姸鎬�
                jobPoolService.lock(jobPool);
                jobServerConfigService.runJob(job.getJobInst(planInstance), clientName);
                jobPool.setStatus(JobPool.TAKE);
                jobPoolService.update(jobPool);
            }
        }
    }
}
