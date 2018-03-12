package com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst;

public class KjbJobInst extends JobInst {
	private String type = "kjb";

	private String file;

	private String runParams;

	private String rep;

	private String user;

	private String pass;

	private String jobs;

	private String dir;

	public KjbJobInst() {

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

	public String getJobs() {
		return jobs;
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

	public void setJobs(String jobs) {
		this.jobs = jobs;
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

		return "KjbJobInst [rep=" + rep + ",user=" + user + ",dir=" + dir
				+ ",job=" + jobs + ",filePath=" + file + " ,params:"
				+ runParams + "]";
	}
}
