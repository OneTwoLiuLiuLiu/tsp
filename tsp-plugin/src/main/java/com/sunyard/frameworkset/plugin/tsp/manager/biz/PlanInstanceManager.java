package com.sunyard.frameworkset.plugin.tsp.manager.biz;

import com.sunyard.frameworkset.core.manager.BaseManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanInstanceQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanInstanceVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanMonitorVo;
import com.sunyard.frameworkset.util.pages.PageList;

import java.util.List;

/**
 * Created by mhy on 2016/12/30.
 */
public interface PlanInstanceManager extends BaseManager<PlanInstanceVo,PlanInstance,String,PlanInstanceQo> {
    /**
     * 暂停计划
     */
    boolean pausePlan(String planInstId);
    /**
     * 启动暂停计划
     */
    boolean runPlan(String planInstId);

    /**
     * 结束计划
     * @param planInstId
     */
    boolean endPlan(String planInstId);

    /**
     * native执行sql查询计划状态
     * @param planMonitorQo
     * @return
     */
    PageList<PlanMonitorVo> queryPlanStatusPageList(PlanMonitorQo planMonitorQo);

    /*
   * 暂停计划实例
   */

    void pausePlanInst(String planInstId);

	/*
	 * 运行计划实例
	 */

    void runPlanInst(String planInstId);


    /**
     * 根据计划实例ID获取该计划实例的状态
     * 0-暂停;1-运行中;2-完成;3-结束;4-失败
     * @param planInstId
     * @return
     */
    String getPlanInstStatus(String planInstId);

    List<PlanInstanceVo> queryPlanInstance(String planName, String startTimeOne,
                                           String endTimeOne, String startTimeTwo, String endTimeTwo,
                                           String startBatchNo, String endBatchNo, String status);
}
