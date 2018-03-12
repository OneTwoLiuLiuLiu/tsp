package com.sunyard.frameworkset.plugin.tsp.manager.dao;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigQo;
import com.sunyard.frameworkset.core.dao.BaseDao;

import java.util.List;

/**
 * 计划配置 dao 接口
 * <p/>
 * Author: Created by code generator
 * Date: Tue Jan 03 10:27:53 CST 2017
 */
public interface PlanConfigDao extends BaseDao<PlanConfig, String, PlanConfigQo> {

	List<PlanConfig> getSharedPlanConfigs ();
}
