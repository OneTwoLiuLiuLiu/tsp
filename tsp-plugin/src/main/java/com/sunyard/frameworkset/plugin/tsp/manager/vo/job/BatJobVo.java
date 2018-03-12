package com.sunyard.frameworkset.plugin.tsp.manager.vo.job;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.AddBatchNo;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.BatJobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BatJobVo extends JobVo {

	protected String jobType="bat";

	private String filePath;
	
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getIcon(){
		return "ui-icon-bat";
	}

	public String getJobType() {
		return jobType;
	}

	public String toString() {
		return "BatJob [filePath=" + filePath + "]";
	}

	public JobInst getJobInst(PlanInstance planInstance) {
		//获取计划实例的id和批字号
		String planInstId=planInstance.getId();
		String batchNo=planInstance.getBatchNo();
		//创建bat作业实例
		BatJobInst bji = new BatJobInst();
		BeanUtils.copyProperties (this, bji);
		bji.setPlanInstId(planInstId);
		bji.setJobId(this.getId());
	    String timeFormat=this.getTimeFormat();
		AddBatchNo.isNeedBatchNo (batchNo, bji, timeFormat, planInstance);
		return bji;
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
