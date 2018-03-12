package com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst;


	public class StoreProJobInst extends JobInst {
		
	 private String type="storepro";
	 
	 private String procedureName;
	 
	 private String dataBaseType;
	 
	 private String dataBaseUser;
	 
	 private String dataBasePwd;
	 
	 private String dataBaseIp;
	 
	 private String dataBasePort;
	 
	 private String dataBaseName;
	 
	 public String getType() {
			return type;
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

	public String getDataBasePwd() {
		return dataBasePwd;
	}

	public void setDataBasePwd(String dataBasePwd) {
		this.dataBasePwd = dataBasePwd;
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

	@Override
	public String toString() {
		return "StoreProJobInst [procedureName=" + procedureName
				+ ", dataBaseType=" + dataBaseType + ", dataBaseUser="
				+ dataBaseUser + ", dataBasePwd=" + dataBasePwd
				+ ", dataBaseIp=" + dataBaseIp + ", dataBasePort="
				+ dataBasePort + ", dataBaseName=" + dataBaseName + "]";
	}

	}
