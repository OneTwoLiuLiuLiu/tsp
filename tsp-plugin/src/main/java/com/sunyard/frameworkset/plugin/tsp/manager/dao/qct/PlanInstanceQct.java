package com.sunyard.frameworkset.plugin.tsp.manager.dao.qct;

import com.sunyard.frameworkset.core.dao.QueryCondition;
import com.sunyard.frameworkset.core.dao.QueryConditionTransfer;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanInstanceQo;

/**
 * Created by mhy on 2017/1/11.
 */
public class PlanInstanceQct extends QueryConditionTransfer<PlanInstanceQo> {
    @Override
    public void transNameQuery (PlanInstanceQo qo, QueryCondition condition ) {
        if( qo!=null ) {
            if(qo.getRunPlanId()!=null){
                condition.add ( " And obj.runPlanId=:runPlanId","runPlanId",qo.getRunPlanId() );
            }
        }
    }

}
