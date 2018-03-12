package com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.KjbJob;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("kjb")
public class KjbJobConfig extends JobConfig {

	static {
		register ("kjb", KjbJobConfig.class);
	}

	@Transient
	private String jobType = "kjb";

	// @Column(name = "run_params", length = 200)
	// @JobDesc(desc = "运行参数")
	// private String runParams;

	@Column(length = 100, name = "kind")
	private String select;

	@Column
	private String file;

	@Column(length = 50)
	private String rep;

	@Column(length = 50, name = "user_name")
	private String user;

	@Column(length = 20)
	private String pass;

	@Column(length = 50)
	private String jobs;

	@Column(length = 100)
	private String dir;

	public String getFile () {
		return file;
	}

	public String getRep () {
		return rep;
	}

	public String getUser () {
		return user;
	}

	public String getPass () {
		return pass;
	}

	public String getJobs () {
		return jobs;
	}

	public String getDir () {
		return dir;
	}

	public String getSelect () {
		return select;
	}

	public String getJobType () {
		return jobType;
	}

	public void setJobType (String jobType) {
		this.jobType = jobType;
	}

	public void setJobs (String jobs) {
		this.jobs = jobs;
	}

	public void setSelect (String select) {
		this.select = select;
	}

	// public String getRunParams() {
	// return runParams;
	// }
	//
	// public void setRunParams(String runParams) {
	// this.runParams = runParams;
	// }

	public void setFile (String file) {
		this.file = file;
	}

	public void setRep (String rep) {
		this.rep = rep;
	}

	public void setUser (String user) {
		this.user = user;
	}

	public void setPass (String pass) {
		this.pass = pass;
	}

	public void setJob (String jobs) {
		this.jobs = jobs;
	}

	public void setDir (String dir) {
		this.dir = dir;
	}

	public String getIcon () {
		return "ui-icon-kjb";
	}

	public Job getJob () {
		KjbJob kj = new KjbJob ();
		BeanUtils.copyProperties (this, kj, new String[]{"id"});
		return kj;
	}

	@Override
	public JobConfig getJobConfigByElement (Element element) throws Exception {
		KjbJobConfig kjc = new KjbJobConfig ();

		// 公有的 8
		String type = element.attribute ("type").getValue ();
		kjc = (KjbJobConfig) ElementToJobConfig.getJobConfigByElement (element, type);

		// 私有的
		String select = element.attribute ("select").getValue ();
		String file = element.attribute ("file").getValue ();
		String rep = element.attribute ("rep").getValue ();
		String user = element.attribute ("user").getValue ();
		String pass = element.attribute ("pass").getValue ();
		String jobs = element.attribute ("jobs").getValue ();
		String dir = element.attribute ("dir").getValue ();

		kjc.setSelect (select);
		kjc.setFile (file);
		kjc.setRep (rep);
		kjc.setUser (user);
		kjc.setPass (pass);
		kjc.setJobs (jobs);
		kjc.setDir (dir);

		//验证参数是否合适
		JobPropertyIsNullAble.jobPropertyIsNullAble (kjc, type);
		return kjc;
	}
}
