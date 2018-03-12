package com.sunyard.frameworkset.plugin.tsp.manager.dao.qct;

import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigQo;
import com.sunyard.frameworkset.core.dao.QueryCondition;
import com.sunyard.frameworkset.core.dao.QueryConditionTransfer;

/**
 * 计划配置 Qct转化类
 * <p/>
 * Author: Created by code generator
 * Date: Tue Jan 03 10:27:53 CST 2017
 */
public class PlanConfigQct extends QueryConditionTransfer<PlanConfigQo> {

	@Override
	public void transNameQuery (PlanConfigQo qo, QueryCondition condition ) {
		if(qo!=null){
			if(qo.getName()!=null&&!"".equals(qo.getName().trim())){
				condition.add(" And obj.name like :name", "name", qo.getBlurKeyword(qo.getName()));
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
