package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.JobMonitorDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobMonitor;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobMonitorQo;
import org.springframework.stereotype.Repository;

/**
 * Created by Misaki on 2016/12/30.
 */
@Repository
public class JobMonitorDaoImpl extends JpaBaseDaoImpl<JobMonitor,String,JobMonitorQo> implements JobMonitorDao {
	private final static Logger LOGGER = LoggerFactory.getLogger (JobMonitorDaoImpl.class);
}
