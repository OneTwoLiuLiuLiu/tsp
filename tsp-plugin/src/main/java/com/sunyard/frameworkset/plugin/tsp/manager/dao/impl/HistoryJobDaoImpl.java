package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.HistoryJobDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.HistoryJob;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.HistoryJobQo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mhy on 2016/12/30.
 */
@Repository
public class HistoryJobDaoImpl extends JpaBaseDaoImpl<HistoryJob,String,HistoryJobQo> implements HistoryJobDao {
    @Override
    public HistoryJob findByPlanInstIdJobIdAndStatus(String planInstId, String jobId, String status) {
        return this.findBySingle("from HistoryJob where planInstId=?  and jobId=? and status=?",planInstId,jobId,status);
    }
}
