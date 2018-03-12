package com.sunyard.frameworkset.plugin.tsp.manager.biz;

import com.sunyard.frameworkset.core.manager.BaseManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobConnectPoolConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobConnectPoolConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.JobConnectPoolConfigVo;

/**
 * Created by pc on 2018/3/2.
 */
public interface JobConnectPoolConfigMannager extends BaseManager<JobConnectPoolConfigVo,JobConnectPoolConfig,String,JobConnectPoolConfigQo>{
    void addDataSource(JobConnectPoolConfigVo jobConnectPoolConfigVo);

    void updataDataSource(JobConnectPoolConfigVo jobConnectPoolConfigVo);
}
