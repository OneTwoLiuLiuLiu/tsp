package com.sunyard.frameworkset.plugin.tsp.manager.dao;

import com.sunyard.frameworkset.core.dao.BaseDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.HistoryJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunningJob;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.HistoryJobQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.RunningJobQo;

import java.util.List;

/**
 * Created by mhy on 2016/12/30.
 */
public interface HistoryJobDao extends BaseDao<HistoryJob,String,HistoryJobQo>{
    /**
     * 根据 jobId,planInstId,status查找
     * @param planInstId
     * @param jobId
     * @param status
     * @return
     */
    HistoryJob findByPlanInstIdJobIdAndStatus(String planInstId, String jobId,String status);
}
