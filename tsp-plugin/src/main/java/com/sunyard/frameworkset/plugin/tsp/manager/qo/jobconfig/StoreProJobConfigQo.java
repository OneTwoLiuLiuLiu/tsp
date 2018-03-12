package com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.qo.job.JobQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.job.StoreProJobQo;
import org.springframework.beans.BeanUtils;

public class StoreProJobConfigQo extends JobConfigQo {

	private String jobType = "storepro";

	private String procedureName;

	private String dataBaseType;

	private String dataBaseUser;

	private String dataBasePwd;

	private String dataBaseIp;

	private String dataBasePort;

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

	public JobQo getJob () {
		StoreProJobQo spj = new StoreProJobQo ();
		BeanUtils.copyProperties (this, spj, new String[]{"id"});
		return spj;
	}


}
