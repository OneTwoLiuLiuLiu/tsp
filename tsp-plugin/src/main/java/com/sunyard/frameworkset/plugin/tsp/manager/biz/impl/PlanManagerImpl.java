package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.PlanManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanVo;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * 计划 manager
 *
 * Author: Created by code generator
 * Date: Wed Jan 04 14:25:49 CST 2017
 */
@Component
@Transactional
public class PlanManagerImpl extends BaseManagerImpl<PlanVo, Plan, String, PlanQo> implements PlanManager {
}
