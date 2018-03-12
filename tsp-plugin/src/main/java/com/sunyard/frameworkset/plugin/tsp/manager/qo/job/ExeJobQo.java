package com.sunyard.frameworkset.plugin.tsp.manager.qo.job;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.AddBatchNo;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.ExeJobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExeJobQo extends JobQo {

	private String jobType="exe";

	private String returnValues;

	private String filePath;

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
		return "ui-icon-exe";
	}

	public String getJobType() {
		return jobType;
	}
	
	public JobInst getJobInst(PlanInstance planInstance){
		
		//获取计划实例的id和批字号
		String planInstId=planInstance.getId();
	    String batchNo=planInstance.getBatchNo();
	    //创建exe作业实例
		ExeJobInst eji=new ExeJobInst();
		BeanUtils.copyProperties (this, eji);
		eji.setPlanInstId(planInstId);
		eji.setJobId(this.getId());
	    String timeFormat=this.getTimeFormat();
		AddBatchNo.isNeedBatchNo (batchNo, eji, timeFormat, planInstance);
		return eji;
	}
	
	public List<Map<String,Object>> getSubInfoResult() throws Exception{
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		Class clazz=this.getClass();
		Field[] fields=clazz.getDeclaredFields();
		for(Field field:fields){
			Map<String,Object> map=new HashMap<String,Object>();
			JobDesc jobDesc=field.getAnnotation(JobDesc.class);
			if(jobDesc!=null){
				field.setAccessible(true);
				map.put("text",jobDesc.desc());
				map.put("value", field.get(this));
				result.add(map);
			}
		}
		return result;
	}
}
