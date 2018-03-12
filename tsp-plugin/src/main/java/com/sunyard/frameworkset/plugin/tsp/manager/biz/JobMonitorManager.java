package com.sunyard.frameworkset.plugin.tsp.manager.biz;

import com.sunyard.frameworkset.core.manager.BaseManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobMonitor;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.ZTreeNode;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.JobMonitorVo;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;

import java.util.List;

/**
 * Created by Misaki on 2016/12/30.
 */
public interface JobMonitorManager extends BaseManager<JobMonitorVo,JobMonitor,String,JobMonitorQo> {
    boolean reRun(JobMonitorQo qo);
    boolean manualPass(String id);
    boolean stopJob(JobMonitorQo qo);
    boolean pauseJob(JobMonitorQo qo);
    boolean continueRun(JobMonitorQo qo);

    /**
     * 获取作业日志
     * @param hostname
     * @param planInstId
     * @param jobId
     * @return
     */
    String getJobLog(String hostname, String planInstId, String jobId);

    List<ZTreeNode> getZTree(PlanConfigQo qo,String id);

    List<ZTreeNode> getZTrees(PlanConfigQo qo, String id);

}
