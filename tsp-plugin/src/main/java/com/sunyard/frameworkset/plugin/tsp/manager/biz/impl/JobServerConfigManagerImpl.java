package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.JobServerConfigManager;
import com.sunyard.frameworkset.plugin.tsp.manager.core.server.netty.TspNettyServer;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobServerConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.JobServerConfigStatusEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.TspPluginMsgEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobServerConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobServerConfigService;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.JobServerConfigVo;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2016/12/29 0029 上午 9:55
 * version $Id: JobServerConfigManager.java, v 0.1 Exp $
 */
@Transactional
@Component
public class JobServerConfigManagerImpl extends BaseManagerImpl
        <JobServerConfigVo,JobServerConfig,String,JobServerConfigQo> implements JobServerConfigManager{
    @Autowired
    private JobServerConfigService jobServerConfigService;

    @Autowired(required = false)
    private TspNettyServer server;

//    @Override
//    public GenericResult<PageList<JobServerConfigVo>> add(JobServerConfigQo qo){
//        GenericResult<PageList<JobServerConfigVo>> result=new GenericResult<PageList<JobServerConfigVo>>();
//        PageList<JobServerConfig> pageList=jobServerConfigService.queryPageList(qo);
//
//        PageList<JobServerConfigVo> jobServerConfigVoPageList=new PageList<JobServerConfigVo>();
//
//        if(CollectionUtils.isNotEmpty(pageList)){
//            for(JobServerConfig jobServerConfig :pageList){
//                JobServerConfigVo jobServerConfigVo=new GenericObjectTranslator<JobServerConfig,JobServerConfigVo>().transfer(new JobServerConfigVo(), jobServerConfig);
//                jobServerConfigVoPageList.add(jobServerConfigVo);
//                }
//        }
//        jobServerConfigVoPageList.setPageTools(pageList.getPageTools());
//        result.setResult(jobServerConfigVoPageList);
//        result.setSuccess(true);
//        return result;
//    }

    public boolean jobServerIsAlive(String hostname) {
        return server.JobServerIsAlive(hostname);
        //return true;
    }

/*    @Override
    @Logable
    public void update(JobServerConfigVo jobServerConfigVo) {
        JobServerConfig jobServerConfig = jobServerConfigService.findById(jobServerConfigVo.getId());
        jobServerConfig.setHostname(jobServerConfigVo.getHostname());
        jobServerConfig.setMaxRunNum(jobServerConfigVo.getMaxRunNum());
        jobServerConfig.setMinFreeCpu(jobServerConfigVo.getMinFreeCpu());
        jobServerConfig.setMaxHistoryDay(jobServerConfigVo.getMaxHistoryDay());
        jobServerConfig.setMinFreeMemory(jobServerConfigVo.getMinFreeMemory());
//        jobServerConfig.set
        jobServerConfigService.update(jobServerConfig);
    }*/

    @Override
    public boolean changestatus(String jobServerConfigId,String status) {
        try{
            JobServerConfig jobServerConfig = jobServerConfigService.findById(jobServerConfigId);
            if(JobServerConfigStatusEnum.ENABLED.getCode().equals(status.trim())){
                jobServerConfig.setStatus(JobServerConfigStatusEnum.ENABLED.getCode());
            }else if(JobServerConfigStatusEnum.DISABLED.getCode().equals(status.trim())){
                jobServerConfig.setStatus(JobServerConfigStatusEnum.DISABLED.getCode());
            }
            jobServerConfigService.update(jobServerConfig);
            return true;
        }catch (Exception e){
            new TspException(TspPluginMsgEnum.UPDATE_ERROR.getCode(),"更新作业服务器状态出错",e);
        }
        return false;
    }

    /**
     * 刷新作业服务器的配置信息
     * @param jobServerConfigVo
     * @return
     */
    @Override
    public void refreshClientResource(JobServerConfigVo jobServerConfigVo) {
        JobServerConfig jobServerConfig = jobServerConfigService.findById(jobServerConfigVo.getId());
        jobServerConfig.setMinFreeMemory(jobServerConfigVo.getMinFreeMemory());
        jobServerConfig.setMaxHistoryDay(jobServerConfigVo.getMaxHistoryDay());
        jobServerConfig.setMinFreeCpu(jobServerConfigVo.getMinFreeCpu());
        jobServerConfig.setHostname(jobServerConfigVo.getHostname());
        jobServerConfig.setMaxRunNum(jobServerConfigVo.getMaxRunNum());
        jobServerConfig.setRunJobType(jobServerConfigVo.getRunJobType());
        ResultMessage rm = server.refreshClientResource(jobServerConfig);
        if (!rm.getSuccess()) {
            if (rm.getResult() instanceof TspException) {
                TspException tspe = (TspException) rm
                        .getResult();
                throw tspe;
            } else {
                throw new RuntimeException(rm.getResult().toString());
            }
        }
        jobServerConfigService.update(jobServerConfig);
    }
}
