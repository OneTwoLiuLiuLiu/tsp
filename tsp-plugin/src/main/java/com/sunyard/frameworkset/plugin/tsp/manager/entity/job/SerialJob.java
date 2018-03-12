package com.sunyard.frameworkset.plugin.tsp.manager.entity.job;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 串行节点
 * @author whj
 *
 */
@Entity
@DiscriminatorValue("serial")
public class SerialJob extends Job{
		
	@Transient
	protected String jobType="serial";
	
	public String getIcon(){
		return "ui-icon-serial";
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
