package com.sunyard.frameworkset.plugin.tsp.manager.entity.job;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2017/1/24 0024 上午 10:20
 * version $Id: TurnOverJob.java, v 0.1 Exp $
 */
@Entity
@DiscriminatorValue("turnover")
public class TurnOverJob extends Job {
    @Transient
    private String jobType="turnover";

    @Override
    public String getJobType() {
        return jobType;
    }

    public String getIcon(){
        return "ui-icon-serial";
    }


    @Override
    public List<Map<String, Object>> getSubInfoResult() throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        return result;
    }
}
