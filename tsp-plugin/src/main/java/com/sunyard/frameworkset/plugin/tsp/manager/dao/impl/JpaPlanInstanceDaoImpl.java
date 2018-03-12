package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.core.dao.QueryCondition;
import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.PlanInstanceDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanMonitor;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunningJob;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanInstanceQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanMonitorVo;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserVo;
import com.sunyard.frameworkset.util.pages.PageList;
import com.sunyard.frameworkset.util.pages.PagingTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mhy on 2016/12/30.
 */
@Repository
public class JpaPlanInstanceDaoImpl extends JpaBaseDaoImpl<PlanInstance,String,PlanInstanceQo> implements PlanInstanceDao{
    @Override
    public PlanInstance getPlanInstTimeAndPlanId(String planId) {
        return this.findBySingle("From PlanInstance where START_TIME=(select min(START_TIME) from TSP_PLAN_INSTANCE WHERE planId="+planId+") And PLAN_ID=planId",planId);
    }

    /**
     * 根据planMonitorQo分页查找组装planMonitorVo
     * @param planMonitorQo
     * @return
     */
    @Override
    public PageList<PlanMonitorVo> queryPlanMonitors(PlanMonitorQo planMonitorQo) {
        String planName = planMonitorQo.getPname();
        String startTimeOne = planMonitorQo.getStartTimeBelow();
        String startTimeTwo = planMonitorQo.getStartTimeTop();
        String endTimeOne = planMonitorQo.getEndTimeBelow();
        String endTimeTwo = planMonitorQo.getEndTimeTop();
        String startBatchNo = planMonitorQo.getStartBatchNo();
        String endBatchNo = planMonitorQo.getEndBatchNo();
        String status = planMonitorQo.getStatus();
        StringBuilder s = new StringBuilder(
                "select pi.ID as PLAN_INST_ID,tp.NAME as PNAME,pi.BATCH_NO,pi.ID,pi.END_USER,(case  when p.JobAccount is null then  0 else p.JobAccount end) as JobAccount ,((case  when p.JobAccount is null then  0 else p.JobAccount end)-(case  when r.RunningAccount is null then  0 else r.RunningAccount end)-(case  when h.DoneAccount is null then  0 else h.DoneAccount end)-(case  when f.FailureAccount is null then  0 else f.FailureAccount end)) as surplus,(case  when r.RunningAccount is null then  0 else r.RunningAccount end) as RunningAccount,(case  when h.DoneAccount is null then  0 else h.DoneAccount end) as DoneAccount,(case  when f.FailureAccount is null then  0 else f.FailureAccount end) as FailureAccount, pi.STATUS,pi.END_TIME,pi.START_TIME from TSP_PLAN_INSTANCE pi left join TSP_PLAN tp on tp.ID = pi.PLAN_ID left join (select j.PLAN_ID as PID,count(j.ID) as JobAccount from TSP_JOB j  group by j.PLAN_ID) p on p.PID = pi.PLAN_ID left join (select r.PLAN_INST_ID, count(r.STATUS) as RunningAccount from TSP_RUNNING_JOB r where  r.STATUS=");
        String str = s.toString();
        str += "'" + RunningJob.RUNNING + "'";
        StringBuilder sq = new StringBuilder(str)
                .append("group by r.PLAN_INST_ID) r on r.PLAN_INST_ID=pi.Id"
                        + " left join (select h.PLAN_INST_ID, count(h.STATUS) as DoneAccount from TSP_HISTORY_JOB h where  h.STATUS=");
        String sqtr = sq.toString();
        sqtr += "'" + RunningJob.COMPLE + "'";
        StringBuilder sql = new StringBuilder(sqtr)
                .append("group by h.PLAN_INST_ID) h on h.PLAN_INST_ID=pi.Id"
                        + " left join (select f.PLAN_INST_ID, count(f.STATUS) as FailureAccount from TSP_Running_JOB f where  f.STATUS=");
        String sqltr = sql.toString();
        sqltr += "'" + RunningJob.RUNERRER + "'";
        StringBuilder sqlfinal = new StringBuilder(sqltr)
                .append(" group by f.PLAN_INST_ID) f on f.PLAN_INST_ID=pi.Id ");
        String sqlfinaltr = sqlfinal.toString();

        String where = " where 1=1 ";
        if (StringUtils.isNotBlank(planName)) {
            where += " and tp.NAME like ?";
        }
        if (StringUtils.isNotBlank(startTimeOne)) {
            where += "and pi.START_TIME>= ?";
        }
        if (StringUtils.isNotBlank(startTimeTwo)) {
            where += "and pi.START_TIME<= ?" ;
        }
        if (StringUtils.isNotBlank(endTimeOne)) {
            where += "and pi.END_TIME>= ?";
        }
        if (StringUtils.isNotBlank(endTimeTwo)) {
            where += "and pi.END_TIME<= ?";
        }
        if (StringUtils.isNotBlank(startBatchNo)) {
            where += "and pi.BATCH_NO>= ?" ;
        }
        if (StringUtils.isNotBlank(endBatchNo)) {
            where += "and pi.BATCH_NO<= ?";
        }
        if (StringUtils.isNotBlank(status)) {
            where += "and pi.STATUS= ?";
        }
        sqlfinaltr += where;
        Query query = this.getEntityManager().createNativeQuery(sqlfinaltr).setFirstResult((planMonitorQo.getPageNum()-1)*planMonitorQo.getPageSize()).setMaxResults(planMonitorQo.getPageSize());
        PageList<PlanMonitorVo> planMonitorVos = new PageList<PlanMonitorVo>();
        List objecArraytList = query.getResultList();
        String countSelect = "select count(*) ";
        String countSql = countSelect+sqlfinaltr.substring(sqlfinaltr.indexOf("from"),sqlfinaltr.length());
        Integer count = (Integer)this.getEntityManager().createNativeQuery(countSql).getResultList().get(0);
        for(int i=0;i<objecArraytList.size();i++) {
            Object[] obj = (Object[]) objecArraytList.get(i);
            PlanMonitorVo planMonitorvo = new PlanMonitorVo();
            planMonitorvo.setPlanInstId((String) obj[0]);
            planMonitorvo.setPname((String) obj[1]);
            planMonitorvo.setBatchNo((String) obj[2]);
            planMonitorvo.setId((String) obj[3]);
            planMonitorvo.setEndUser((String) obj[4]);
            planMonitorvo.setJobaccount((Integer) obj[5]);
            planMonitorvo.setSurplus((Integer) obj[6]);
            planMonitorvo.setRunningaccount((Integer) obj[7]);
            planMonitorvo.setDoneaccount((Integer) obj[8]);
            planMonitorvo.setFailureaccount((Integer) obj[9]);
            planMonitorvo.setStatus((String) obj[10]);
            planMonitorvo.setEndTime((String) obj[11]);
            planMonitorvo.setStartTime((String) obj[12]);
            planMonitorVos.add(planMonitorvo);
        }
        PagingTools pagingTools = createPagingTools(planMonitorQo,count);
        planMonitorVos.setPageTools (pagingTools);

        return planMonitorVos;
    }

    @Override
    public int endPlanInstance(String planInstId,UserVo userVo) {
        String endTime = DateUtil.dataToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
        QueryCondition queryCondition = new QueryCondition("update PlanInstance obj ");
        queryCondition.addSet("obj.status=:end,","end",PlanInstance.END);
        queryCondition.addSet("obj.endUser=:endUser,","endUser", userVo.getName());
        queryCondition.addSet("obj.endUserCode =:endUserCode, ","endUserCode",userVo.getCode());
        queryCondition.addSet("obj.endTime=:endTime","endTime",endTime);
        queryCondition.add("And obj.id=:id", "id", planInstId);
        queryCondition.add("And obj.status!=:ended","ended",PlanInstance.END);
        int result = bulkUpdate ( queryCondition );
        return result;
    }

    @Override
    public int updatePlanInstance(String planInstId,String status) {
        int result=this.executeUpdate("update PlanInstance as obj set obj.status = ? where obj.id = ?", status.trim(), planInstId);
        return result;
    }

    @Override
    public PlanInstance findPlanInstance(String planInstId) {
        return this.findById(planInstId);
    }

    @Override
    public int updateStatusAndTime(PlanInstance planInstance) {
        int result=this.executeUpdate("update PlanInstance as obj set obj.status=? , obj.startTime=? where obj.id = ?",planInstance.getStatus() ,planInstance.getStartTime(),planInstance.getId());
        return result;
    }


}
