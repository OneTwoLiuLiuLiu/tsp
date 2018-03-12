package com.sunyard.frameworkset.plugin.tsp.manager.dao;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanQo;
import com.sunyard.frameworkset.core.dao.BaseDao;

/**
 * 计划 dao 接口
 * <p/>
 * Author: Created by code generator
 * Date: Wed Jan 04 14:25:48 CST 2017
 */
public interface PlanDao extends BaseDao<Plan, String, PlanQo> {



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
	 * 根据planConfigId查找plan
	 *
	 * @param planConfigId
	 * @return
	 */
	Plan getPlanByPlanConfigId (String planConfigId);
}
