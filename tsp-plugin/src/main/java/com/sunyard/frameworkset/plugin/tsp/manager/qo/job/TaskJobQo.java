package com.sunyard.frameworkset.plugin.tsp.manager.qo.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Write class comments here
 * <p/>
 * User: dinghao
 * Date: 2017/2/8 14:22
 * version $Id: TaskJobQo.java, v 0.1  14:22 Exp $
 */
public class TaskJobQo extends JobQo{

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
