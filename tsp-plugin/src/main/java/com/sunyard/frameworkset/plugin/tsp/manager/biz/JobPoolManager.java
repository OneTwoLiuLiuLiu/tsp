package com.sunyard.frameworkset.plugin.tsp.manager.biz;

import com.sunyard.frameworkset.core.manager.BaseManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobPool;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobPoolQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.JobPoolVo;

import java.util.List;

/**
 * 作业池 manager 接口
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
public interface JobPoolManager extends BaseManager<JobPoolVo, JobPool, String, JobPoolQo> {
    void runJob(JobPool jobPool);
}
