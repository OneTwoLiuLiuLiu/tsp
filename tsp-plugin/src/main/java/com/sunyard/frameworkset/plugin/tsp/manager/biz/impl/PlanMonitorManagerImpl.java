package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.PlanMonitorManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanMonitor;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanMonitorVo;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * 计划监控 manager
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 11:06:54 CST 2017
 */
@Component
@Transactional
public class PlanMonitorManagerImpl extends BaseManagerImpl<PlanMonitorVo, PlanMonitor, String, PlanMonitorQo> implements PlanMonitorManager {
}
