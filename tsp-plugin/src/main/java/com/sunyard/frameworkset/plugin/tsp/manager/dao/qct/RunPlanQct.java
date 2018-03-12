package com.sunyard.frameworkset.plugin.tsp.manager.dao.qct;

import com.sunyard.frameworkset.plugin.tsp.manager.qo.RunPlanQo;
import com.sunyard.frameworkset.core.dao.QueryCondition;
import com.sunyard.frameworkset.core.dao.QueryConditionTransfer;

/**
 * 手动执行计划 Qct转化类
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
public class RunPlanQct extends QueryConditionTransfer<RunPlanQo> {

    @Override
    public void transNameQuery (RunPlanQo qo, QueryCondition condition ) {
        if(qo!=null){
            if(qo.getPlanconfigname()!=null&&!"".equals(qo.getPlanconfigname().trim())){
                condition.add(" And obj.planConfig.name like :planconfigname", "planconfigname", qo.getBlurKeyword(qo.getPlanconfigname()));
            }
            if(qo.getStartTimeBelow()!=null&&!"".equals(qo.getStartTimeBelow().trim())){
                condition.add(" And obj.startTime>=:startTimeBelow","startTimeBelow",qo.getStartTimeBelow());
            }
            if(qo.getStartTimeTop()!=null&&!"".equals(qo.getStartTimeTop().trim())){
                condition.add(" And obj.startTime<=:startTimeTop","startTimeTop",qo.getStartTimeTop());
            }
            if(qo.getEndTimeBelow()!=null&&!"".equals(qo.getEndTimeBelow().trim())){
                condition.add(" And obj.endTime>=:endTimeBelow","endTimeBelow",qo.getEndTimeBelow());
            }
            if(qo.getEndTimeTop()!=null&&!"".equals(qo.getEndTimeTop().trim())){
                condition.add(" And obj.endTime<=:endTimeTop","endTimeTop",qo.getEndTimeTop());
            }
            if(qo.getStatus()!=null&&!"".equals(qo.getStatus().trim())){
                condition.add(" And obj.status=:status","status",qo.getStatus());
            }
        }
    }
}
