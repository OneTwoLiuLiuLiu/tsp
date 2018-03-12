package com.sunyard.frameworkset.plugin.tsp.manager.service;

import com.sunyard.frameworkset.core.service.BaseService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.HistoryJob;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.HistoryJobQo;

/**
 * Created by mhy on 2017/1/3.
 */
public interface HistoryJobService extends BaseService<HistoryJob,String,HistoryJobQo> {

    /**
     * 根据 jobId,planInstId,status查找
     * @param jobId
     * @param planInstId
     * @param status
     * @return
     */
    HistoryJob findByPlanInstIdJobIdAndStatus(String planInstId, String jobId,String status);
}
