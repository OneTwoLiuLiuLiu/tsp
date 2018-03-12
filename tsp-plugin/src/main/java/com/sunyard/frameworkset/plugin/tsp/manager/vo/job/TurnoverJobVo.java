package com.sunyard.frameworkset.plugin.tsp.manager.vo.job;

import com.sunyard.frameworkset.plugin.tsp.manager.core.annotation.JobDesc;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.ExeJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.TurnOverJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.AddBatchNo;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig.JobConfigVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig.PlanJobConfigVo;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.TurnOverJobInst;
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
 * Date: 2017/1/24 0024 上午 8:54
 * version $Id: turnover.java, v 0.1 Exp $
 */
public class TurnoverJobVo extends JobVo {
    private String jobType = "turnover";
    public String getIcon() {
        return "ui-icon-chu";
    }

    public String getJobType() {
        return jobType;
    }


    public List<Map<String, Object>> getSubInfoResult () throws Exception {
        List<Map<String, Object>> rsult = new ArrayList<Map<String, Object>> ();
        return rsult;
    }

}
