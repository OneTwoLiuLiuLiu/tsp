package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.JobConnectPoolConfigDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobConnectPoolConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobConnectPoolConfigQo;
import org.springframework.stereotype.Repository;

/**
 * Created by pc on 2018/3/2.
 */
@Repository
public class JobConnectPoolConfigDaoImpl extends JpaBaseDaoImpl<JobConnectPoolConfig,String,JobConnectPoolConfigQo> implements JobConnectPoolConfigDao {
    private final static Logger LOGGER = LoggerFactory.getLogger ( JobConnectPoolConfigDaoImpl.class );

}
