package com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.KtrJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.KtrJobVo;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KtrJobConfigVo extends JobConfigVo {

	private String jobType = "ktr";

	// private String runParams;

	@JobDesc(desc = "资源类型", isNullAble = false)
	private String select;

	@JobDesc(desc = "本地文件路径", isNullAble = false)
	private String file;

	@JobDesc(desc = "资源库", isNullAble = false)
	private String rep;

	@JobDesc(desc = "用户名", isNullAble = false)
	private String user;

	@JobDesc(desc = "密码", isNullAble = false)
	private String pass;

	@JobDesc(desc = "转换文件名", isNullAble = false)
	private String trans;

	@JobDesc(desc = "文件路径", isNullAble = false)
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

	public String getTrans () {
		return trans;
	}

	public String getJobType () {
		return jobType;
	}

	public void setJobType (String jobType) {
		this.jobType = jobType;
	}

	public String getDir () {
		return dir;
	}

	public String getSelect () {
		return select;
	}

	public void setSelect (String select) {
		this.select = select;
	}

	// public String getRunParams() {
	// return runParams;
	// }

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

	public void setTrans (String trans) {
		this.trans = trans;
	}

	public void setDir (String dir) {
		this.dir = dir;
	}

	public String getIcon () {
		return "ui-icon-ktr";
	}

	public JobVo getJob () {
		KtrJobVo kj = new KtrJobVo ();
		BeanUtils.copyProperties (this, kj, new String[]{"id"});
		return kj;
	}

	public List<Map<String, Object>> getSubInfoResult () throws Exception {
		List<Map<String, Object>> rsult = new ArrayList<Map<String, Object>> ();
		Class clazz = this.getClass ();
		Field[] fields = clazz.getDeclaredFields ();
		for (Field field : fields) {
			Map<String, Object> map = new HashMap<String, Object> ();
			JobDesc job = field.getAnnotation (JobDesc.class);
			if (job != null) {
				field.setAccessible (true);
				if ("select".equals (field.getName ())) {
					map.put ("text", job.desc ());
					if ("1".equals (field.get (this))) {
						map.put ("value", "数据库资源库");
					} else if ("2".equals (field.get (this))) {
						map.put ("value", "文件资源库");
					} else if ("3".equals (field.get (this))) {
						map.put ("value", "本地文件路径");
					}
				} else {
					map.put ("text", job.desc ());
					if (field.get (this) != null) {
						map.put ("value", field.get (this));
					}
					if ("pass".equalsIgnoreCase (field.getName ())) {
						if (!"".equals (field.get (this))) {
							map.put ("value", "******");
						}
					}
				}
				rsult.add (map);
			}
		}
		return rsult;
	}

	@Override
	public JobConfig getJobConfigByElement (Element element) throws Exception {
		KtrJobConfig kjc;

		// 公有的 8
		String type = element.attribute ("type").getValue ();
		kjc = (KtrJobConfig) ElementToJobConfig.getJobConfigByElement (element, type);
		// 私有的
		String select = element.attribute ("select").getValue ();
		String file = element.attribute ("file").getValue ();
		String rep = element.attribute ("rep").getValue ();
		String user = element.attribute ("user").getValue ();
		String pass = element.attribute ("pass").getValue ();
		String trans = element.attribute ("trans").getValue ();
		String dir = element.attribute ("dir").getValue ();

		kjc.setSelect (select);
		kjc.setFile (file);
		kjc.setRep (rep);
		kjc.setUser (user);
		kjc.setPass (pass);
		kjc.setTrans (trans);
		kjc.setDir (dir);

		//验证参数是否合适
		JobPropertyIsNullAble.jobPropertyIsNullAble (kjc, type);
		return kjc;
	}
}
