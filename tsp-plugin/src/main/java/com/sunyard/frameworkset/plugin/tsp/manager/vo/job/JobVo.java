package com.sunyard.frameworkset.plugin.tsp.manager.vo.job;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class JobVo implements Serializable {

    private static final long serialVersionUID = 500557664688465383L;

    /**
     * 忽略错误
     */
    public static final String IGNORERR = "1";

    /**
     * 不忽略错误
     */
    public static final String NOTIGNORERR = "2";

    private String id;

    private String name;

    private String runParams;

    private String ignoreErr;

    private Integer retryCnt = 3;

    private Integer retrySec = 60;

    private String createTime;

    private String CreateUserCode;

    private String createUser;

    private String prevJobId;

    private String nextJobId;

    private String parentJobId;

    private String timeFormat;

    private Integer expectRunTime;

    private Plan plan;

    private JobConfig jobConfig;

    public JobVo() {
        super();

    }

    public JobConfig getJobConfig() {
        return jobConfig;
    }

    public void setJobConfig(JobConfig jobConfig) {
        this.jobConfig = jobConfig;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract String getJobType();

    public String getRunParams() {
        return runParams;
    }

    public void setRunParams(String runParams) {
        this.runParams = runParams;
    }

    public String getIgnoreErr() {
        return ignoreErr;
    }

    public void setIgnoreErr(String ignoreErr) {
        this.ignoreErr = ignoreErr;
    }

    public Integer getRetryCnt() {
        return retryCnt;
    }

    public void setRetryCnt(Integer retryCnt) {
        this.retryCnt = retryCnt;
    }

    public Integer getRetrySec() {
        return retrySec;
    }

    public void setRetrySec(Integer retrySec) {
        this.retrySec = retrySec;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserCode() {
        return CreateUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        CreateUserCode = createUserCode;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getPrevJobId() {
        return prevJobId;
    }

    public void setPrevJobId(String prevJobId) {
        this.prevJobId = prevJobId;
    }

    public String getNextJobId() {
        return nextJobId;
    }

    public void setNextJobId(String nextJobId) {
        this.nextJobId = nextJobId;
    }

    public String getParentJobId() {
        return parentJobId;
    }

    public void setParentJobId(String parentJobId) {
        this.parentJobId = parentJobId;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public Integer getExpectRunTime() {
        return expectRunTime;
    }

    public void setExpectRunTime(Integer expectRunTime) {
        this.expectRunTime = expectRunTime;
    }

    public JobInst getJobInst(PlanInstance planInstance) {
        return null;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    /**
     * 获取图标
     *
     * @return
     */
    public abstract String getIcon();

    /**
     * 获取当前子类信息
     *
     * @return
     */
    public abstract List<Map<String, Object>> getSubInfoResult()
            throws Exception;

    public List<Map<String, Object>> getInfoReuslt() throws Exception {
        List<Map<String, Object>> lists = getSubInfoResult();
        Class clazz1 = JobVo.class;
        Field[] fields1 = clazz1.getDeclaredFields();
        for (Field field : fields1) {
            Map<String, Object> map = new HashMap<String, Object>();
            JobDesc jobDesc = field.getAnnotation(JobDesc.class);
            if (jobDesc != null) {
                field.setAccessible(true);
                if ("ignoreErr".equals(field.getName())) {
                    map.put("text", jobDesc.desc());
                    if ("1".equals(field.get(this))) {
                        map.put("value", "是");
                    } else if ("2".equals(field.get(this))) {
                        map.put("value", "否");
                    }
                } else {
                    map.put("text", jobDesc.desc());
                    map.put("value", field.get(this));
                }
                lists.add(map);
            }
        }
        return lists;
    }

    @Override
    public String toString() {
        return "JobVo [jobType=" + getJobType() + ", id=" + id + ", name=" + name
                + "]";
    }
}
