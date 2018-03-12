package com.sunyard.frameworkset.plugin.tsp.manager.service.impl;

import com.sunyard.frameworkset.core.service.BaseServiceImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.PlanMonitorDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.PlanMonitorService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 计划监控 service
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 11:06:54 CST 2017
 */
@Transactional
@Service
public class PlanMonitorServiceImpl extends BaseServiceImpl<PlanMonitor, String, PlanMonitorQo> implements PlanMonitorService {
    @Autowired
    private PlanMonitorDao planMonitorDao;
    @Override
    public String getPlanInstStatus(String planInstId) {
        return null;
    }


    @Override
    public List<PlanMonitor> getPlanInstByPlanConfigIdAndStatus1(String PlanConfigId) {
        return planMonitorDao.getPlanInstByPlanConfigIdAndStatus1(PlanConfigId);
    }

    @Override
    public List<PlanMonitor> getPlanInstByPlanConfigIdAndStatus5(String PlanConfigId) {
        return planMonitorDao.getPlanInstByPlanConfigIdAndStatus5(PlanConfigId);
    }

}
