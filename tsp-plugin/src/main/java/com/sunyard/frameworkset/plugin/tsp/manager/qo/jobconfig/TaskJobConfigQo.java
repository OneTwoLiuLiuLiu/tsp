package com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig;

/**
 * Write class comments here
 * <p/>
 * User: dinghao
 * Date: 2017/2/8 14:53
 * version $Id: TaskJobConfigQo.java, v 0.1  14:53 Exp $
 */
public class TaskJobConfigQo extends JobConfigQo{
    public static final String JOBNAME = "任务";

    private String jobType = "task";

    public String getIcon () {
        return "ui-icon-task";
    }

    public String getJobType () {
        return jobType;
    }
}
