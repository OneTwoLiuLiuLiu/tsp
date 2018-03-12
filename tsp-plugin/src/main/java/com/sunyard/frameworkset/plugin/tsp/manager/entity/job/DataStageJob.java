package com.sunyard.frameworkset.plugin.tsp.manager.entity.job;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.AddBatchNo;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.DataStageInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@DiscriminatorValue("datastage")
public class DataStageJob extends Job{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Transient
	protected String jobType="datastage";
	
	
	@Column
	@JobDesc(desc="DS工程名称")
	private String dsProjectName;
	
	@Column
	@JobDesc(desc="DS作业名称")
	private String dsJobName;

	public String getDsProjectName() {
		return dsProjectName;
	}

	public void setDsProjectName(String dsProjectName) {
		this.dsProjectName = dsProjectName;
	}

	public String getDsJobName() {
		return dsJobName;
	}

	public void setDsJobName(String dsJobName) {
		this.dsJobName = dsJobName;
	}

	public String getJobType() {
		return jobType;
	}
	
	public String getIcon(){
		return "ui-icon-datasage";
	}
	
	
	public JobInst getJobInst(PlanInstance planInstance) {
		//获取计划实例的id和批字号
		String planInstId=planInstance.getId();
		String batchNo=planInstance.getBatchNo();
		//创建kjb作业实例
		DataStageInst dsi = new DataStageInst();
		BeanUtils.copyProperties (this, dsi);
		dsi.setPlanInstId(planInstId);
		dsi.setJobId(this.getId());
		String timeFormat=this.getTimeFormat();
		AddBatchNo.isNeedBatchNo (batchNo, dsi, timeFormat, planInstance);
		return dsi;
	}

	@Override
	public String toString() {
		return "DataStageJob [jobType=" + jobType + ", dsProjectName="
				+ dsProjectName + ", dsJobName=" + dsJobName
				+ ", getRunParams()=" + getRunParams() + "]";
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
