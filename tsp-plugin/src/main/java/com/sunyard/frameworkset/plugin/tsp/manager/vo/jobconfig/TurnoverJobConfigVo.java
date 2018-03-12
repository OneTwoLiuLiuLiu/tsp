package com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JarJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.TurnOverJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JarJobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.JobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.TurnoverJobVo;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2017/1/24 0024 下午 2:37
 * version $Id: TurnoverConfigVo.java, v 0.1 Exp $
 */
public class TurnoverJobConfigVo extends JobConfigVo{
    private String jobType = "turnover";



    public String getIcon () {
        return "ui-icon-java";
    }

    public String getJobType () {
        return jobType;
    }

    public JobVo getJob () {
        TurnoverJobVo jj = new TurnoverJobVo();
        BeanUtils.copyProperties (this, jj, new String[]{"id"});
        return jj;
    }

    public List<Map<String, Object>> getSubInfoResult () throws Exception {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        return lists;
    }

    @Override
    public JobConfig getJobConfigByElement (Element element) throws Exception {
        TurnOverJobConfig jobConfig;

        //公有的  8
        String type = element.attribute ("type").getValue ();
        jobConfig = (TurnOverJobConfig) ElementToJobConfig.getJobConfigByElement (element, type);
        return jobConfig;
    }

}
