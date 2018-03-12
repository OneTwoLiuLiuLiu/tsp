package com.sunyard.frameworkset.plugin.tsp.manager.service;

import com.sunyard.frameworkset.core.dao.BaseDao;
import com.sunyard.frameworkset.core.service.BaseService;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.JobServerConfigDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobServerConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobServerConfigQo;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2016/12/29 0029 上午 10:33
 * version $Id: JpaJobServerConfigService.java, v 0.1 Exp $
 */
public interface JobServerConfigService extends BaseService<JobServerConfig,String,JobServerConfigQo> {
    /**
     * 作业服务器和主服务器连接
     * @param clientName
     */
    public void connectJobServer(String clientName);

    /**
     * 主服务器与作业服务器断开
     * @param clientName
     */
    public void disconnectServer(String clientName);


    /**
     * 运行作业
     * @param jobinst
     * @param clientName
     * @param clientName
     */
    public void runJob(JobInst jobinst,String clientName);


    /**
     * 根据 hostName查找
     * @param hostName
     * @return
     */
    JobServerConfig  findByHostName(String hostName);

    String getMaxFreeThreadClientName(String jobType);
}
