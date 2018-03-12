package com.sunyard.frameworkset.plugin.tsp.manager.dao.qct;

import com.sunyard.frameworkset.core.dao.QueryCondition;
import com.sunyard.frameworkset.core.dao.QueryConditionTransfer;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobServerConfigQo;

/**
 * Function查询条件转换
 * <p/>
 * User: qianbobo
 * Date: 2016/4/22 8:44
 * version $Id: FunctionQct.java, v 0.1  8:44 Exp $
 */
public class JobServerConfigQct extends QueryConditionTransfer<JobServerConfigQo > {
	@Override
	public  void transNameQuery (JobServerConfigQo qo, QueryCondition condition) {

		if( qo.getHostname()!=null && !"".equals ( qo.getHostname() )){
			condition.add ( " And obj.hostname like :hostname","hostname",qo.getBlurKeyword(qo.getHostname()));
		}
		condition.appenOrderBy ( "obj.hostname" );
	}

	@Override
	public  void transQuery(JobServerConfigQo qo, QueryCondition condition) {
		super.transQuery(qo, condition);
	}
}
