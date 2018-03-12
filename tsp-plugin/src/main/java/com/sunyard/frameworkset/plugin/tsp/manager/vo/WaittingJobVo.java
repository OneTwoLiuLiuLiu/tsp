package com.sunyard.frameworkset.plugin.tsp.manager.vo;

import java.io.Serializable;

public class WaittingJobVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2687916510940847854L;

	private String id;

	private String pname;

	private String jname;

	private String startuser;

	private String starttime;

	private String createTime;

	private String createuser;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getJname() {
		return jname;
	}

	public void setJname(String jname) {
		this.jname = jname;
	}

	public String getStartuser() {
		return startuser;
	}

	public void setStartuser(String startuser) {
		this.startuser = startuser;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
}
