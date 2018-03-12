package com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.StoreProJob;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("storepro")
public class StoreProJobConfig extends JobConfig {

	static {
		register ("storepro", StoreProJobConfig.class);
	}

	@Transient
	private String jobType = "storepro";

	@Column(name = "procedureName", length = 50)
	private String procedureName;

	@Column(name = "dataBaseType", length = 50)
	private String dataBaseType;

	@Column(name = "dataBaseUser", length = 50)
	private String dataBaseUser;

	@Column(name = "dataBasePwd", length = 50)
	private String dataBasePwd;

	@Column(name = "dataBaseIp", length = 50)
	private String dataBaseIp;

	@Column(name = "dataBasePort", length = 50)
	private String dataBasePort;

	@Column(name = "dataBaseName", length = 50)
	private String dataBaseName;


	public String getProcedureName () {
		return procedureName;
	}

	public void setProcedureName (String procedureName) {
		this.procedureName = procedureName;
	}

	public String getDataBaseType () {
		return dataBaseType;
	}

	public void setDataBaseType (String dataBaseType) {
		this.dataBaseType = dataBaseType;
	}

	public String getDataBaseUser () {
		return dataBaseUser;
	}

	public void setDataBaseUser (String dataBaseUser) {
		this.dataBaseUser = dataBaseUser;
	}

	public String getDataBasePwd () {
		return dataBasePwd;
	}

	public void setDataBasePwd (String dataBasePwd) {
		this.dataBasePwd = dataBasePwd;
	}

	public String getDataBaseIp () {
		return dataBaseIp;
	}

	public void setDataBaseIp (String dataBaseIp) {
		this.dataBaseIp = dataBaseIp;
	}

	public String getDataBasePort () {
		return dataBasePort;
	}

	public void setDataBasePort (String dataBasePort) {
		this.dataBasePort = dataBasePort;
	}

	public String getDataBaseName () {
		return dataBaseName;
	}

	public void setDataBaseName (String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public void setJobType (String jobType) {
		this.jobType = jobType;
	}

	public String getIcon () {
		return "ui-icon-chu";
	}

	public String getJobType () {
		return jobType;
	}

	public Job getJob () {
		StoreProJob spj = new StoreProJob ();
		BeanUtils.copyProperties (this, spj, new String[]{"id"});
		return spj;
	}


	@Override
	public JobConfig getJobConfigByElement (Element element) throws Exception {
		StoreProJobConfig jobConfig = new StoreProJobConfig ();

		//公有的  7
		String type = element.attribute ("type").getValue ();
		jobConfig = (StoreProJobConfig) ElementToJobConfig.getJobConfigByElement (element, type);
		//存储过程独特的属性
		String procedureName = element.attribute ("procedureName").getValue ();
		String dataBaseType = element.attribute ("dataBaseType").getValue ();
		String dataBaseUser = element.attribute ("dataBaseUser").getValue ();
		String dataBasePwd = element.attribute ("dataBasePwd").getValue ();
		String dataBaseIp = element.attribute ("dataBaseIp").getValue ();
		String dataBasePort = element.attribute ("dataBasePort").getValue ();
		String dataBaseName = element.attribute ("dataBaseName").getValue ();


		jobConfig.setProcedureName (procedureName);
		jobConfig.setDataBaseType (dataBaseType);
		jobConfig.setDataBaseUser (dataBaseUser);
		jobConfig.setDataBasePwd (dataBasePwd);
		jobConfig.setDataBasePort (dataBasePort);
		jobConfig.setDataBaseIp (dataBaseIp);
		jobConfig.setDataBaseName (dataBaseName);
		//验证参数是否合适
		JobPropertyIsNullAble.jobPropertyIsNullAble (jobConfig, type);
		return jobConfig;
	}
}
