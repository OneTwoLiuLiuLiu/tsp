package com.sunyard.frameworkset.plugin.tsp.manager.biz;

import com.sunyard.frameworkset.core.manager.BaseManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobServerConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobServerConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.JobServerConfigVo;

import java.util.List;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2016/12/29 0029 上午 9:55
 * version $Id: JobServerConfigManagerImpl.java, v 0.1 Exp $
 */
public interface JobServerConfigManager extends BaseManager
        <JobServerConfigVo,JobServerConfig,String,JobServerConfigQo> {
        boolean jobServerIsAlive(String hostname);
        boolean changestatus(String jobServerConfigId,String status);

        /**
         * 刷新作业服务器的配置信息
         * @param jobServerConfigVo
         * @return
         */
        void refreshClientResource(JobServerConfigVo jobServerConfigVo);
}
