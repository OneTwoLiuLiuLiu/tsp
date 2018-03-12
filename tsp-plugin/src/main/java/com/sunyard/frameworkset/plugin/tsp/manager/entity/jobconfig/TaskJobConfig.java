package com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.ParallelJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.TaskJob;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Write class comments here
 * <p/>
 * User: dinghao
 * Date: 2017/2/8 14:42
 * version $Id: TaskJobConfig.java, v 0.1  14:42 Exp $
 */

@Entity
@DiscriminatorValue("task")
public class TaskJobConfig extends JobConfig{
    public static final String JOBNAME = "任务";

    static {
        register ("task", TaskJobConfig.class);
    }

    @Transient
    private String jobType = "task";

    public String getIcon () {
        return "ui-icon-task";
    }

    public String getJobType () {
        return jobType;
    }

    @Override
    public Job getJob () {
        TaskJob pj = new TaskJob ();
        BeanUtils.copyProperties(this, pj, new String[]{"id"});
        return pj;
    }


    @Override
    public JobConfig getJobConfigByElement (Element element) {
        TaskJobConfig taskJobConfig = new TaskJobConfig ();
        String name = element.attribute ("name").getValue ();
        String createTime = DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss");
        taskJobConfig.setName (name);
        taskJobConfig.setCreateTime (createTime);
        return taskJobConfig;
    }
}
