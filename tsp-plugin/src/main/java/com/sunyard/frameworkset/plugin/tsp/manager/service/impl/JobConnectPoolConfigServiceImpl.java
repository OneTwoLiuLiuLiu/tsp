package com.sunyard.frameworkset.plugin.tsp.manager.service.impl;

import com.sunyard.frameworkset.core.service.BaseServiceImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.JobConnectPoolConfigDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobConnectPoolConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobConnectPoolConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobConfigService;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobConnectPoolConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by pc on 2018/3/2.
 */
@Service
public class JobConnectPoolConfigServiceImpl extends BaseServiceImpl<JobConnectPoolConfig,String,JobConnectPoolConfigQo> implements JobConnectPoolConfigService{

    private static final Logger logger = LoggerFactory
            .getLogger(JobConnectPoolConfigServiceImpl.class);

    @Autowired
    private JobConnectPoolConfigDao jobConnectPoolConfigDao;

}
