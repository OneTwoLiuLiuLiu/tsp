package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.PlanInstanceManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanInstanceQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.*;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanInstanceVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanMonitorVo;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserAndOrgService;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserVo;
import com.sunyard.frameworkset.util.pages.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mhy on 2016/12/30.
 */
@Transactional
@Component
public class PlanInstanceManagerImpl extends BaseManagerImpl<PlanInstanceVo,PlanInstance,String,PlanInstanceQo> implements PlanInstanceManager{


    @Autowired
    private PlanInstanceService planInstanceService;

    @Autowired
    private WaittingJobService waittingJobService;

    @Autowired
    private UserAndOrgService userAndOrgService;


    @Override
    public boolean pausePlan(String planInstId) {
        Boolean isUpdated = false;
        int updateCount=planInstanceService.pausePlan(planInstId);
        if(updateCount!=0){
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean runPlan(String planInstId) {
        Boolean isUpdated = false;
        int updateCount=planInstanceService.runPlanInstance(planInstId);
        if(updateCount!=0){
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean endPlan(String planInstId) {
        Boolean isUpdated = false;
        UserVo stopUser = userAndOrgService.getCurrentUser();
        int updateCount = planInstanceService.endPlanInstance(planInstId,stopUser);
        if(updateCount!=0){
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public PageList<PlanMonitorVo> queryPlanStatusPageList(PlanMonitorQo planMonitorQo) {
        PageList<PlanMonitorVo> planMonitorVos = planInstanceService.queryPlanMonitor(planMonitorQo);
        return planMonitorVos;
    }

    @Override
    public void pausePlanInst(String planInstId) {

    }

    @Override
    public void runPlanInst(String planInstId) {

    }

    @Override
    public String getPlanInstStatus(String planInstId) {
        return null;
    }

    @Override
    public List<PlanInstanceVo> queryPlanInstance(String planName, String startTimeOne, String endTimeOne, String startTimeTwo, String endTimeTwo, String startBatchNo, String endBatchNo, String status) {
        return null;
    }
//    @Autowired
//    private PlanInstanceService planInstanceService;
//
//    @Autowired
//    private RunningJobService runningJobService;
//    /*
//	 * 暂停计划实例
//	 *
//	 */
//    @Override
//    public  void pausePlanInst(String planInstId){
//        PlanInstance pi = planInstanceService.findById(planInstId);
//        if(PlanInstance.RUNNING.equals(pi.getStatus())){
//            pi.setStatus(PlanInstance.PAUSE);
//            planInstanceService.update(pi);
//        }else{
//            throw new RuntimeException("只有状态为运行中的计划实例才可以暂停");
//        }
//    }
//    /*
//     * 运行计划实例
//     *
//     */
//    @Override
//    public void runPlanInst(String planInstId){
//        PlanInstance pi = planInstanceService.findById(planInstId);
//        if(PlanInstance.PAUSE.equals(pi.getStatus())){
//            pi.setStatus(PlanInstance.RUNNING);
//            planInstanceService.update(pi);
//        }else{
//            throw new RuntimeException("只有状态为暂停的计划实例才可以重新运行");
//        }
//    }
//    @Override
//    public String getPlanInstStatus(String planInstId) {
//        List<RunningJob> result = this.runningJobService.getAllByPlanInstIdAndStatus(planInstId, RunningJob.RUNERRER);
//        if (result != null && result.size() > 0) {
//            return PlanInstance.ERROR;
//        } else {
//            PlanInstance planInst = this.planInstanceService.findById(planInstId);
//            if (planInst == null) {
//                return null;
//            }
//            return planInst.getStatus();
//        }
//    }
//
//    @Override
//    public List<PlanInstanceVo> queryPlanInstance(String planName, String startTimeOne,
//                                                  String endTimeOne, String startTimeTwo, String endTimeTwo,
//                                                  String startBatchNo, String endBatchNo, String status) {
//        startTimeTwo = "".equals(startTimeTwo) || startTimeTwo == null ? ""
//                : startTimeTwo + " 23:59:59";
//        endTimeTwo = "".equals(endTimeTwo) || endTimeTwo == null ? ""
//                : endTimeTwo + " 23:59:59";
//        List<PlanInstance> planInstances = planInstanceService.queryPlanInstance(planName, startTimeOne,
//                endTimeOne, startTimeTwo, endTimeTwo,
//                startBatchNo, endBatchNo, status);
//        PageList<PlanInstanceVo> planInstanceVos = getPo2voer().transfer(PlanInstanceVo.class, planInstances);
//        return planInstanceVos;
//    }

}
