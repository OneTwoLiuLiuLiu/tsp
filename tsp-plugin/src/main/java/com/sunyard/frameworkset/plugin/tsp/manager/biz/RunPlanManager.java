package com.sunyard.frameworkset.plugin.tsp.manager.biz;

import com.sunyard.frameworkset.core.manager.BaseManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunPlan;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.RunPlanQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.RunPlanVo;

/**
 * 手动执行计划 manager 接口
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
public interface RunPlanManager extends BaseManager<RunPlanVo, RunPlan, String, RunPlanQo> {
    /**
     * 运行计划
     * @param runPlanId
     */
    void reRunPlan(String runPlanId);

    /**
     * 停止计划
     * @param runPlanId
     */
    void stopPlan(String runPlanId);
}
