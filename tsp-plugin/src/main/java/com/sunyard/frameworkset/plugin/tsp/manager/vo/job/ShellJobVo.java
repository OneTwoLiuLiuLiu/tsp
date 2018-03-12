package com.sunyard.frameworkset.plugin.tsp.manager.vo.job;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.AddBatchNo;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.ShellJobInst;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShellJobVo extends JobVo {

	protected String jobType="shell";

	private String filePath;

	private String returnValues;
	
	
	public String getReturnValues() {
		return returnValues;
	}

	public void setReturnValues(String returnValues) {
		this.returnValues = returnValues;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getIcon(){
		return "ui-icon-shell";
	}

	public String getJobType() {
		return jobType;
	}

	@Override
	public String toString() {
		return "ShellJob [filePath=" + filePath + "]";
	}
	
	public JobInst getJobInst(PlanInstance planInstance) {
		//获取计划实例的id和批字号
		String planInstId=planInstance.getId();
		String batchNo=planInstance.getBatchNo();
		//创建shell作业实例
		ShellJobInst sji = new ShellJobInst();
		BeanUtils.copyProperties (this, sji);
		sji.setPlanInstId(planInstId);
		sji.setJobId(this.getId());
	    String timeFormat=this.getTimeFormat();
		AddBatchNo.isNeedBatchNo (batchNo, sji, timeFormat, planInstance);
		return sji;
	}

	@Override
	public List<Map<String, Object>> getSubInfoResult() throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Class clazz = this.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Map<String, Object> map = new HashMap<String, Object>();
			JobDesc job = field.getAnnotation(JobDesc.class);
			if (job != null) {
				field.setAccessible(true);
				map.put("text", job.desc());
				map.put("value", field.get(this));		
				result.add(map);
			}
		}
		return result;
	}
	

}
