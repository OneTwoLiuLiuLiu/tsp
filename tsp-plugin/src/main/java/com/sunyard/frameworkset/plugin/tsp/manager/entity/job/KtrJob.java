package com.sunyard.frameworkset.plugin.tsp.manager.entity.job;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.AddBatchNo;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.KtrJobInst;
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
@DiscriminatorValue("ktr")
public class KtrJob extends Job {

	@Transient
	private String jobType = "ktr";

	@Column
	@JobDesc(desc = "参数")
	private String params;

	@Column(name="kind")
	@JobDesc(desc = "资源类型")
	private String select;

	@Column
	@JobDesc(desc = "文件路径")
	private String file;

	@Column(length = 50)
	@JobDesc(desc = "资源库")
	private String rep;

	@Column(length = 50,name="user_name")
	@JobDesc(desc = "用户名")
	private String user;

	@Column(length = 20)
	@JobDesc(desc = "用户名密码")
	private String pass;

	@Column(length = 50)
	@JobDesc(desc = "转换文件")
	private String trans;

	@Column(length = 100)
	@JobDesc(desc = "转换文件资源库路径")
	private String dir;

	public String getParams() {
		return params;
	}

	public String getFile() {
		return file;
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

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public void setFile(String file) {
		this.file = file;
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

	public String getIcon() {
		return "ui-icon-ktr";
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public List<Map<String, Object>> getSubInfoResult() throws Exception {
		List<Map<String, Object>> rsult = new ArrayList<Map<String, Object>>();
		Class clazz = this.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Map<String, Object> map = new HashMap<String, Object>();
			JobDesc job = field.getAnnotation(JobDesc.class);
			if (job != null) {
				field.setAccessible(true);
				if ("ignoreErr".equals(field.getName())) {
					map.put("text", job.desc());
					if ("1".equals(field.get(this))) {
						map.put("value", "数据库资源库");
					} else if ("2".equals(field.get(this))) {
						map.put("value", "文件资源库");
					} else if ("3".equals(field.get(this))) {
						map.put("value", "本地文件路径");
					}
				} else {
					map.put("text", job.desc());
					map.put("value", field.get(this));
				}
				rsult.add(map);
			}
		}
		return rsult;
	}

	@Override
	public JobInst getJobInst(PlanInstance planInstance) {
		//获取计划实例的id和批字号
		String planInstId=planInstance.getId();
		String batchNo=planInstance.getBatchNo();
		//创建ktr作业实例
		KtrJobInst bji = new KtrJobInst();
		BeanUtils.copyProperties (this, bji);
		bji.setPlanInstId(planInstId);
		bji.setJobId(this.getId());
	    String timeFormat=this.getTimeFormat();
		AddBatchNo.isNeedBatchNo (batchNo, bji, timeFormat, planInstance);
		return bji;
	}
}
