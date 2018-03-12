package com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import org.dom4j.Element;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JobConfigVo implements Serializable {


	private static Map<String, Class> jobes = new ConcurrentHashMap<String, Class> ();

	private static final long serialVersionUID = 500557664688465383L;

	private String jobType = "base";

	private String id;

	@JobDesc(desc = "名称", isNullAble = false)
	private String name;

	@JobDesc(desc = "运行参数")
	private String runParams;

	@JobDesc(desc = "是否忽略错误", isNullAble = false)
	private String ignoreErr;

	@JobDesc(desc = "重试次数", isNullAble = false)
	private Integer retryCnt = 3;

	@JobDesc(desc = "重试时间间隔", isNullAble = false)
	private Integer retrySec = 60;

	@JobDesc(desc = "创建时间")
	private String createTime;

	@JobDesc(desc = "创建人")
	private String createUser;

	@JobDesc(desc = "修改时间")
	private String modifiedTime;

	@JobDesc(desc = "修改人")
	private String modifiedUser;

	private String modifiedUserCode;

	@JobDesc(desc = "上一节点")
	private String prevJobId;

	@JobDesc(desc = "下一个节点")
	private String nextJobId;

	@JobDesc(desc = "父节点")
	private String parentJobId;

	@JobDesc(desc = "日期格式", isNullAble = false)
	private String timeFormat;

	@JobDesc(desc = "期待运行时间")
	private Integer expectRunTime;

	public JobConfigVo () {
		super ();

	}

	public String getId () {
		return id;
	}

	public void setId (String id) {
		this.id = id;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}


	/**
	 * 作业的类型
	 *
	 * @return
	 */
	public String getJobType () {
		return jobType;
	}


	public void setJobType (String jobType) {
		this.jobType = jobType;
	}


	public String getPrevJobId () {
		return prevJobId;
	}


	public void setPrevJobId (String prevJobId) {
		this.prevJobId = prevJobId;
	}


	public String getNextJobId () {
		return nextJobId;
	}


	public void setNextJobId (String nextJobId) {
		this.nextJobId = nextJobId;
	}


	public String getParentJobId () {
		return parentJobId;
	}


	public void setParentJobId (String parentJobId) {
		this.parentJobId = parentJobId;
	}

	public String getRunParams () {
		return runParams;
	}

	public void setRunParams (String runParams) {
		this.runParams = runParams;
	}

	public String getIgnoreErr () {
		return ignoreErr;
	}

	public void setIgnoreErr (String ignoreErr) {
		this.ignoreErr = ignoreErr;
	}

	public Integer getRetryCnt () {
		return retryCnt;
	}

	public void setRetryCnt (Integer retryCnt) {
		this.retryCnt = retryCnt;
	}

	public Integer getRetrySec () {
		return retrySec;
	}

	public void setRetrySec (Integer retrySec) {
		this.retrySec = retrySec;
	}

	public String getCreateTime () {
		return createTime;
	}

	public void setCreateTime (String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser () {
		return createUser;
	}

	public void setCreateUser (String createUser) {
		this.createUser = createUser;
	}

	public String getModifiedTime () {
		return modifiedTime;
	}

	public void setModifiedTime (String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getModifiedUser () {
		return modifiedUser;
	}

	public void setModifiedUser (String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getTimeFormat () {
		return timeFormat;
	}

	public void setTimeFormat (String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public Integer getExpectRunTime () {
		return expectRunTime;
	}

	public void setExpectRunTime (Integer expectRunTime) {
		this.expectRunTime = expectRunTime;
	}


	/**
	 * 作业的图标
	 *
	 * @return
	 */
	public String getIcon () {
		return null;
	}

	/**
	 * 转换为Job
	 *
	 * @return
	 */
	public JobVo getJob () {
		return null;
	}

	public static void register (String type, Class<? extends JobConfigVo> clazz) {
		Class cl = jobes.get (type);
		if (cl == null) {
			jobes.put (type, clazz);
		}
	}


	public static Class<? extends JobConfigVo> getJobConfigClassByType (String type) {
		Class clazz = jobes.get (type);
		if (clazz == null) {
			throw new RuntimeException ("找不到类型为:" + type + "对应的class类");
		}
		return clazz;
	}


	@Override
	public String toString () {
		return "JobConfigVo [id=" + id + ", name=" + name + ", runParams="
				+ runParams + ", ignoreErr=" + ignoreErr + ", retryCnt="
				+ retryCnt + ", retrySec=" + retrySec + ", createTime="
				+ createTime + ", createUser=" + createUser
				+ ", modifiedTime="
				+ modifiedTime + ", modifiedUser=" + modifiedUser
				+ ", prevJobId="
				+ prevJobId + ", nextJobId=" + nextJobId + ", parentJobId="
				+ parentJobId + ", timeFormat=" + timeFormat
				+ ", expectRunTime=" + expectRunTime +
				"]";
	}

	public List<Map<String, Object>> getSubInfoResult () throws Exception {
		return new ArrayList<Map<String, Object>> ();
	}


	public List<Map<String, Object>> getInfoReuslt () throws Exception {
		List<Map<String, Object>> lists = getSubInfoResult ();
		Class clazz1 = JobConfigVo.class;
		Field[] fields1 = clazz1.getDeclaredFields ();
		for (Field field : fields1) {
			Map<String, Object> map = new HashMap<String, Object> ();
			JobDesc job = field.getAnnotation (JobDesc.class);
			if (job != null) {
				field.setAccessible (true);
				if ("ignoreErr".equals (field.getName ())) {
					map.put ("text", job.desc ());
					if ("1".equals (field.get (this))) {
						map.put ("value", "是");
					} else if ("2".equals (field.get (this))) {
						map.put ("value", "否");
					}
				} else {
					map.put ("text", job.desc ());
					map.put ("value", field.get (this));
				}
				lists.add (map);
			}
		}
		return lists;
	}


	public JobConfig getJobConfigByElement (Element element) throws Exception {
		return null;
	}

	public String getModifiedUserCode () {
		return modifiedUserCode;
	}

	public void setModifiedUserCode (String modifiedUserCode) {
		this.modifiedUserCode = modifiedUserCode;
	}
}
