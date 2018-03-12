package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.PlanMonitorDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanMonitor;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.PlanMonitorEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanMonitorQo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 计划监控 jdbc实现类
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 11:06:54 CST 2017
 */
@Repository
public class JpaPlanMonitorDaoImpl extends JpaBaseDaoImpl<PlanMonitor,String,PlanMonitorQo> implements PlanMonitorDao {

    @Override
    public List<PlanMonitor> getPlanInstByPlanConfigIdAndStatus1(String PlanConfigId) {
        return this.findByQueryString("FROM PlanMonitor as obj where obj.planConfigId=? and obj.status=?",PlanConfigId,"1");
    }

    @Override
    public List<PlanMonitor> getPlanInstByPlanConfigIdAndStatus5(String PlanConfigId) {
        return this.findByQueryString("FROM PlanMonitor as obj where obj.planConfigId=? and obj.status=?",PlanConfigId,"5");
    }

    @Override
    public PlanMonitor findByPlanConfigIdAndVersionTime(String planConfigId, Integer version, String time) {
        return this.findBySingle ("from PlanMonitor where planConfig.id=? and version=? and START_TIME=?", planConfigId, version,time);
    }


}
