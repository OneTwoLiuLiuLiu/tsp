package com.sunyard.frameworkset.plugin.tsp.manager.entity.job;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Write class comments here
 * <p/>
 * User: dinghao
 * Date: 2017/2/8 14:28
 * version $Id: TaskJob.java, v 0.1  14:28 Exp $
 */
@Entity
@DiscriminatorValue("task")
public class TaskJob extends Job{
    @Transient
    protected String jobType="task";

    public String getIcon(){
        return "ui-icon-task";
    }

    public String getJobType() {
        return jobType;
    }

    @Override
    public List<Map<String, Object>> getSubInfoResult() throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        return result;
    }
}
