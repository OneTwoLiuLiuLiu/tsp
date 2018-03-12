package com.sunyard.frameworkset.plugin.tsp.manager.service;

import com.sunyard.frameworkset.core.service.BaseService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanQo;

/**
 * 计划 service 接口
 * <p/>
 * Author: Created by code generator
 * Date: Wed Jan 04 14:25:48 CST 2017
 */
public interface PlanService extends BaseService<Plan, String, PlanQo> {
	/**
	 * 根据planConfigId获取最大版本号
	 *
	 * @param planConfigId
	 * @return
	 */
	Integer getMaxVersionByPlanConfigId (String planConfigId);


	/**
	 * 根据planConfig和version查找
	 *
	 * @param planConfigId
	 * @param version
	 * @return
	 */
	Plan findByPlanConfigIdAndVersion (String planConfigId, Integer version);

	/**
	 * 发布任务
	 *
	 * @param planConfigId
	 * @param planConfig
	 * @param deployUser
	 * @param deployUserCode
	 * @return
	 */
	Plan releasePlan (String planConfigId, PlanConfig planConfig, String deployUser, String deployUserCode);

	/**
	 * 根据planConfigId查找plan
	 *
	 * @param planConfigId
	 * @return
	 */
	Plan getPlanByPlanConfigId (String planConfigId);

	/**
	 * 根据
	 */
}
