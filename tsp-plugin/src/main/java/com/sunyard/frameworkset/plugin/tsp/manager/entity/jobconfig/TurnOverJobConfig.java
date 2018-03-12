package com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig;

import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.JarJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.TurnOverJob;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.ElementToJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.JobPropertyIsNullAble;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2017/1/24 0024 上午 10:23
 * version $Id: TurnOverJobConfig.java, v 0.1 Exp $
 */
@Entity
@DiscriminatorValue("turnover")
public class TurnOverJobConfig extends JobConfig {
    static {
        register ("turnover", TurnOverJobConfig.class);
    }
    @Transient
    private String jobType = "turnover";

    public String getJobType() {
        return jobType;
    }
    public Job getJob () {
        TurnOverJob jj = new TurnOverJob ();
        BeanUtils.copyProperties (this, jj, new String[]{"id"});
        return jj;
    }
    @Override
    public JobConfig getJobConfigByElement (Element element) throws Exception {
        StoreProJobConfig jobConfig = new StoreProJobConfig ();

        //公有的  7
        String type = element.attribute ("type").getValue ();
        jobConfig = (StoreProJobConfig) ElementToJobConfig.getJobConfigByElement (element, type);
        return jobConfig;
    }

}
