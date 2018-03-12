package com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.TaskJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.TaskJobVo;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Write class comments here
 * <p/>
 * User: dinghao
 * Date: 2017/2/8 14:46
 * version $Id: TaskJobConfigVo.java, v 0.1  14:46 Exp $
 */
public class TaskJobConfigVo extends JobConfigVo{
    public static final String JOBNAME = "任务";

    private String jobType = "task";

    public String getIcon () {
        return "ui-icon-task";
    }

    public String getJobType () {
        return jobType;
    }

    @Override
    public JobVo getJob () {
        TaskJobVo pj = new TaskJobVo ();
        BeanUtils.copyProperties(this, pj, new String[]{"id"});
        return pj;
    }

    @Override
    public List<Map<String, Object>> getSubInfoResult () throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        return result;
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
