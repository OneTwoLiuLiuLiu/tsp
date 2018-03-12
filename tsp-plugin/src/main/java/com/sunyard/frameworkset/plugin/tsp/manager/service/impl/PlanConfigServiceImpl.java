package com.sunyard.frameworkset.plugin.tsp.manager.service.impl;

import com.sunyard.frameworkset.core.service.BaseServiceImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.PlanConfigDao;
import com.sunyard.frameworkset.plugin.tsp.manager.service.PlanConfigService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigQo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 计划配置 service
 * <p/>
 * Author: Created by code generator
 * Date: Tue Jan 03 10:27:53 CST 2017
 */
@Service
public class PlanConfigServiceImpl extends BaseServiceImpl<PlanConfig, String, PlanConfigQo> implements PlanConfigService {

	@Autowired
	PlanConfigDao planConfigDao;

	@Override
	public List<PlanConfig> getSharedPlanConfigs () {
		return planConfigDao.getSharedPlanConfigs ();
	}
}
