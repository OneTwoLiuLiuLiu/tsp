package com.sunyard.frameworkset.plugin.tsp.manager.service;

import com.sunyard.frameworkset.core.service.BaseService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigQo;

import java.util.List;

/**
 * 计划配置 service 接口
 * <p/>
 * Author: Created by code generator
 * Date: Tue Jan 03 10:27:53 CST 2017
 */
public interface PlanConfigService extends BaseService<PlanConfig, String, PlanConfigQo> {
	List<PlanConfig> getSharedPlanConfigs ();
}
