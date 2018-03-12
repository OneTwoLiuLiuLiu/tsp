package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.ReRunningJob;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.ReRunningJobQo;
import org.springframework.stereotype.Repository;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2017/1/10 0010 上午 10:31
 * version $Id: JpaReRunningJobDao.java, v 0.1 Exp $
 */
@Repository
public class JpaReRunningJobDao extends JpaBaseDaoImpl<ReRunningJob,String,ReRunningJobQo> {
}
