package com.sunyard.frameworkset.plugin.tsp.manager.vo.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Write class comments here
 * <p/>
 * 任务节点
 * User: dinghao
 * Date: 2017/2/8 14:26
 * version $Id: TaskJobVo.java, v 0.1  14:26 Exp $
 */
public class TaskJobVo extends JobVo{

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
