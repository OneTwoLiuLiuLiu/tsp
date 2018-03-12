package com.sunyard.frameworkset.plugin.tsp.manager.entity.job;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.AddBatchNo;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.StoreProJobInst;
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
@DiscriminatorValue("storepro")
public class StoreProJob extends Job{
	
	@Transient
	private String jobType="storepro";
	
	
	@Column(name="procedureName",length=50)
	@JobDesc(desc="存储过程名字")
	private String procedureName;
	
	 @Column(name="dataBaseType",length=50)
	 @JobDesc(desc="数据库类型")
	 private String dataBaseType;
	 
	 @Column(name="dataBaseUser",length=50)
	 @JobDesc(desc="数据库用户名")
	 private String dataBaseUser;
	 
	 @Column(name="dataBasePwd",length=50)
	 //@JobDesc(desc="数据库密码")
	 private String dataBasePwd;
	 
	 @Column(name="dataBaseIp",length=50)
	 @JobDesc(desc="数据库连接Ip")
	 private String dataBaseIp;
	 
	 @Column(name="dataBasePort",length=50)
	 @JobDesc(desc="数据库连接端口号")
	 private String dataBasePort;
	 
	 @Column(name="dataBaseName",length=50)
	 @JobDesc(desc="数据库名字")
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
