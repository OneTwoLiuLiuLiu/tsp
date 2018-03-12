package com.sunyard.frameworkset.plugin.tsp.manager.dao;

import com.sunyard.frameworkset.core.dao.BaseDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanMonitor;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanInstanceQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanMonitorVo;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserVo;
import com.sunyard.frameworkset.util.pages.PageList;

import java.util.List;

/**
 * Created by mhy on 2016/12/30.
 */
public interface PlanInstanceDao extends BaseDao<PlanInstance,String,PlanInstanceQo> {
    PlanInstance getPlanInstTimeAndPlanId(String planId);
    PageList<PlanMonitorVo> queryPlanMonitors(PlanMonitorQo planMonitorQo);
    int endPlanInstance(String planInstId,UserVo userVo);
    int updatePlanInstance(String planInstId,String status);
    PlanInstance findPlanInstance(String planInstId);
    int updateStatusAndTime(PlanInstance planInstance);
}
