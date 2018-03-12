package com.sunyard.frameworkset.plugin.tsp.manager.service;

import com.sunyard.frameworkset.core.service.BaseService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanMonitor;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanMonitorQo;

import java.util.List;

/**
 * 计划监控 service 接口
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 11:06:54 CST 2017
 */
public interface PlanMonitorService extends BaseService<PlanMonitor, String, PlanMonitorQo> {
    /**
     * 根据计划实例ID获取该计划实例的状态
     * 0-暂停;1-运行中;2-完成;3-结束;4-失败
     * @param planInstId
     * @return
     */
     String getPlanInstStatus(String planInstId);

    List<PlanMonitor> getPlanInstByPlanConfigIdAndStatus1(String PlanConfigId);
     List<PlanMonitor> getPlanInstByPlanConfigIdAndStatus5(String PlanConfigId);

}
