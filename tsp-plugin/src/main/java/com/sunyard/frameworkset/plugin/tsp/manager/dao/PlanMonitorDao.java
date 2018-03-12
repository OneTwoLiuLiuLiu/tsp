package com.sunyard.frameworkset.plugin.tsp.manager.dao;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanMonitor;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanMonitorQo;
import com.sunyard.frameworkset.core.dao.BaseDao;

import java.util.List;

/**
 * 计划监控 dao 接口
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 11:06:54 CST 2017
 */
public interface PlanMonitorDao extends BaseDao<PlanMonitor, String, PlanMonitorQo> {
    List<PlanMonitor> getPlanInstByPlanConfigIdAndStatus1(String PlanConfigId);
    List<PlanMonitor> getPlanInstByPlanConfigIdAndStatus5(String PlanConfigId);
    /**
     * 根据planConfig和version,time查找
     *
     * @param planConfigId
     * @param version
     * @return
     */
    PlanMonitor findByPlanConfigIdAndVersionTime(String planConfigId, Integer version, String time);
}
