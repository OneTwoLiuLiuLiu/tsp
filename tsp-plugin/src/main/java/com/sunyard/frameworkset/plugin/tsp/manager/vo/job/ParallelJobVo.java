package com.sunyard.frameworkset.plugin.tsp.manager.vo.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 并行节点
 * @author whj
 *
 */
public class ParallelJobVo extends JobVo {

	protected String jobType="parallel";
	
	public String getIcon(){
		return "ui-icon-parallel";
	}
	
	public String getJobType() {
		return jobType;
	}

	@Override
	public List<Map<String, Object>> getSubInfoResult() throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		return result;
	}
}   
