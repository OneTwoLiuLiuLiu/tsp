package com.sunyard.frameworkset.plugin.tsp.manager.dao;

import com.sunyard.frameworkset.core.dao.BaseDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobServerConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobServerConfigQo;

import java.util.List;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2016/12/29 0029 上午 10:09
 * version $Id: JpaJobServerConfig.java, v 0.1 Exp $
 */
public interface JobServerConfigDao extends BaseDao<JobServerConfig,String,JobServerConfigQo>{
    /**
     * 根据 hostName查找
     * @param hostName
     * @return
     */
    JobServerConfig  findByHostName(String hostName);

    /**
     * 根据可执行任务类型查找处于运行状态的JobServer
     * @param jobType
     * @return
     */
    List<JobServerConfig> findActiveServerByJobType(String jobType);
}
