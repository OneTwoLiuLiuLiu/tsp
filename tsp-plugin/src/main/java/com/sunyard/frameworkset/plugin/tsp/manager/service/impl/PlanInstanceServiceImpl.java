package com.sunyard.frameworkset.plugin.tsp.manager.service.impl;

import com.sunyard.frameworkset.core.service.BaseServiceImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.PlanInstanceDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanMonitor;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.PlanMonitorEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanInstanceQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.PlanInstanceService;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanMonitorVo;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserVo;
import com.sunyard.frameworkset.util.pages.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PlanInstanceServiceImpl extends BaseServiceImpl<PlanInstance,String,PlanInstanceQo> implements PlanInstanceService {

    @Autowired
    private PlanInstanceDao planInstanceDao;

    @Override
    public List<PlanInstance> queryPlanInstance(String planName, String startTimeOne, String endTimeOne, String startTimeTwo, String endTimeTwo, String startBatchNo, String endBatchNo, String status) {
        return null;
    }


    @Override
    public PageList<PlanMonitorVo> queryPlanMonitor(PlanMonitorQo planMonitorQo) {
        PageList<PlanMonitorVo> planMonitorVos = planInstanceDao.queryPlanMonitors(planMonitorQo);
        return planMonitorVos;
    }

    @Override
    public int endPlanInstance(String planInstId,UserVo userVo) {
        return planInstanceDao.endPlanInstance(planInstId,userVo);

    }

    @Override
    public int runPlanInstance(String planInstId) {
        int result=0;
        PlanInstance planInstance=planInstanceDao.findPlanInstance(planInstId);
        if(planInstance==null) {
            return result;
        }
        if(planInstance.getStatus().equals(PlanMonitorEnum.PAUSE.getCode())){
            result = planInstanceDao.updatePlanInstance(planInstId, PlanMonitorEnum.RUNNING.getCode());
        }
       return result;
    }

    @Override
    public int pausePlan(String planInstId) {
        int result=0;
        PlanInstance planInstance=planInstanceDao.findPlanInstance(planInstId);
        if(planInstance==null){
            return result;
        }
        if(planInstance.getStatus().equals(PlanMonitorEnum.RUNNING.getCode())){
            result=planInstanceDao.updatePlanInstance(planInstId,PlanMonitorEnum.PAUSE.getCode());
        }
        return result;
    }

    @Override
    public PlanInstance getPlanInstMinTimeAndPlanId(String planId) {
        return planInstanceDao.getPlanInstTimeAndPlanId(planId);
    }

    @Override
    public int updateStatusAndTime(PlanInstance planInstance) {
        return planInstanceDao.updateStatusAndTime(planInstance);
    }

//    @Autowired
//    PlanInstanceService planInstanceService;
//
//    @Override
//    public List<PlanInstance> queryPlanInstance(String planName, String startTimeOne, String endTimeOne, String startTimeTwo, String endTimeTwo, String startBatchNo, String endBatchNo, String status) {
//        return planInstanceService.queryPlanInstance(planName, startTimeOne,
//                endTimeOne, startTimeTwo, endTimeTwo,
//                startBatchNo, endBatchNo, status);
//    }
}
