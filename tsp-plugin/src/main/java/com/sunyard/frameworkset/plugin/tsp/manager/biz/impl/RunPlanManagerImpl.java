package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.RunPlanManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunPlan;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.RunPlanStatusEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.TspPluginMsgEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.RunPlanQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.RunPlanService;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.RunPlanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * 手动执行计划 manager
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Component
@Transactional
public class RunPlanManagerImpl extends BaseManagerImpl<RunPlanVo, RunPlan, String, RunPlanQo> implements RunPlanManager {
    @Autowired
    private RunPlanService runPlanService;

    @Override
    public void reRunPlan(String runPlanId){
        RunPlan runPlan = runPlanService.findById(runPlanId);
        if(RunPlanStatusEnum.RUN.getCode().equals(runPlan.getStatus())){
            throw new TspException(TspPluginMsgEnum.UPDATE_ERROR.getCode(),"该计划已处于运行状态");
        }
        try {
            runPlan.setStatus(RunPlanStatusEnum.STOP.getCode());
            runPlanService.update(runPlan);
        }catch (Exception e){
            throw new TspException(TspPluginMsgEnum.UPDATE_ERROR.getCode(),"更新运行计划出错",e);
        }

    }

    @Override
    public void stopPlan(String runPlanId) {
        RunPlan runPlan = runPlanService.findById(runPlanId);
        if(RunPlanStatusEnum.STOP.getCode().equals(runPlan.getStatus())){
            throw  new TspException(TspPluginMsgEnum.UPDATE_ERROR.getCode(),"该计划已处于停止状态");
        }
        try {
            runPlan.setStatus(RunPlanStatusEnum.STOP.getCode());
            runPlanService.update(runPlan);
        }catch (Exception e){
            e.printStackTrace();
            throw new TspException(TspPluginMsgEnum.UPDATE_ERROR.getCode(),"更新运行计划出错",e);
        }

    }
}
