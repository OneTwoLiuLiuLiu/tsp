package com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.StoreProJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.StoreProJobVo;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreProJobConfigVo extends JobConfigVo {

	private String jobType = "storepro";

	@JobDesc(desc = "存储过程名字", isNullAble = false)
	private String procedureName;

	@JobDesc(desc = "数据库类型", isNullAble = false)
	private String dataBaseType;

	@JobDesc(desc = "数据库用户名", isNullAble = false)
	private String dataBaseUser;

	@JobDesc(desc = "数据库密码", isNullAble = false)
	private String dataBasePwd;

	@JobDesc(desc = "数据库连接Ip", isNullAble = false)
	private String dataBaseIp;

	@JobDesc(desc = "数据库连接端口号", isNullAble = false)
	private String dataBasePort;

	@JobDesc(desc = "数据库名字", isNullAble = false)
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

	public JobVo getJob () {
		StoreProJobVo spj = new StoreProJobVo ();
		BeanUtils.copyProperties (this, spj, new String[]{"id"});
		return spj;
	}

	@Override
	public List<Map<String, Object>> getSubInfoResult () throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>> ();
		Class clazz = this.getClass ();
		Field[] fields = clazz.getDeclaredFields ();
		for (Field field : fields) {
			Map<String, Object> map = new HashMap<String, Object> ();
			JobDesc job = field.getAnnotation (JobDesc.class);
			if (job != null) {
				field.setAccessible (true);
				if ("dataBaseType".equals (field.getName ())) {
					map.put ("text", job.desc ());
					if ("0".equals (field.get (this))) {
						map.put ("value", "oracle");
					} else if ("1".equals (field.get (this))) {
						map.put ("value", "db2");
					} else if ("2".equals (field.get (this))) {
						map.put ("value", "mysql");
					} else if ("3".equals (field.get (this))) {
						map.put ("value", "postgresql");
					}
				} else if ("dataBasePwd".equals (field.getName ())) {
					map.put ("text", job.desc ());
					map.put ("value", "******");
				} else {
					map.put ("text", job.desc ());
					map.put ("value", field.get (this));
				}
				result.add (map);
			}
		}
		return result;
	}

	@Override
	public JobConfig getJobConfigByElement (Element element) throws Exception {
		StoreProJobConfig jobConfig;

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
