package com.sunyard.frameworkset.plugin.tsp.manager.entity.job;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.AddBatchNo;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JarJobInst;
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
@DiscriminatorValue("jar")
public class JarJob extends Job{
	
	@Transient
	protected String jobType="jar";
	
	@Column(name="filePath",length=100)
	@JobDesc(desc="文件路径")
	private String filePath;
	
	@Column(name="initialMemoryValue",length=10)
	@JobDesc(desc="虚拟机内存初始值")
	private String initialMemoryValue;

	@Column(name="maxMemoryValue",length=10)
	@JobDesc(desc="虚拟机内存最大值")
	private String maxMemoryValue;

	@Column(name="clientName",length=10)
	@JobDesc(desc="客户端名")
	private String clientName;

	public String getClientName () {
		return clientName;
	}

	public void setClientName (String clientName) {
		this.clientName = clientName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getInitialMemoryValue() {
		return initialMemoryValue;
	}

	public void setInitialMemoryValue(String initialMemoryValue) {
		this.initialMemoryValue = initialMemoryValue;
	}

	public String getMaxMemoryValue() {
		return maxMemoryValue;
	}

	public void setMaxMemoryValue(String maxMemoryValue) {
		this.maxMemoryValue = maxMemoryValue;
	}

	public String getIcon(){
		return "ui-icon-java";
	}
	
	public String getJobType() {
		return jobType;
	}
	
	public JobInst getJobInst(PlanInstance planInstance){
		//获取计划实例的id和批字号
		String planInstId=planInstance.getId();
		String batchNo=planInstance.getBatchNo();
		//创建jar文件实例
		JarJobInst jji=new JarJobInst ();
		BeanUtils.copyProperties (this, jji);
		jji.setPlanInstId(planInstId);
		jji.setJobId(this.getId());
	    String timeFormat=this.getTimeFormat();
		AddBatchNo.isNeedBatchNo (batchNo, jji, timeFormat, planInstance);
		return jji;
	}
	
	public List<Map<String,Object>> getSubInfoResult () throws Exception{
		List<Map<String,Object>> lists=new ArrayList<Map<String,Object>>();
		 Class clazz = this.getClass();
		 Field[] fields=clazz.getDeclaredFields();
		 for(Field field:fields){
			 Map<String,Object> map=new HashMap<String,Object>();
			 JobDesc jobDesc= field.getAnnotation(JobDesc.class);
			 if(jobDesc!=null){
				 field.setAccessible(true);
				 map.put("text",jobDesc.desc());
				 map.put("value",field.get(this));
				 lists.add(map);
			 }
		 }
		 return lists;
	}
}
