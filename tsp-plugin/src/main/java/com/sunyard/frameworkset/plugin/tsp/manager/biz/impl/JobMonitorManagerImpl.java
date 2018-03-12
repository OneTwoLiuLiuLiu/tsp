package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.dto.GenericObjectTranslator;
import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.JobMonitorManager;
import com.sunyard.frameworkset.plugin.tsp.manager.core.server.netty.TspNettyServer;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobMonitor;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunningJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.ZTreeNode;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.JobMonitorEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.TspPluginMsgEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.service.JobMonitorService;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobConfigService;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobService;
import com.sunyard.frameworkset.plugin.tsp.manager.service.PlanConfigService;
import com.sunyard.frameworkset.plugin.tsp.manager.service.RunningJobService;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.JobMonitorVo;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserAndOrgService;
import com.sunyard.frameworkset.util.pages.PageList;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2016/12/29 0029 上午 9:55
 * version $Id: JobServerConfigManager.java, v 0.1 Exp $
 */
@Transactional
@Component
public class JobMonitorManagerImpl extends BaseManagerImpl<JobMonitorVo,JobMonitor,String,JobMonitorQo> implements JobMonitorManager {
    @Autowired
    private JobMonitorService jobMonitorService;
    @Autowired
    private RunningJobService runningJobService;
    @Autowired(required = false)
    private TspNettyServer tspNettyServer;
    @Autowired
    private UserAndOrgService userAndOrgService;
    @Autowired
    private PlanConfigService planConfigService;
    @Autowired
    private JobConfigService jobConfigService;
    @Autowired
    private JobService jobService;


    public boolean reRun(JobMonitorQo qo) {
        return jobMonitorService.reRunJob(qo);

    }

    @Override
    public boolean manualPass(String id) {
        return jobMonitorService.manualPass(id);
    }

    @Override
    public boolean stopJob(JobMonitorQo qo) {
      try{
          ResultMessage resultMessage = tspNettyServer.stopJob(qo.getRunHostname(), qo.getJobId());
          if (resultMessage == null) {
              return false;
          }
          RunningJob runningJob = runningJobService.findById(qo.getId());
          runningJob.setLastModifyTime(DateUtil.dataToStr(new Date(),
                  "yyyy-MM-dd HH:mm:ss"));
          runningJob.setLastModifyIUser(userAndOrgService.getCurrentUser().getName());
          if (resultMessage.getSuccess()) {
              runningJob.setStatus(JobMonitorEnum.STOP.getCode());
          } else {
              runningJob.setStatus(JobMonitorEnum.RUNERRER.getCode());
          }
          int result = runningJobService.updateRunStatus(runningJob);
          if (result != 0) {
              return true;
          }
      } catch (Exception e) {
          throw  new TspException(TspPluginMsgEnum.QUERY_ERROR.getCode(), "没有获取客户端的同步服务", e);
      }
        return false;
    }

    @Override
    public boolean pauseJob(JobMonitorQo qo) {
        ResultMessage resultMessage = null;
        try {
            resultMessage = tspNettyServer.pauseJob(qo.getRunHostname(), qo.getJobId());
            RunningJob runningJob = runningJobService.findById(qo.getId());
            runningJob.setLastModifyTime(DateUtil.dataToStr(new Date(),
                    "yyyy-MM-dd HH:mm:ss"));
            runningJob.setLastModifyIUser(userAndOrgService.getCurrentUser().getName());
            if (resultMessage.getSuccess()) {
                runningJob.setStatus(JobMonitorEnum.PAUSE.getCode());
            } else {
                runningJob.setStatus(JobMonitorEnum.RUNERRER.getCode());
            }
            int result = runningJobService.updateRunStatus(runningJob);
            if (result != 0) {
                return true;
            }

        } catch (Exception e) {
           throw  new TspException(TspPluginMsgEnum.QUERY_ERROR.getCode(), "没有获取客户端的同步服务", e);
        }
        return false;
    }






    @Override
    public boolean continueRun(JobMonitorQo qo) {
       try{
           ResultMessage resultMessage=tspNettyServer.continueJob(qo.getRunHostname(),qo.getJobId());
           RunningJob runningJob = runningJobService.findById (qo.getId());
           runningJob.setLastModifyTime (DateUtil.dataToStr (new Date (),
                   "yyyy-MM-dd HH:mm:ss"));
           runningJob.setLastModifyIUser (userAndOrgService.getCurrentUser ().getName ());
           if (resultMessage.getSuccess ()) {
               runningJob.setStatus (JobMonitorEnum.PAUSE.getCode());
           } else {
               runningJob.setStatus (JobMonitorEnum.RUNERRER.getCode());
           }
           int result=runningJobService.updateRunStatus(runningJob);
           if(result!=0){
               return true;
           }
       }catch (Exception e) {
           throw  new TspException(TspPluginMsgEnum.QUERY_ERROR.getCode(), "没有获取客户端的同步服务", e);
       }
        return false;
    }

    @Override
    public String getJobLog(String hostname, String planInstId, String jobId) {
        return tspNettyServer.getLogContent (hostname, planInstId, jobId);
    }

    /*
	 * 得到作业配置树的信息，把树显示出来
	 *
	 */
    public List<ZTreeNode> getZTree(PlanConfigQo qo,String id) {
        List<ZTreeNode> ztreenodes = new ArrayList<ZTreeNode>();
        List<PlanConfig> planConfigs = planConfigService.queryPageList(qo);
        for(PlanConfig planConfig:planConfigs){
            String planId = planConfig.getId();
            String planName = planConfig.getName();
            if (StringUtils.isBlank(id)) {
                ZTreeNode root = new ZTreeNode();
                root.setName(planName);
                root.setId(planId);
                root.setIsParent(true);
                root.setJobConfigType("root");
                ztreenodes.add(root);
            }
        }
        return ztreenodes;
    }

    /*
	 * 得到作业配置树的信息，把树显示出来(包含子任务)
	 *
	 */
    public List<ZTreeNode> getZTrees(PlanConfigQo qo,String id) {
        List<ZTreeNode> ztreenodes = new ArrayList<ZTreeNode>();
        List<PlanConfig> planConfigs = planConfigService.queryPageList(qo);
        for(PlanConfig planConfig:planConfigs){
            String planId = planConfig.getId();
            String planName = planConfig.getName();
            if (StringUtils.isBlank(id)) {
                ZTreeNode root = new ZTreeNode();
                root.setName(planName);
                root.setId(planId);
                root.setIsParent(true);
                root.setJobConfigType("root");
                root.setChildren(jobConfigService.getChildrenNode("root", planId));
                ztreenodes.add(root);
            }else{
                ztreenodes = jobConfigService.getChildrenNode(id, planId);
            }
        }
        return ztreenodes;
    }

    @Override
    public PageList<JobMonitorVo> queryPageList(JobMonitorQo qo){
        if(qo.getpName() != null && !qo.getpName().equals("")){
            String parentId = null;
            String planConfigId = null;
            PlanConfig planConfig = planConfigService.findById(qo.getId());
            if(planConfig != null){
                qo.setpName(qo.getpName());
            }else{
                qo.setpName(null);
                parentId = qo.getId();
                JobConfig jobConfig1 = jobConfigService.findById(qo.getId());
                planConfigId = jobConfig1.getPlanConfig().getId();

                List<ZTreeNode> list;
                list = jobConfigService.getAllChildrenNode(parentId, planConfigId);
                List jobIds;
                List jobIdss=new ArrayList();
                if(list.size() == 0){
                   jobIds=jobService.findbyConfigId(qo.getId());
                   qo.setJobIds(jobIds);
                }else{
                    JobConfig jobConfig;
                    for(int i = 0 ; i<list.size() ; i++){
                        jobConfig = jobConfigService.findById(list.get(i).getId());
                        String jobId = jobConfig.getId();
                       jobIds=jobService.findbyConfigId(jobId);
                        jobIdss.add(jobIds);
                    }
                    qo.setJobIds(jobIdss);
                }
            }
        }
        PageList<JobMonitor> result=jobMonitorService.queryPageList(qo);
        PageList<JobMonitorVo> resultVo=new PageList<JobMonitorVo>();
        for(JobMonitor jobMonitor:result){
            JobMonitorVo jobMonitorVo=new GenericObjectTranslator<JobMonitor,JobMonitorVo>().transfer(new JobMonitorVo(), jobMonitor);
            resultVo.add(jobMonitorVo);
        }

        resultVo.setPageTools(result.getPageTools());
        return resultVo;
    }

}
