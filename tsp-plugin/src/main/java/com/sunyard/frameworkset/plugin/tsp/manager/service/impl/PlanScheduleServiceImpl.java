package com.sunyard.frameworkset.plugin.tsp.manager.service.impl;


import com.sunyard.frameworkset.plugin.tsp.manager.entity.*;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.JarJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.ParallelJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.job.SerialJob;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.TspPluginMsgEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import com.sunyard.frameworkset.plugin.tsp.manager.service.*;
import com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.job.FdapStartJob;
import com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.QuartzManager;
import com.sunyard.frameworkset.plugin.tsp.manager.core.quartz.job.StartJob;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Transactional
@Service
public class PlanScheduleServiceImpl implements PlanScheduleService {

	private static final Logger logger = LoggerFactory.getLogger(PlanScheduleServiceImpl.class);
	
	private static final String ERRORCODE="PAPP001";


	@Autowired
	private JobService jobService;

	@Autowired
	private PlanInstanceService planInstanceService;

	@Autowired
	private PlanConfigService planConfigService;

	@Autowired(required = false)
	private QuartzManager quartzManager;

	@Autowired
	private WaittingJobService waittingJobService;


	@Autowired
	private RunningJobService runningJobService;

	@Autowired
	private PlanInstParamService planInstParamService;


	@Autowired
	private HistoryJobService historyJobService;

	@Autowired
	private PlanService planService;

	@Autowired
	private PlanMonitorService planMonitorService;


	private JobServerConfigService jobServerConfigService;
	
	private Lock lock = new ReentrantLock();


	public List<Job> getNextJobs(String nextjobId) throws TaskSchedulingPlatformException {
		List<Job> result = new ArrayList<Job>();
		//Job job =(Job)jpaCommonDaoImpl.findByHqlSingle("from Job where id=?", nextjobId);
		Job job =(Job)jobService.findById(nextjobId);
		if(job != null){			
			if(job instanceof SerialJob|| job instanceof ParallelJob){//如果是串行节点,获取该节点下面的第一个节点，如果是并行节点获取下面的所有任务
				result.addAll(this.getJobByParentJob(job));
			}else{
				result.add(job);
			}
		}
		return result;
	}

	
	private List<Job> getJobByParentJob(Job job){
		List<Job> result = new ArrayList<Job>();
		if(job instanceof SerialJob){
			//Job subjob =(Job)jpaCommonDaoImpl.findByHqlSingle("from Job where prevJobId is null and parentJobId=?", job.getId());
			Job subjob =jobService.findByParentJobIdAndPrevJobIdIsNull(job.getId());
			if(subjob instanceof SerialJob || subjob instanceof ParallelJob){
				result.addAll(this.getJobByParentJob(subjob));
			}else{
				result.add(subjob);
			}
		}else if(job instanceof ParallelJob){
			//List<Job> subjobs =(List<Job>)jpaCommonDaoImpl.findByHql("from Job where  parentJobId=?", job.getId());
			List<Job> subjobs =jobService.findByParentJobId(job.getId());

			for (Job job2 : subjobs) {
				if(job2 instanceof SerialJob || job2 instanceof ParallelJob){
					result.addAll(this.getJobByParentJob(job2));
				}else{
					result.add(job2);
				}
			}
		}
		result.add(job);
		return result;
	}
	
	public boolean isRunAble(String jobId,String planInstId) throws TaskSchedulingPlatformException {
		//Job job =(Job) jpaCommonDaoImpl.findByHqlSingle("from Job where id=?", jobId);
		Job job =(Job)jobService.findById(jobId);
		if(job==null){
			return false;
		}
		//PlanInstance pi = jpaCommonDaoImpl.findById(PlanInstance.class, planInstId);
		PlanInstance pi = planInstanceService.findById(planInstId);
		if(pi==null){
			return false;
		}
		String jobtype = job.getJobType();
		if (jobtype.equals("turnover")) {
			if (PlanInstance.COMPLETE.equals(pi.getStatus())) {
				if (isPlanComplete(planInstId)) {
					return true;
				}
			}
		}

		if(PlanInstance.RUNNING.equals(pi.getStatus())){
			if("root".equals(job.getParentJobId())){
				return this.prevJobIsComplete(job.getPrevJobId(),planInstId);
			}else{
				//Job parentJob = (Job) jpaCommonDaoImpl.findByHqlSingle("from Job where id=?", job.getParentJobId());
				Job parentJob = (Job) jobService.findById(job.getParentJobId());

				if(parentJob instanceof ParallelJob){
					String prevJobId = this.getPrevJobId(parentJob);
					return this.prevJobIsComplete(prevJobId, planInstId);
				}else if(parentJob instanceof SerialJob){
					return this.prevJobIsComplete(job.getPrevJobId(),planInstId);
				}
			}
		}
		return false;
	}
	/**
	 * 判断指定计划下面除了翻牌任务之外有没有完成
	 *
	 * @param planInstId
	 * @return true-完成，false-未完成
	 */
	private boolean isPlanComplete(String planInstId) {
		List<Job> result1 =jobService.getJob(planInstId) ;
		// 判断计划下面任务是否全部完成
		if (result1.size() == 0) {
			return true;
		}
		return false;
	}

	private String getPrevJobId(Job parentJob) {
		if("root".equals(parentJob.getParentJobId())){//当父节点的父节点为根节点时,直接返回true
			return parentJob.getPrevJobId();
		}else{
			//Job grandparentJob = (Job) jpaCommonDaoImpl.findByHqlSingle("from Job where id=?", parentJob.getParentJobId());
			Job grandparentJob = (Job) jobService.findById(parentJob.getParentJobId());

			if(grandparentJob instanceof ParallelJob){//当父节点的父节点还是并行节点时,继续往上找 .一直找到当父节点不再是并行节点,获取该节点的前一个节点
				return this.getPrevJobId(grandparentJob);
			}else if(grandparentJob instanceof SerialJob){
				return parentJob.getPrevJobId();
			}
			return grandparentJob.getPrevJobId();
		}
	}


	private boolean prevJobIsComplete(String prevJobId,String planInstId){
		if(prevJobId !=null){//前一个节点ID为null说明为第一个节点
			//Job prveJob = (Job) jpaCommonDaoImpl.findByHqlSingle("from Job where id=?",prevJobId);
			Job prveJob = (Job) jobService.findById(prevJobId);

			boolean result = false;
			//其他节点
			//HistoryJob historyJob = (HistoryJob)jpaCommonDaoImpl.findByHqlSingle("from HistoryJob where jobId=? and planInstId=? and status=?", prveJob.getId(),planInstId,HistoryJob.COMPLE);
			HistoryJob historyJob = historyJobService.findByPlanInstIdJobIdAndStatus(planInstId,prveJob.getId(), HistoryJob.COMPLE);

			if(historyJob!=null){
						result = true;
					}
					return result;
		}
		return true;
	}


	public String startPlan(String planConfigId,String parentPlanInstId,String batchNo,String runPlanId) throws TaskSchedulingPlatformException {
		logger.info("==================启动计划=======================");
		//Integer maxversion = (Integer) jpaCommonDaoImpl.findByHqlSingle("select max(version) from Plan where planConfig.id=?", planConfigId);
		Integer maxversion = planService.getMaxVersionByPlanConfigId(planConfigId);

		if(maxversion == 0){
			logger.info("==================启动计划失败=======================");
			throw new RuntimeException("该计划没有发布的版本,请先发布");
		}
		//Plan plan= (Plan) jpaCommonDaoImpl.findByHqlSingle("from Plan where planConfig.id=? and version=?", planConfigId,maxversion);
		Plan plan= planService.findByPlanConfigIdAndVersion(planConfigId,maxversion);

		PlanInstance pl = new PlanInstance();
		pl.setPlan(plan);
		pl.setStartTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
		pl.setParentPlanInstId(parentPlanInstId);
		pl.setBatchNo(batchNo);
		pl.setRunPlanId(runPlanId);
		//jpaCommonDaoImpl.create(pl);
		planInstanceService.create(pl);
		pl.setStatus(PlanInstance.RUNNING);
		//Job job = (Job) jpaCommonDaoImpl.findByHqlSingle("from Job where parentJobId=? and plan.id=? and prevJobId is null", "root",plan.getId());
		Job job = jobService.findByParentJobIdPlanIdAndPrevJobIdIsNull("root",plan.getId());

		List<Job> jobs = new ArrayList<Job>();
		if(job instanceof SerialJob || job instanceof ParallelJob){
			jobs=this.getJobByParentJob(job);
		}else{
			jobs.add(job);
		}
		WaittingJob wj = null;
		for (Job job2 : jobs) {
			if (job2.getJobType().equals("turnover")) {
				continue;
			}
			wj = new WaittingJob();
			wj.setPlanInstId(pl.getId());
			wj.setJobId(job2.getId());
			wj.setCreateTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			//jpaCommonDaoImpl.create(wj);
			waittingJobService.create(wj);
		}
		logger.info("==================启动计划成功=======================");
		return pl.getId();
	}

	@Override
	public String startWaitPlan(Plan plan) {
		logger.info("==================启动等待计划=======================");
		PlanInstance planInstance=planInstanceService.getPlanInstMinTimeAndPlanId(plan.getId());
		planInstance.setStatus(PlanInstance.RUNNING);
		planInstance.setStartTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
		planInstanceService.updateStatusAndTime(planInstance);
		Job job = jobService.findByParentJobIdPlanIdAndPrevJobIdIsNull("root",plan.getId());
		List<Job> jobs = new ArrayList<Job>();
		if(job instanceof SerialJob || job instanceof ParallelJob){
			jobs=this.getJobByParentJob(job);
		}else{
			jobs.add(job);
		}
		WaittingJob wj = null;
		for (com.sunyard.frameworkset.plugin.tsp.manager.entity.job.Job job2 : jobs) {
			wj = new WaittingJob();
			wj.setPlanInstId(planInstance.getId());
			wj.setJobId(job2.getId());
			wj.setCreateTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			waittingJobService.create(wj);
		}
		logger.info("==================启动等待计划成功=======================");
		return  planInstance.getId();
	}

	@Override
	public void excutePlan(String planConfigId, String batchNo) {
		Integer maxversion = planService.getMaxVersionByPlanConfigId(planConfigId);
		if(maxversion == 0){
			throw new RuntimeException("该计划没有发布的版本,请先发布");
		}
		Plan plan= planService.findByPlanConfigIdAndVersion(planConfigId,maxversion);
		List<PlanMonitor> planMonitorRuning=planMonitorService.getPlanInstByPlanConfigIdAndStatus1(planConfigId);
		List<PlanMonitor> planMonitorWaitting=planMonitorService.getPlanInstByPlanConfigIdAndStatus5(planConfigId);
		int c=planMonitorRuning.size();
		if("1".equals(plan.getStartKind())){
			//旧任务没结束，新任务取消
			if(planMonitorRuning.size()==0){
				this.startPlan(planConfigId,null,batchNo,null);
			}
		}else if("2".equals(plan.getStartKind())){
			//旧任务没结束，新任务等待
			if(planMonitorRuning.size()==0&&planMonitorWaitting.size()==0){
				this.startPlan(planConfigId,null,batchNo,null);
			}else{
				if(planMonitorRuning.size()!=0){
					PlanInstance pl = new PlanInstance();
					pl.setPlan(plan);
					pl.setStartTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));//做为任务开始时优先级标志，不做开始时间
					pl.setParentPlanInstId(null);
					pl.setBatchNo(batchNo);
					pl.setRunPlanId(null);
					planInstanceService.create(pl);
					pl.setStatus(PlanInstance.WAITING);
				}else{
					this.startWaitPlan(plan);
				}
			}

		}else if("3".equals(plan.getStartKind())){
			//旧任务没结束新任务立刻执行
			this.startPlan(planConfigId,null,batchNo,null);
		}

	}

	public void startFdapPlan (String planConfigId, String parentInstId, String batchNo, String runPlanId) throws TaskSchedulingPlatformException {
		//Integer maxversion = (Integer) jpaCommonDaoImpl.findByHqlSingle("select max(version) from Plan where planConfig.id=?", planConfigId);
		Integer maxversion =planService.getMaxVersionByPlanConfigId(planConfigId);

		if(maxversion == null){
			throw new RuntimeException("该计划没有发布的版本,请先发布");
		}
		//Plan plan= (Plan) jpaCommonDaoImpl.findByHqlSingle("from Plan where planConfig.id=? and version=?", planConfigId,maxversion);
		Plan plan=planService.findByPlanConfigIdAndVersion(planConfigId,maxversion);
		//JarJob job= (JarJob) jpaCommonDaoImpl.findByHqlSingle("from Job where plan=?", plan);
		JarJob job= (JarJob) jobService.findByPlan(plan);

		PlanInstance planInstance = new PlanInstance ();
		List<PlanInstParam> params = new ArrayList<PlanInstParam>();
		planInstance.setParams (params);
		jobServerConfigService.runJob(job.getJobInst (planInstance), job.getClientName());
	}

	public boolean removePlanConfigFromQuartz(String planConfigId)  throws TaskSchedulingPlatformException{
		boolean exist=quartzManager.echeckExistsxits(planConfigId);
		if(exist){
			quartzManager.removeJob(planConfigId);
		}else{
			throw new TspException(TspPluginMsgEnum.DEPLOY_ERROR.getCode(),"该计划还没有被启动，请先启动");
		}
		return true;
	}





	public boolean deployPlanConfigToQuartz(String planConfigId) throws TaskSchedulingPlatformException {
		//PlanConfig planConfig=jpaCommonDaoImpl.findById(PlanConfig.class, planConfigId);
		PlanConfig planConfig=planConfigService.findById(planConfigId);

		boolean exist = false;
		exist=quartzManager.echeckExistsxits(planConfig.getId());
		//Integer maxversion = (Integer)jpaCommonDaoImpl.findByHqlSingle("select max(version) from Plan where planConfig.id=?", planConfigId);
		Integer maxversion =planService.getMaxVersionByPlanConfigId(planConfigId);
		//Plan plan= (Plan) jpaCommonDaoImpl.findByHqlSingle("from Plan where planConfig.id=? and version=?", planConfigId,maxversion);
		Plan plan= planService.findByPlanConfigIdAndVersion(planConfigId,maxversion);

		if(plan == null){
			throw new TspException(TspPluginMsgEnum.DEPLOY_ERROR.getCode(),"该计划未发布过");
		}
		if(!exist){
			quartzManager.addJob(StartJob.class, plan.getPlanConfig().getId(), planConfig.getPeriod(), new HashMap<String, Object>());
		}else{
			quartzManager.modifyJobTime(plan.getPlanConfig().getId(), plan.getPeriod());
		}
		return true;
	}

	public boolean deployFdapPlanConfigToQuartz (String planConfigId) throws TaskSchedulingPlatformException {
		//PlanConfig planConfig=jpaCommonDaoImpl.findById(PlanConfig.class, planConfigId);
		PlanConfig planConfig=planConfigService.findById(planConfigId);

		boolean exist = false;
		exist=quartzManager.echeckExistsxits(planConfig.getId());
		//Integer maxversion = (Integer)jpaCommonDaoImpl.findByHqlSingle("select max(version) from Plan where planConfig.id=?", planConfigId);
		Integer maxversion = planService.getMaxVersionByPlanConfigId(planConfigId);
		//Plan plan= (Plan) jpaCommonDaoImpl.findByHqlSingle("from Plan where planConfig.id=? and version=?", planConfigId,maxversion);
		Plan plan= planService.findByPlanConfigIdAndVersion(planConfigId,maxversion);

		if(plan == null){
//			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
//			tspe.setErrorCode(ERRORCODE);
//			tspe.setBriefDescription(planConfig.getName()+"还没有被发布");
//			tspe.setSeriousness(tspe.WARNING);
//			tspe.setDiagnosticInference("请先发布一个版本");
//			throw tspe;
			throw new TspException(TspPluginMsgEnum.DEPLOY_ERROR.getCode(),planConfig.getName()+"还没有被发布"+"请先发布一个版本");
		}
		if(!exist){
			quartzManager.addJob(FdapStartJob.class, plan.getPlanConfig().getId(), planConfig.getPeriod(), new HashMap<String, Object>());
		}else{
			quartzManager.modifyJobTime(plan.getPlanConfig().getId(), plan.getPeriod());
		}
		return true;
	}

	public boolean updatePlanPeriod(Plan plan)
			throws TaskSchedulingPlatformException {
		boolean exist = false;
		exist=quartzManager.echeckExistsxits(plan.getPlanConfig().getId());
		if(exist){
			quartzManager.modifyJobTime(plan.getPlanConfig().getId(), plan.getPeriod());
		}
		return true;
	}


	public boolean stopPlan(String planConfigId, Boolean isStopInst)
			throws TaskSchedulingPlatformException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean suspendPlanInst(String planId)
			throws TaskSchedulingPlatformException {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean stopPlanInst(String planId)
			throws TaskSchedulingPlatformException {
		// TODO Auto-generated method stub
		return false;
	}


	public void afterCompleteJob(String planInstId, String jobId)
			throws TaskSchedulingPlatformException {
		logger.info("作业ID：【{}】;计划实例ID:【{}】;已经完成",jobId,planInstId);
		//Job job = (Job) this.jpaCommonDaoImpl.findByHqlSingle("from Job where id =?", jobId);
		Job job =jobService.findById(jobId);
		if(job==null){
//			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
//			tspe.setErrorCode(ERRORCODE);
//			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
//			tspe.setBriefDescription("已完成的作业无法在数据库查找到对应作业:"+planInstId);
//			throw tspe;
			return;
		}
		List<Job> jobs = new ArrayList<Job>();//后续任务
		if("root".equals(job.getParentJobId())){//先判断该节点的父节点是否为根节点
			if(job.getNextJobId()==null){
				this.completePlanInst(planInstId);
			}else{
				 jobs = this.getNextJobs(job.getNextJobId());
			}
		}else{
			Job parentjob = jobService.findById(job.getParentJobId());
			if(parentjob instanceof SerialJob){//如果父节点为串行节点
				if(job.getNextJobId()==null){
					this.completeRunngingJob(planInstId, parentjob.getId());
				}else{
					 jobs = this.getNextJobs(job.getNextJobId());
				}
			}else if(parentjob instanceof ParallelJob){
				//List<Map<String, Object>> result=this.jdbcTemplate.queryForList("select TSP_JOB.id from TSP_JOB left join TSP_HISTORY_JOB on TSP_JOB.ID = TSP_HISTORY_JOB.JOB_ID  and TSP_HISTORY_JOB.PLAN_INST_ID=? where TSP_JOB.PARENT_JOB_ID=? and (TSP_HISTORY_JOB.STATUS<>? or TSP_HISTORY_JOB.STATUS is NULL)  ", planInstId, parentjob.getId(),HistoryJob.COMPLE);
				List<Map<String, Object>> result=jobService.findByPlanInstIdParentJobIdAndStatus(planInstId, parentjob.getId(),HistoryJob.COMPLE);

				boolean flag = false;//判断子任务是否全部完成的标志
				if(result.size()==1&&result.get(0).get("ID").equals(job.getId())){
					flag=true;
				}
				if(flag){
					this.completeRunngingJob(planInstId, parentjob.getId());
				}
			}
		}
		logger.info("获取的后续任务为【{}】",jobs);
		WaittingJob wj = null;
		for (Job job2 : jobs) {
			wj = new WaittingJob();
			wj.setJobId(job2.getId());
			wj.setPlanInstId(planInstId);
			wj.setCreateTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			//this.jpaCommonDaoImpl.create(wj);
			waittingJobService.create(wj);
		}
	}


	/**
	 * 完成实例
	 * @param planInstId
	 */
	private void completePlanInst(String planInstId) {
		//PlanInstance planInst = this.jpaCommonDaoImpl.findById(PlanInstance.class, planInstId);
		PlanInstance planInst = planInstanceService.findById(planInstId);

		if(planInst!=null){
			planInst.setStatus(PlanInstance.COMPLETE);
			planInst.setEndTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			//this.jpaCommonDaoImpl.update(planInst);
			planInstanceService.update(planInst);
			if(StringUtils.isNotEmpty(planInst.getParentPlanInstId())){//如果该计划实例的父计划实例ID不为空
				//RunningJob rj=	(RunningJob) this.jpaCommonDaoImpl.findByHqlSingle("from RunningJob where callPlanInstId=? and planInstId=?",planInstId,planInst.getParentPlanInstId());
				RunningJob rj=	runningJobService.findByCallPlanInstIdAndPlanInstId(planInstId, planInst.getParentPlanInstId());

				if(rj != null){
					this.completeRunngingJob(planInst.getParentPlanInstId(), rj.getJobId());
				}
			}
		}else{
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
			tspe.setErrorCode(ERRORCODE);
			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
			tspe.setBriefDescription("该计划实例不存在,计划实例ID:"+planInstId);
			logger.error(tspe.getMessage());
			throw tspe;
		}
	}

	private void completeRunngingJob(String planInstId,String jobId){
		//RunningJob runjob = (RunningJob) this.jpaCommonDaoImpl.findByHqlSingle("from RunningJob where jobId=? and planInstId=?", jobId,planInstId);
		RunningJob runjob =runningJobService.findByPlanInstIdAndJobId(jobId, planInstId);

		if(runjob != null){
			runjob.setStatus(RunningJob.COMPLE);
			runjob.setEndTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			runningJobService.update(runjob);
		}else{
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
			tspe.setErrorCode(ERRORCODE);
			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
			tspe.setBriefDescription("该运行作业不存在,运行作业的计划实例ID:"+planInstId+";运行的作业ID:"+jobId);
			logger.error(tspe.getMessage());
			throw tspe;
		}

	}

	@Transactional(propagation= Propagation.REQUIRES_NEW)
	public void completeJob(RunningJob runningJob) {
		runningJob = runningJobService.findById(runningJob.getId());
		HistoryJob history = new HistoryJob();
		history.setJobId(runningJob.getJobId());
		history.setStartTime(runningJob.getStartTime());
		history.setEndTime(runningJob.getEndTime());
		history.setRunLog(runningJob.getRunLog());
		history.setRunHostname(runningJob.getRunHostname());
		history.setStatus(runningJob.getStatus());
		history.setPlanInstId(runningJob.getPlanInstId());
		//this.jpaCommonDaoImpl.create(history);
		historyJobService.create(history);
		//this.jpaCommonDaoImpl.delete(runningJob);
		runningJobService.delete(runningJob);

		if (RunningJob.COMPLE.equals(runningJob.getStatus())) {
			this.afterCompleteJob(runningJob.getPlanInstId(),runningJob.getJobId());
		}
	}


	public String startPlanByApp(String planConfigId, String batchNo,Map<String, String> params) {
		String planInstId=this.startPlan(planConfigId, null, batchNo, null);
		//PlanInstance planInst= this.jpaCommonDaoImpl.findById(PlanInstance.class, planInstId);
		PlanInstance planInst= planInstanceService.findById(planInstId);

		if(params!=null){
			Set<String> keys=params.keySet();
			PlanInstParam param= null;
			for (String string : keys) {
				param = new PlanInstParam();
				param.setParamName(string);
				param.setParamValue(params.get(string));
				param.setPlanInst(planInst);
				//this.jpaCommonDaoImpl.create(param);
				planInstParamService.create(param);
			}
		}
		return planInstId;
	}
	

}
