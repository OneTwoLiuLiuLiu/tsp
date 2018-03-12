package com.sunyard.frameworkset.plugin.tsp.manager.entity.job;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "tsp_job")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("base")
public abstract class Job implements Serializable {

    private static final long serialVersionUID = 500557664688465383L;

    /**
     * 忽略错误
     */
    public static final String IGNORERR = "1";

    /**
     * 不忽略错误
     */
    public static final String NOTIGNORERR = "2";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @Column(name = "name", length = 50)
    @JobDesc(desc = "名称")
    private String name;

    @Column(name = "run_params", length = 200)
    @JobDesc(desc = "运行参数")
    private String runParams;

    @Column(name = "ignore_err", length = 1)
    @JobDesc(desc = "是否忽略错误")
    private String ignoreErr;

    @Column(name = "retry_cnt")
    @JobDesc(desc = "重试次数")
    private Integer retryCnt = 3;

    @Column(name = "retry_sec")
    @JobDesc(desc = "重试时间间隔")
    private Integer retrySec = 60;

    @Column(name = "create_time", length = 20)
    @JobDesc(desc = "创建时间")
    private String createTime;

    @Column(name = "create_User_Code", length = 20)
    @JobDesc(desc = "创建人代码")
    private String CreateUserCode;

    @Column(name = "create_user", length = 20)
    @JobDesc(desc = "创建人")
    private String createUser;

    @Column(name = "prev_job_id")
    @JobDesc(desc = "上一节点")
    private String prevJobId;

    @Column(name = "next_job_id")
    @JobDesc(desc = "下一个节点")
    private String nextJobId;

    @Column(name = "parent_job_id")
    @JobDesc(desc = "父节点")
    private String parentJobId;

    @Column(name = "time_format", length = 20)
    @JobDesc(desc = "日期格式")
    private String timeFormat;

    @Column(name = "expect_run_time")
    private Integer expectRunTime;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_config_id")
    private JobConfig jobConfig;

    public JobConfig getJobConfig() {
        return jobConfig;
    }

    public void setJobConfig(JobConfig jobConfig) {
        this.jobConfig = jobConfig;
    }

    public Job() {
        super();

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
        Class clazz1 = Job.class;
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
        return "Job [jobType=" + getJobType() + ", id=" + id + ", name=" + name
                + "]";
    }
}
