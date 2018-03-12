package com.sunyard.frameworkset.plugin.tsp.manager.dao.qct;

import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingViewQo;
import com.sunyard.frameworkset.core.dao.QueryCondition;
import com.sunyard.frameworkset.core.dao.QueryConditionTransfer;
import com.sunyard.frameworkset.util.StringHelper;

/**
 * 等待表 Qct转化类
 *
 * Author: Created by code generator
 * Date: Thu Jan 05 10:51:07 CST 2017
 */
public class WaittingViewQct extends QueryConditionTransfer<WaittingViewQo> {

    @Override
    public void transNameQuery ( WaittingViewQo qo, QueryCondition condition ) {

        if( qo!=null ) {
            if ( qo.getPname()!=null&& !StringHelper.isEmpty(qo.getPname())) {
                condition.add ( " And obj.pname like :pname","pname",qo.getBlurKeyword ( qo.getPname().trim() ) );
            }

        }
    }

    @Override
    public void transQuery( WaittingViewQo qo, QueryCondition condition) {
        super.transQuery ( qo, condition );
    }

}
