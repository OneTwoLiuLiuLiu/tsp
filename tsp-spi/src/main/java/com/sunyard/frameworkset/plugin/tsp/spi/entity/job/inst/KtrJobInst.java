package com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst;

public class KtrJobInst extends JobInst {
	private String type = "ktr";

	private String file;

	private String runParams;

	private String rep;

	private String user;

	private String pass;

	private String trans;

	private String dir;

	public KtrJobInst() {

	}

	public String getRep() {
		return rep;
	}

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}

	public String getTrans() {
		return trans;
	}

	public String getDir() {
		return dir;
	}

	public void setRep(String rep) {
		this.rep = rep;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setTrans(String trans) {
		this.trans = trans;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getRunParams() {
		return runParams;
	}

	public void setRunParams(String runParams) {
		this.runParams = runParams;
	}

	public String getFile() {
		return file;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "KtrJobInst [rep=" + rep + ",user=" + user + ",dir=" + dir
				+ ",trans=" + trans + ",file=" + file + "]";
	}
}
