package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.PlanConfigDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigQo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 计划配置 jdbc实现类
 * <p/>
 * Author: Created by code generator
 * Date: Tue Jan 03 10:27:53 CST 2017
 */
@Repository
public class JpaPlanConfigDaoImpl extends JpaBaseDaoImpl<PlanConfig, String, PlanConfigQo> implements PlanConfigDao {

	@Override
	public List<PlanConfig> getSharedPlanConfigs () {
		return this.find ("from PlanConfig where isShare=?", "1");
	}
}
