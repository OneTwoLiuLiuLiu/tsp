package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.PlanInstParamDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstParam;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanInstParamQo;
import org.springframework.stereotype.Repository;

/**
 * 计划监控 jdbc实现类
 *
 * Author: Created by code generator
 * Date: Wed Jan 04 13:22:34 CST 2017
 */
@Repository
public class JpaPlanInstParamDaoImpl extends JpaBaseDaoImpl<PlanInstParam,String,PlanInstParamQo> implements PlanInstParamDao{

}
