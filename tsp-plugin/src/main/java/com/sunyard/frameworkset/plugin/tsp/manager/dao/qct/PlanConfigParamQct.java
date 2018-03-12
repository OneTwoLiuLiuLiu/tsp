package com.sunyard.frameworkset.plugin.tsp.manager.dao.qct;

import com.sunyard.frameworkset.core.dao.QueryCondition;
import com.sunyard.frameworkset.core.dao.QueryConditionTransfer;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigParamQo;
import org.apache.commons.lang.StringUtils;

/**
 *
 *
 * Author: Created by code generator
 * Date: Mon Jan 29 15:16:04 CST 2018
 */
public class PlanConfigParamQct extends QueryConditionTransfer<PlanConfigParamQo> {

    @Override
    public void transNameQuery(PlanConfigParamQo qo, QueryCondition condition) {
        if(null!=qo){
            if(StringUtils.isNotBlank(qo.getParamName())){
                condition.add ( " And obj.paramName=:paramName","paramName",qo.getParamName());
            }
            if(StringUtils.isNotBlank(qo.getParamValue())){
                condition.add ( " And obj.paramValue=:paramValue","paramValue",qo.getParamValue());
            }
            condition.appenOrderBy("obj.id desc");
        }
    }

    @Override
    public void transQuery(PlanConfigParamQo qo, QueryCondition condition) {

    }

}
