package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.RunPlanDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunPlan;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.RunPlanQo;
import org.springframework.stereotype.Repository;

/**
 * 手动执行计划 jdbc实现类
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Repository
public class JpaRunPlanDaoImpl  extends JpaBaseDaoImpl<RunPlan,String,RunPlanQo> implements RunPlanDao{

}
