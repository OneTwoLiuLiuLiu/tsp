package com.sunyard.frameworkset.plugin.tsp.manager.vo.job;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.AddBatchNo;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.StoreProJobInst;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StoreProJobVo extends JobVo {

	private String jobType="storepro";
	

	private String procedureName;

	 private String dataBaseType;

	 private String dataBaseUser;

	 private String dataBasePwd;

	 private String dataBaseIp;

	 private String dataBasePort;

	 private String dataBaseName;
	 
	 
	 
	 
	 public String getDataBasePwd() {
		return dataBasePwd;
	}

	public void setDataBasePwd(String dataBasePwd) {
		this.dataBasePwd = dataBasePwd;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getDataBaseType() {
		return dataBaseType;
	}

	public void setDataBaseType(String dataBaseType) {
		this.dataBaseType = dataBaseType;
	}

	public String getDataBaseUser() {
		return dataBaseUser;
	}

	public void setDataBaseUser(String dataBaseUser) {
		this.dataBaseUser = dataBaseUser;
	}

	public String getDataBaseIp() {
		return dataBaseIp;
	}

	public void setDataBaseIp(String dataBaseIp) {
		this.dataBaseIp = dataBaseIp;
	}

	public String getDataBasePort() {
		return dataBasePort;
	}

	public void setDataBasePort(String dataBasePort) {
		this.dataBasePort = dataBasePort;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public String getIcon(){
			return "ui-icon-chu";
		}

		public String getJobType() {
			return jobType;
		}

		@Override
		public JobInst getJobInst(PlanInstance planInstance) {
			//获取计划实例的id和批字号
			String planInstId=planInstance.getId();
			String batchNo=planInstance.getBatchNo();
			//创建存储过程作业实例
			StoreProJobInst sji = new StoreProJobInst();
			BeanUtils.copyProperties (this, sji);
			sji.setPlanInstId(planInstId);
			sji.setJobId(this.getId());
		    String timeFormat=this.getTimeFormat();
			AddBatchNo.isNeedBatchNo (batchNo, sji, timeFormat, planInstance);
			return sji;
		}
		
		public List<Map<String,Object>> getSubInfoResult() throws Exception{
			List<Map<String,Object>> lists=new ArrayList<Map<String,Object>>();
			 Class clazz = this.getClass();
			 Field[] fields=clazz.getDeclaredFields();
			 for (Field field : fields) {
				 Map<String,Object> map=new HashMap<String,Object>();
				 JobDesc jobDesc= field.getAnnotation(JobDesc.class);
				 if(jobDesc != null){
					 field.setAccessible(true);
					 if("dataBaseType".equals(field.getName())){
						 map.put("text", jobDesc.desc());
						 if ("0".equals(field.get(this))) {
								map.put("value", "oracle");
							} else if ("1".equals(field.get(this))){
								map.put("value", "db2");
							}else if("2".equals(field.get(this))){
								map.put("value", "mysql");
							} 
					 }
					 map.put("text",jobDesc.desc());
					 map.put("value",field.get(this));
					 lists.add(map);
				 }
		}
			 return lists;
		}
}
