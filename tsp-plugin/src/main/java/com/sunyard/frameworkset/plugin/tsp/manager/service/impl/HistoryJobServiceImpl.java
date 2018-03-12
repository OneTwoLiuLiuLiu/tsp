package com.sunyard.frameworkset.plugin.tsp.manager.service.impl;

import com.sunyard.frameworkset.core.service.BaseServiceImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.HistoryJobDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.HistoryJob;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.HistoryJobQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.HistoryJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mhy on 2017/1/3.
 */
@Service
public class HistoryJobServiceImpl extends BaseServiceImpl<HistoryJob,String,HistoryJobQo> implements HistoryJobService {

    @Autowired
    private HistoryJobDao historyJobDao;

    @Override
    public HistoryJob findByPlanInstIdJobIdAndStatus(String planInstId, String jobId, String status) {
        return historyJobDao.findByPlanInstIdJobIdAndStatus(planInstId,jobId,status);
    }
}
