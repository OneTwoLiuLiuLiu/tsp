package com.sunyard.frameworkset.plugin.tsp.manager.dao.qct;

import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanMonitorQo;
import com.sunyard.frameworkset.core.dao.QueryCondition;
import com.sunyard.frameworkset.core.dao.QueryConditionTransfer;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingViewQo;
import com.sunyard.frameworkset.util.StringHelper;

/**
 * 计划监控 Qct转化类
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 11:06:54 CST 2017
 */
public class PlanMonitorQct extends QueryConditionTransfer<PlanMonitorQo> {

    @Override
    public void transNameQuery (PlanMonitorQo qo, QueryCondition condition ) {

        if( qo!=null ) {
            if ( qo.getPlanInstId()!=null&& !StringHelper.isEmpty(qo.getPlanInstId())) {
                condition.add ( " And obj.planInstId like :planInstId","planInstId",qo.getBlurKeyword ( qo.getPlanInstId() ) );
            }

            //查询条件
            if(qo.getPname()!=null&&!StringHelper.isEmpty(qo.getPname())){
                condition.add ( " And obj.pname  like:pname","pname",qo.getBlurKeyword ( qo.getPname() ) );
            }
            //任务开始起始时间
            if(qo.getStartTimeTop()!=null&&!StringHelper.isEmpty(qo.getStartTimeTop())){
                condition.add ( " And obj.startTime >=:startTimeTop","startTimeTop",qo.getStartTimeTop().trim() );
            }
            //任务结束起始时间
            if(qo.getEndTimeTop()!=null&&!StringHelper.isEmpty(qo.getEndTime())){
                condition.add ( " And obj.endTime >=:endTimeTop","endTimeTop",qo.getEndTimeTop().trim());
            }
            //任务开始终止时间
            if(qo.getStartTimeBelow()!=null&&!StringHelper.isEmpty(qo.getStartTimeBelow())){
                condition.add ( " And obj.startTime <=:startTimeBelow","startTimeBelow",qo.getStartTimeBelow().trim() );
            }
            //任务结束终止时间
            if(qo.getEndTimeBelow()!=null&&!StringHelper.isEmpty(qo.getEndTimeBelow())){
                condition.add ( " And obj.endTime <=:endTimeBelow","endTimeBelow",qo.getEndTimeBelow().trim());
            }
            if(qo.getStartBatchNo()!=null&&!StringHelper.isEmpty(qo.getStartBatchNo())){
                condition.add ( " And obj.batchNo >=:startBatchNo","startBatchNo",qo.getStartBatchNo().trim());
            }
            if(qo.getEndBatchNo()!=null&&!StringHelper.isEmpty(qo.getEndBatchNo())){
                condition.add ( " And obj.batchNo >=:endBatchNo","endBatchNo",qo.getEndBatchNo().trim());
            }
            if(qo.getStatus()!=null&&!StringHelper.isEmpty(qo.getStatus())){
                condition.add( " And obj.status=:status","status",qo.getStatus().trim());

            }

        }
    }

    @Override
    public void transQuery( PlanMonitorQo qo, QueryCondition condition) {
        super.transQuery ( qo, condition );
    }

}
