package com.sunyard.frameworkset.plugin.tsp.manager.dao.qct;

import com.sunyard.frameworkset.core.dao.QueryCondition;
import com.sunyard.frameworkset.core.dao.QueryConditionTransfer;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobServerConfigQo;
import com.sunyard.frameworkset.util.StringHelper;
import org.apache.commons.collections.CollectionUtils;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2017/1/12 0012 上午 10:06
 * version $Id: JobMonitorQct.java, v 0.1 Exp $
 */
public class JobMonitorQct  extends QueryConditionTransfer<JobMonitorQo> {

    @Override
    public  void transNameQuery (JobMonitorQo qo, QueryCondition condition) {

        if (qo != null) {
            //查询条件
            if (qo.getpName() != null && !StringHelper.isEmpty(qo.getpName()) &&(qo.getJobType()=="root"||StringHelper.isEmpty(qo.getJobType()))) {
                condition.add(" And obj.pName like:pname", "pname", qo.getBlurKeyword(qo.getpName()));
            }
            if(CollectionUtils.isNotEmpty(qo.getJobIds())){
                condition.append("And obj.jobId in (");
                for(int i = 0 ; i<qo.getJobIds().size() ; i++){
                    condition.add(":jobId_" + i + ", ", "jobId_" + i, qo.getJobIds().get(i));

                }
                condition.append(" 'none') ");
            }
            //任务开始起始时间
            if (qo.getStartBeforeTime() != null && !StringHelper.isEmpty(qo.getStartBeforeTime())) {
                condition.add(" And obj.startTime >=:startBeforeTime", "startBeforeTime", qo.getStartBeforeTime().trim());
            }
            //任务结束起始时间
            if (qo.getEndBeforeTime() != null && !StringHelper.isEmpty(qo.getEndBeforeTime())) {
                condition.add(" And obj.endTime >=:endBeforeTime", "endBeforeTime", qo.getEndBeforeTime().trim());
            }
            //任务开始终止时间
            if (qo.getStartEndTime() != null && !StringHelper.isEmpty(qo.getStartEndTime())) {
                condition.add(" And obj.startTime <=:startEndTime", "startEndTime", qo.getStartEndTime().trim());
            }
            //任务结束终止时间
            if (qo.getEndEndTime() != null && !StringHelper.isEmpty(qo.getEndEndTime())) {
                condition.add(" And obj.endTime <=:endEndTime", "endEndTime", qo.getEndEndTime().trim());
            }
            if (qo.getStatus() != null && !StringHelper.isEmpty(qo.getStatus())) {
                condition.add(" And obj.status=:status", "status", qo.getStatus());
            }
            if(qo.getPlanInstId()!= null && !StringHelper.isEmpty(qo.getPlanInstId())){
                condition.add( "And obj.planInstId=:planInstId","planInstId",qo.getPlanInstId());
            }
        }
    }

    @Override
    public  void transQuery(JobMonitorQo qo, QueryCondition condition) {
        super.transQuery(qo, condition);
    }
}
