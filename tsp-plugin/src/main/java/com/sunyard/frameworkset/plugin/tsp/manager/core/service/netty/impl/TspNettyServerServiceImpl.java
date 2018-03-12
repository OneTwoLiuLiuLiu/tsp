package com.sunyard.frameworkset.plugin.tsp.manager.core.service.netty.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunyard.frameworkset.plugin.tsp.manager.core.service.mail.MailService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobPool;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunningJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobPoolService;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobService;
import com.sunyard.frameworkset.plugin.tsp.manager.service.PlanScheduleService;
import com.sunyard.frameworkset.plugin.tsp.manager.service.RunningJobService;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import com.sunyard.frameworkset.plugin.tsp.spi.message.service.MessageService;
import com.sunyard.frameworkset.plugin.tsp.spi.server.service.TspNettyServerService;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserAndOrgService;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.summercool.hsf.annotation.RemoteServiceContract;


@Service
@Transactional
@RemoteServiceContract
public class TspNettyServerServiceImpl implements TspNettyServerService {

    private Logger logger = LoggerFactory.getLogger(TspNettyServerServiceImpl.class);

    @Autowired(required=false)
    private MailService mailService;

    @Autowired(required=false)
    private MessageService messageService;

    @Autowired(required=false)
    private UserAndOrgService userAndOrgService;

    @Autowired(required=false)
    private PlanScheduleService planScheduleService;

    @Autowired(required=false)
    private RunningJobService runningJobService;

    @Autowired(required=false)
    private JobService jobService;

    @Autowired(required=false)
    private JobPoolService jobPoolService;

    public void dealWithJobResult(ResultMessage jobResult) {
        //RunningJob rj=this.jpaCommonDaoImpl.findById(RunningJob.class, jobResult.getRunjobId());
        RunningJob rj=runningJobService.findById(jobResult.getRunjobId());

        rj.setEndTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
        if(jobResult.getSuccess()){
            rj.setStatus(RunningJob.COMPLE);
        }else{
            //Job job=(Job) jpaCommonDaoImpl.findByHqlSingle("from Job where id=?", rj.getJobId());
            Job job=jobService.findById(rj.getJobId());

            if(job.IGNORERR.equals(job.getIgnoreErr())){
                rj.setStatus(RunningJob.COMPLE);
            }else{
                Object result=jobResult.getResult();
                String errorMessage = "";
                if(result instanceof TaskSchedulingPlatformException){
                    errorMessage = ((TaskSchedulingPlatformException)result).getMessage();
                }else{
                    errorMessage=result.toString();
                }
                sendMessage(job,errorMessage);
                rj.setStatus(RunningJob.RUNERRER);
            }
        }
        //this.jpaCommonDaoImpl.update(rj);
        runningJobService.update(rj);
    }

    private void sendMessage(Job job,String errorMessage) {
        try{
            Plan plan=job.getPlan();
            String recip=plan.getRecipients();
            if(StringUtils.isNotEmpty(recip)){
                Map<String, List<String>> map = getMailAddresses(recip);
                String way=plan.getNoticeWay();
                if(Plan.MAIL.equals(way)){
                    List<String> mails= map.get("mail");
                    String[] a = new String[mails.size()];
                    mailService.sendMail(mails.toArray(a), "错误信息:"+errorMessage, job.getName()+"运行错误");
                }else if(Plan.MESSAGE.equals(way)){
                    List<String> phones= map.get("phone");
                    String[] a = new String[phones.size()];
                    messageService.sendMessage(phones.toArray(a),  job.getName()+"运行错误;"+errorMessage);
                }else if(Plan.MAILANDMESSAGE.equals(way)){
                    List<String> mails= map.get("mail");
                    String[] a = new String[mails.size()];
                    mailService.sendMail(mails.toArray(a), "错误信息:"+errorMessage, job.getName()+"运行错误");
                    List<String> phones= map.get("phone");
                    messageService.sendMessage((String[])phones.toArray(),  job.getName()+"运行错误;"+errorMessage);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
        }
    }

    private Map<String, List<String>> getMailAddresses(String recip){
        Map<String, List<String>>  result = new HashMap<String, List<String>>();
        List<String> mails = new ArrayList<String>();
        List<String> phones = new ArrayList<String>();
        if(recip==null){
            recip="";
        }
        String[] codes=recip.split(",");
        for (String string : codes) {
            UserVo uv= userAndOrgService.findByCode(string);
            mails.add(uv.getMail());
            phones.add(uv.getPhone());
        }
        result.put("mail", mails);
        result.put("phone", phones);
        return result;
    }


    public String checkRunning(JobInst jobInst){
        RunningJob rj = new RunningJob();
        rj.setJobId(jobInst.getJobId());
        rj.setPlanInstId(jobInst.getPlanInstId());
        rj.setRunHostname(jobInst.getRunHostName());
        rj.setRunLog(jobInst.getRunLog());
        rj.setStartTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
        rj.setStatus(RunningJob.RUNNING);
        //this.jpaCommonDaoImpl.create(rj);
        runningJobService.create(rj);

        //List<JobPool> jps=(List<JobPool>) this.jpaCommonDaoImpl.findByHql("from JobPool where planInstId=? and jobId=?", jobInst.getPlanInstId(),jobInst.getJobId());
        List<JobPool> jps=jobPoolService.findByPlanInstIdAndJobId(jobInst.getPlanInstId(),jobInst.getJobId());
        for (JobPool jobPool : jps) {
            //this.jpaCommonDaoImpl.delete(jobPool);
            jobPoolService.delete(jobPool);
        }
        return rj.getId();
    }

}
