package com.sunyard.frameworkset.plugin.tsp.manager.dao.qct;

import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanQo;
import com.sunyard.frameworkset.core.dao.QueryCondition;
import com.sunyard.frameworkset.core.dao.QueryConditionTransfer;

/**
 * 计划 Qct转化类
 *
 * Author: Created by code generator
 * Date: Wed Jan 04 14:25:48 CST 2017
 */
public class PlanQct extends QueryConditionTransfer<PlanQo> {

    @Override
    public void transNameQuery (PlanQo qo, QueryCondition condition ) {
        if( qo!=null ) {
            if(qo.getPlanConfig()!=null && qo.getPlanConfig().getId()!=null){
                condition.add ( " And obj.planConfig.id=:planConfigId","planConfigId",qo.getPlanConfig().getId() );
                condition.appenOrderBy("obj.version");
            }
        }
    }

    @Override
    public void transQuery(PlanQo qo, QueryCondition condition) {

    }

}
