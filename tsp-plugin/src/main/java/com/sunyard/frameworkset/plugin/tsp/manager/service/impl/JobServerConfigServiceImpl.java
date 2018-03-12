package com.sunyard.frameworkset.plugin.tsp.manager.service.impl;

import com.sunyard.frameworkset.core.dao.BaseDao;
import com.sunyard.frameworkset.core.service.BaseServiceImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.core.server.netty.TspNettyServer;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.JobServerConfigDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobServerConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobServerConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobServerConfigService;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.JobServerResource;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class JobServerConfigServiceImpl extends BaseServiceImpl<JobServerConfig,String,JobServerConfigQo> implements JobServerConfigService{
    private static final Logger logger = LoggerFactory
            .getLogger(JobServerConfigServiceImpl.class);
    @Autowired
    private JobServerConfigDao jobServerConfigDao;

    @Autowired(required = false)
    private TspNettyServer server;

    @Override
    protected BaseDao<JobServerConfig, String, JobServerConfigQo> getDao() {
        return this.jobServerConfigDao;
    }

    @Override
    public void connectJobServer(String clientName) {
        //JobServerConfig jsc = (JobServerConfig) this.jpaDaoImpl.findByHqlSingle("from JobServerConfig where hostname=?", clientName);
        JobServerConfig jsc = jobServerConfigDao.findByHostName(clientName);

        if (jsc == null) {
            jsc = new JobServerConfig();
            jsc.setHostname(clientName);
            jsc.setStatus(JobServerConfig.DISABLE);
            jobServerConfigDao.create(jsc);
        } else {
            ResultMessage rm = server.refreshClientResource(jsc);
            if (!rm.getSuccess()) {
                if (rm.getResult() instanceof TaskSchedulingPlatformException) {
                    TaskSchedulingPlatformException tspe = (TaskSchedulingPlatformException) rm
                            .getResult();
                    throw tspe;
                }
            }
            jobServerConfigDao.update(jsc);
        }
    }

    @Override
    public void disconnectServer(String clientName) {
        return;
    }

    @Override
    public void runJob(JobInst jobInst, String clientName) {
        server.runJob(jobInst, clientName);
    }

    @Override
    public JobServerConfig findByHostName(String hostName) {
        return jobServerConfigDao.findByHostName(hostName);
    }

    @Override
    public String getMaxFreeThreadClientName(String jobType) {
//        List<JobServerConfig> jobServerConfigs = (List<JobServerConfig>) this.jpaDaoImpl.findByHql("from JobServerConfig where runJobType like ? and status=?","%" + jobType + "%", JobServerConfig.ABLE);
        List<JobServerConfig> jobServerConfigs = jobServerConfigDao.findActiveServerByJobType(jobType);
        String clientName = "";
        Integer maxFree = 0;
        for (JobServerConfig jobServerConfig : jobServerConfigs) {
            if (server.JobServerIsAlive(jobServerConfig.getHostname())) {
                ResultMessage rm = server.getResources(jobServerConfig);// 判断该作业服务器的资源信息是否够用
                if (rm.getSuccess()) {
                    JobServerResource jsr = (JobServerResource) rm.getResult();
                    if (jsr.isEnoughResource()) {
                        Integer freeThreadNum = jsr.getFreeThreadNum();
                        if (freeThreadNum > maxFree) {
                            clientName = jobServerConfig.getHostname();
                            maxFree = freeThreadNum;
                        }
                    }
                } else {
                    Object result = rm.getResult();
                    if (result instanceof TaskSchedulingPlatformException) {
                        TaskSchedulingPlatformException tspe = (TaskSchedulingPlatformException) rm.getResult();
                        throw tspe;
                    } else {
                        logger.warn(result.toString());
                    }
                }
            }
        }
        return clientName;
    }
}
