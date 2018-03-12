package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.JobServerConfigDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobServerConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobServerConfigQo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2016/12/29 0029 上午 10:09
 * version $Id: JpaJobServerConfigImpl.java, v 0.1 Exp $
 */
@Repository
public class JpaJobServerConfigDaoImpl extends JpaBaseDaoImpl<JobServerConfig,String,JobServerConfigQo> implements JobServerConfigDao {
    private final static Logger LOGGER = LoggerFactory.getLogger ( JpaJobServerConfigDaoImpl.class );

//    @Override
//    protected void transNameQuery(JobServerConfigQo jobServerConfigQo, QueryCondition<JobServerConfig> condition) {
//        if(null!=jobServerConfigQo){
//            if(jobServerConfigQo.getScene().equals(JobServerConfigQo.GET_ALL)) {
//
//            }
//
//        }
//    }

    @Override
    public JobServerConfig findByHostName(String hostName) {
        return this.findBySingle("from JobServerConfig where hostname=?",hostName);
    }

    @Override
    public List<JobServerConfig> findActiveServerByJobType(String jobType) {
        return this.findByQueryString("from JobServerConfig where runJobType like ? and status=?","%"+jobType+"%",JobServerConfig.ABLE);
    }
}
