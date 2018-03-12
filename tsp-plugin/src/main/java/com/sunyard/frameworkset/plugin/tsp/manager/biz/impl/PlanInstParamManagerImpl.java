package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.PlanInstParamManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstParam;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanInstParamQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanInstParamVo;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * 计划监控 manager
 *
 * Author: Created by code generator
 * Date: Wed Jan 04 13:22:34 CST 2017
 */
@Component
@Transactional
public class PlanInstParamManagerImpl extends BaseManagerImpl<PlanInstParamVo, PlanInstParam, String, PlanInstParamQo> implements PlanInstParamManager {
}
