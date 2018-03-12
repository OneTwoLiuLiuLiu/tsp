package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.JobConfigManager;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.dtf.JobTranslator;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.ZTreeNode;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.*;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.StoreProDataBaseTypeEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.TspPluginMsgEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig.JobConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobConfigService;
import com.sunyard.frameworkset.plugin.tsp.manager.service.PlanConfigService;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.AllJobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig.*;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserAndOrgService;
import com.sunyard.frameworkset.util.converter.BeanConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class JobConfigManagerImpl extends BaseManagerImpl<JobConfigVo, JobConfig, String, JobConfigQo> implements JobConfigManager {

	@Autowired
	private PlanConfigService planConfigService;

	@Autowired
	private JobConfigService jobConfigService;

	@Autowired(required=false)
	private UserAndOrgService userAndOrgService;

	protected JobTranslator<JobConfig,JobConfigVo> po2voer = new JobTranslator<JobConfig,JobConfigVo> ();

	public List<ZTreeNode> getZTree (String planConfigId, String nodeId) {
		List<ZTreeNode> ztreenodes = new ArrayList<ZTreeNode> ();
		PlanConfig planConfig = planConfigService.findById (planConfigId);
		if (StringUtils.isBlank (nodeId)) {
			ZTreeNode root = new ZTreeNode ();
			root.setName (planConfig.getName ());
			root.setIsParent (true);
			root.setId ("root");
			root.setJobConfigType ("root");
			root.setChildren (jobConfigService.getChildrenNode ("root", planConfigId));
			ztreenodes.add (root);
		} else {
			ztreenodes = jobConfigService.getChildrenNode (nodeId, planConfigId);
		}
		return ztreenodes;
	}

	@Override
	public List<Map<String, Object>> getJobConfig (String jobConfigId) {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>> ();
		if (jobConfigId != null && !"root".equals (jobConfigId)) {
			try {
				JobConfigVo jobConfigVo = po2voer.transfer (jobConfigService.getJobInformation (jobConfigId));
				lists = jobConfigVo.getInfoReuslt ();
			} catch (Exception e) {
				e.printStackTrace ();
				TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException ();
				tspe.setSeriousness (TaskSchedulingPlatformException.ERROR);
				tspe.setBriefDescription (e.getMessage ());
				throw tspe;
			}
		}
		return lists;
	}

	/*
	 * 增加一个新作业配置，即在父节点的最后增加一个作业配置
	 *
	 */
	@Override
	public String addNewJobConfig (String root, AllJobVo allJobVo, String planConfigId, String type) {
		try {
			JobConfig job = getJobConfigByVo (allJobVo, type);

			//添加创建人信息,修改人信息
  			String createUser = userAndOrgService.getCurrentUser ().getName ();
			String createUserCode = userAndOrgService.getCurrentUser ().getCode ();
			String modifiedUser = userAndOrgService.getCurrentUser ().getName ();
			String modifiedUserCode = userAndOrgService.getCurrentUser ().getCode ();

			//因为树的拖动新增一个节点时候创建人，创建时间和创建人的编码都是有的，而且不能覆盖
			if (job.getCreateUser () == null) {
				job.setCreateUser (createUser);
			}
			if (job.getCreateUserCode () == null) {
				job.setCreateUserCode (createUserCode);
			}
			if (job.getCreateTime () == null) {
				job.setCreateTime (DateUtil
						.getCurrDate ("yyyy-MM-dd HH:mm:ss"));
			}

			PlanConfig planConfig = planConfigService.findById (planConfigId);
			//找到要添加的父节点下面的最后一个节点
			JobConfig jobs = jobConfigService.getJobConfigByRoot (root, planConfigId);
			if (jobs == null) {
				String nextId2 = null;
				String PreId2 = null;
				String parentId2 = root;
				job.setNextJobId (nextId2);
				job.setParentJobId (parentId2);
				job.setPrevJobId (PreId2);
				job.setPlanConfig (planConfig);
				jobConfigService.create (job);
			} else {
				String nextId1 = null;
				String PreId1 = jobs.getId ();
				String parentId1 = jobs.getParentJobId ();
				job.setNextJobId (nextId1);
				job.setParentJobId (parentId1);
				job.setPrevJobId (PreId1);
				job.setPlanConfig (planConfig);
				jobConfigService.create (job);
				String nextId = job.getId ();
				jobs.setModifiedUser (modifiedUser);
				jobs.setModifiedUserCode (modifiedUserCode);
				jobs.setModifiedTime (DateUtil
						.getCurrDate ("yyyy-MM-dd HH:mm:ss"));
				jobs.setNextJobId (nextId);
				jobConfigService.update (jobs);
			}
			return job.getId ();
		} catch (Exception e) {
			e.printStackTrace ();
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException ();
			tspe.setSeriousness (TaskSchedulingPlatformException.ERROR);
			tspe.setBriefDescription (e.getMessage ());
			throw tspe;
		}
	}

	/*
	 * 向前增加一个作业配置
	 *
	 */
	public String addAfterJobConfig (String root, AllJobVo allJobVo, String type) {
		try {
			JobConfig jobConfig = getJobConfigByVo (allJobVo, type);

			//添加创建人及修改人信息
			String createUser = userAndOrgService.getCurrentUser ().getName ();
			String createUserCode = userAndOrgService.getCurrentUser ().getCode ();
			String modifiedUser = userAndOrgService.getCurrentUser ().getName ();
			String modifiedUserCode = userAndOrgService.getCurrentUser ().getCode ();


			JobConfig job = jobConfigService.findById (root);
			String parentId = job.getParentJobId ();
			String nextId = job.getNextJobId ();
			String preId = job.getPrevJobId ();
			String jobId = root;

			String parentId1 = parentId;
			String nextId1 = null;
			String preId1 = jobId;
			String jobid1 = null;

			if (nextId == null) {
				nextId1 = null;
			} else {
				nextId1 = nextId;
			}
			if (jobConfig.getCreateUser () == null) {
				jobConfig.setCreateUser (createUser);
			}
			if (jobConfig.getCreateUserCode () == null) {
				jobConfig.setCreateUserCode (createUserCode);
			}
			if (jobConfig.getCreateTime () == null) {
				jobConfig.setCreateTime(DateUtil
						.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			}
			jobConfig.setParentJobId (parentId1);
			jobConfig.setNextJobId (nextId1);
			jobConfig.setPrevJobId (preId1);
			jobConfig.setPlanConfig (job.getPlanConfig ());
			jobConfigService.create (jobConfig);

			//更新点击那个节点信息
			job.setModifiedUser(modifiedUser);
			job.setModifiedUserCode(modifiedUserCode);
			job.setNextJobId(jobConfig.getId());
			job.setModifiedTime(DateUtil.getCurrDate("yyyy-MM-dd HH:mm:ss"));
			jobConfigService.update (job);
			//更新点击那个节点的后一个节点信息
			if (nextId != null) {
				JobConfig jobs = jobConfigService.findById (nextId);
				jobs.setPrevJobId(jobConfig.getId());
				jobs.setModifiedTime (DateUtil
						.getCurrDate ("yyyy-MM-dd HH:mm:ss"));
				jobs.setModifiedUser (modifiedUser);
				jobs.setModifiedUserCode (modifiedUserCode);
				jobConfigService.update (jobs);
			}
			return jobConfig.getId ();
		} catch (Exception e) {
			e.printStackTrace ();
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException ();
			tspe.setSeriousness (TaskSchedulingPlatformException.ERROR);
			tspe.setBriefDescription (e.getMessage ());
			throw tspe;
		}
	}

	/*
	 * 向前增加一个作业配置
	 *
	 */
	public String addBeforeJobConfig (String root, AllJobVo allJobVo, String type) {
		try {
			JobConfig jobConfig=getJobConfigByVo(allJobVo, type);

			//添加创建人及修改人信息
			String createUser=userAndOrgService.getCurrentUser().getName();
			String createUserCode=userAndOrgService.getCurrentUser().getCode();
			String modifiedUser=userAndOrgService.getCurrentUser().getName();
			String modifiedUserCode=userAndOrgService.getCurrentUser().getCode();


			//点击的那个节点
			JobConfig job = jobConfigService.findById(root);
			String parentId = job.getParentJobId();
			String nextId = job.getNextJobId();
			String preId = job.getPrevJobId();
			String jobId = root;

			String parentId1 = parentId;
			String nextId1 = jobId;
			String preId1 = null;
			String jobid1 = null;

			// 判断是插入顶端位置还是中间位置
			if (preId == null) {
				preId1 = null;
			} else {
				preId1 = preId;
			}
			if(jobConfig.getCreateUser()==null){
				jobConfig.setCreateUser(createUser);
			}
			if(jobConfig.getCreateUserCode()==null){
				jobConfig.setCreateUserCode(createUserCode);
			}
			if(jobConfig.getCreateTime()==null){
				jobConfig
						.setCreateTime (DateUtil.getCurrDate ("yyyy-MM-dd HH:mm:ss"));
			}
			jobConfig.setParentJobId(parentId1);
			jobConfig.setNextJobId(nextId1);
			jobConfig.setPrevJobId(preId1);
			jobConfig.setPlanConfig(job.getPlanConfig());
			jobConfigService.create(jobConfig);

			//点击更新的那个节点
			job.setPrevJobId (jobConfig.getId ());
			job.setModifiedTime (DateUtil.getCurrDate ("yyyy-MM-dd HH:mm:ss"));
			job.setModifiedUser (modifiedUser);
			job.setModifiedUserCode (modifiedUserCode);
			jobConfigService.update(job);
			if (preId != null) {
				JobConfig jobs = jobConfigService.findById (preId);
				jobs.setNextJobId (jobConfig.getId ());
				jobs.setModifiedTime (DateUtil
						.getCurrDate ("yyyy-MM-dd HH:mm:ss"));
				jobs.setModifiedUser (modifiedUser);
				jobs.setModifiedUserCode (modifiedUserCode);
				jobConfigService.update (jobs);
			}
			return jobConfig.getId ();
		} catch (Exception e) {
			e.printStackTrace ();
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException ();
			tspe.setSeriousness (TaskSchedulingPlatformException.ERROR);
			tspe.setBriefDescription (e.getMessage ());
			throw tspe;
		}
	}

	@Override
	public void deleteByJobConfigId(String id) {
		try {
			JobConfig job = jobConfigService.findById(id);
			String nextId = job.getNextJobId();
			String preId = job.getPrevJobId();
			jobConfigService.delete(job);
			if (preId == null && nextId != null) {
				JobConfig jobs = jobConfigService.findById(nextId);
				jobs.setPrevJobId(null);
				jobConfigService.update(jobs);
			} else if (nextId == null && preId != null) {
				JobConfig jobs1 = jobConfigService.findById(preId);
				jobs1.setNextJobId(null);
				jobConfigService.update(jobs1);
			} else if (nextId != null && preId != null) {
				JobConfig job1 = jobConfigService.findById(preId);
				JobConfig job2 = jobConfigService.findById(nextId);
				String id1 = job1.getId();
				String id2 = job2.getId();
				job1.setNextJobId(id2);
				job2.setPrevJobId(id1);
				jobConfigService.update(job1);
				jobConfigService.update(job2);
			}
		}catch (Exception e){
			throw new TspException(TspPluginMsgEnum.DELETE_ERROR.getCode (), "任务删除异常", e);
		}
	}

	@Override
	public List<Map<String, Object>> getSharedPlanConfigs () {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>> ();
		List<PlanConfig> pcs = planConfigService.getSharedPlanConfigs ();
		Map<String, Object> map = null;
		for (PlanConfig planConfig : pcs) {
			map = new HashMap<String, Object> ();
			map.put ("text", planConfig.getName ());
			map.put ("value", planConfig.getId ());
			list.add (map);
		}
		return list;
	}

	/*
         * 修改一个作业配置
         *
         */
	public void updateJobConfig(String root, AllJobVo allJobVo,String type) {
		try {
			JobConfig jobConfig = jobConfigService.findById(root);
			//获取修改人信息
			String modifiedUser=userAndOrgService.getCurrentUser().getName();
			String modifiedUserCode=userAndOrgService.getCurrentUser().getCode();
			jobConfig.setModifiedUser(modifiedUser);
			jobConfig.setModifiedUserCode(modifiedUserCode);
			jobConfig.setRunParams(allJobVo.getRunParams());
			jobConfig.setTimeFormat(allJobVo.getTimeFormat());
			jobConfig.setIgnoreErr(allJobVo.getIgnoreErr());
			jobConfig.setRetryCnt(allJobVo.getRetryCnt());
			jobConfig.setRetrySec(allJobVo.getRetrySec());
			jobConfig.setName(allJobVo.getName());
//		JobConfig jobConfig=getJobConfigByVo(allJobVo, type);
			if(type.equals("jar")){
				JarJobConfig jarJobConfig=(JarJobConfig)jobConfig;
//				jarJobConfig.setName(allJobVo.getName());
//				jarJobConfig.setRunParams(allJobVo.getRunParams());
//				jarJobConfig.setTimeFormat(allJobVo.getTimeFormat());
//				jarJobConfig.setIgnoreErr(allJobVo.getIgnoreErr());
//				jarJobConfig.setRetryCnt(allJobVo.getRetryCnt());
//				jarJobConfig.setRetrySec(allJobVo.getRetrySec());
				jarJobConfig.setFilePath(allJobVo.getFilePath());
				jarJobConfig.setInitialMemoryValue(allJobVo.getInitialMemoryValue());
				jarJobConfig.setMaxMemoryValue(allJobVo.getMaxMemoryValue());
				jobConfigService.update(jarJobConfig);
			}else if(type.equals("kjb")){
				KjbJobConfig kjbJobConfig=(KjbJobConfig)jobConfig;
				kjbJobConfig.setSelect(allJobVo.getSelect());
//				kjbJobConfig.setName(allJobVo.getName());
				kjbJobConfig.setRep(allJobVo.getRep());
				kjbJobConfig.setUser(allJobVo.getUser());
				kjbJobConfig.setPass(allJobVo.getPass());
				kjbJobConfig.setDir(allJobVo.getDir());
				kjbJobConfig.setJobs(allJobVo.getJobs());
				kjbJobConfig.setFile(allJobVo.getFile());
//				kjbJobConfig.setRunParams(allJobVo.getRunParams());
//				kjbJobConfig.setTimeFormat(allJobVo.getTimeFormat());
//				kjbJobConfig.setIgnoreErr(allJobVo.getIgnoreErr());
//				kjbJobConfig.setRetryCnt(allJobVo.getRetryCnt());
//				kjbJobConfig.setRetrySec(allJobVo.getRetrySec());
				jobConfigService.update(kjbJobConfig);
			}else  if(type.equals("ktr")) {
				KtrJobConfig ktrJobConfig = (KtrJobConfig) jobConfig;
				ktrJobConfig.setSelect(allJobVo.getSelect());
//				ktrJobConfig.setName(allJobVo.getName());
				ktrJobConfig.setRep(allJobVo.getRep());
				ktrJobConfig.setUser(allJobVo.getUser());
				ktrJobConfig.setPass(allJobVo.getPass());
				ktrJobConfig.setDir(allJobVo.getDir());
				ktrJobConfig.setTrans(allJobVo.getTrans());
				ktrJobConfig.setFile(allJobVo.getFile());
//				ktrJobConfig.setRunParams(allJobVo.getRunParams());
//				ktrJobConfig.setTimeFormat(allJobVo.getTimeFormat());
//				ktrJobConfig.setIgnoreErr(allJobVo.getIgnoreErr());
//				ktrJobConfig.setRetryCnt(allJobVo.getRetryCnt());
//				ktrJobConfig.setRetrySec(allJobVo.getRetrySec());
				jobConfigService.update(ktrJobConfig);
			}else  if(type.equals("shell")){
				ShellJobConfig shellJobConfig = (ShellJobConfig) jobConfig;
//				shellJobConfig.setName(allJobVo.getName());
				shellJobConfig.setFilePath(allJobVo.getFilePath());
				shellJobConfig.setReturnValues(allJobVo.getReturnValues());
				jobConfigService.update(shellJobConfig);
			}else  if(type.equals("storepro")){
				StoreProJobConfig storeProJobConfig=(StoreProJobConfig)jobConfig;
//				storeProJobConfig.setName(allJobVo.getName());
				storeProJobConfig.setProcedureName(allJobVo.getProcedureName());
				storeProJobConfig.setDataBaseType(allJobVo.getDataBaseType());
				storeProJobConfig.setDataBaseUser(allJobVo.getDataBaseUser());
				storeProJobConfig.setDataBasePwd(allJobVo.getDataBasePwd());
				storeProJobConfig.setDataBaseIp(allJobVo.getDataBaseIp());
				storeProJobConfig.setDataBasePort(allJobVo.getDataBasePort());
				storeProJobConfig.setDataBaseName(allJobVo.getDataBaseName());
				jobConfigService.update(storeProJobConfig);
			}else  if(type.equals("bat")){
				BatJobConfig batJobConfig=(BatJobConfig) jobConfig;
//				batJobConfig.setName(allJobVo.getName());
				batJobConfig.setFilePath(allJobVo.getFilePath());
				jobConfigService.update(batJobConfig);
			}else  if(type.equals("exe")){
				ExeJobConfig exeJobConfig=(ExeJobConfig) jobConfig;
//				exeJobConfig.setName(allJobVo.getName());
				exeJobConfig.setFilePath(allJobVo.getFilePath());
				exeJobConfig.setReturnValues(allJobVo.getReturnValues());
				jobConfigService.update(exeJobConfig);
			}else  if(type.equals("http")){
				HttpJobConfig httpJobConfig=(HttpJobConfig)jobConfig;
//				httpJobConfig.setName(allJobVo.getName());
				httpJobConfig.setHttpUrl(allJobVo.getHttpUrl());
				jobConfigService.update(httpJobConfig);
			}else  if(type.equals("plan")) {
				PlanJobConfig planJobConfig = (PlanJobConfig) jobConfig;
//				httpJobConfig.setName(allJobVo.getName());
				planJobConfig.setCallPlanConfigId(allJobVo.getCallPlanConfigId());
				jobConfigService.update(planJobConfig);
			}else if(type.equals("turnover")) {
				TurnOverJobConfig turnOverJobConfig = (TurnOverJobConfig) jobConfig;
				jobConfigService.update(turnOverJobConfig);
			}else if(type.equals("task")) {
				TaskJobConfig taskJobConfig = (TaskJobConfig) jobConfig;
				jobConfigService.update(taskJobConfig);
			}else{
					DataStageJobConfig  dataStageJobConfig=(DataStageJobConfig)jobConfig;
//				dataStageJobConfig.setName(allJobVo.getName());
					dataStageJobConfig.setDsJobName(allJobVo.getDsJobName());
					dataStageJobConfig.setDsProjectName(allJobVo.getDsProjectName());
					jobConfigService.update(dataStageJobConfig);
				}
		} catch (Exception e) {
			throw new TspException(TspPluginMsgEnum.UPDATE_ERROR.getCode (), "任务更新异常", e);
		}

	}

	//根据不同类型转成不同vo
	public AllJobVo findVoById (String id) {
		JobConfig jobConfig=jobConfigService.findById(id);
		AllJobVo allJobVo= BeanConverter.convert(new AllJobVo(), jobConfig);
		return allJobVo;
	}

	@Override
	public void addMoveNew (String treeNodeId, String targetNodeId, String planConfigId) {
		try {
			//得到修改人的信息
			String modifiedUser = userAndOrgService.getCurrentUser ().getName ();
			String modifiedUserCode = userAndOrgService.getCurrentUser ().getCode ();
			// 要移动的那个节点
			AllJobVo jobConfigVo = this.findVoById (treeNodeId);
			// 获得移动这个节点新的对象
			AllJobVo newJobConfigVo = jobConfigVo.getClass ().newInstance ();
			BeanUtils.copyProperties (jobConfigVo, newJobConfigVo, new String[]{"id",
					"nextJobId", "parentId", "prevJobId", "planConfig"});
			newJobConfigVo.setModifiedTime (DateUtil
					.getCurrDate ("yyyy-MM-dd HH:mm:ss"));
			newJobConfigVo.setModifiedUser (modifiedUser);
			newJobConfigVo.setModifiedUserCode (modifiedUserCode);
			String newJobConfigId = this.addNewJobConfig(targetNodeId, newJobConfigVo, planConfigId, jobConfigVo.getJobType());
			// 判断是不是父节点
			this.isParentNode (treeNodeId, planConfigId, jobConfigVo, newJobConfigId);
			//添加新节点以后删除原来那个节点
			this.deleteByJobConfigId (treeNodeId);
		} catch (Exception e) {
			throw new TspException (TspPluginMsgEnum.UPDATE_ERROR.getCode (), "向内迁移异常", e);
		}
	}

	@Override
	public void addMoveBefore (String treeNodeId, String targetNodeId, String planConfigId) {
		try {
			//得到修改人的信息
			String modifiedUser = userAndOrgService.getCurrentUser ().getName ();
			String modifiedUserCode = userAndOrgService.getCurrentUser ().getCode ();

			// 要移动那个节点
			AllJobVo jobConfigVo = this.findVoById (treeNodeId);

			// 获得移动这个节点新的对象
			AllJobVo newJobConfigVo = jobConfigVo.getClass ().newInstance ();
			BeanUtils.copyProperties (jobConfigVo, newJobConfigVo, new String[]{"id",
					"nextJobId", "parentId", "prevJobId", "planConfig"});
			newJobConfigVo.setModifiedTime (DateUtil
					.getCurrDate ("yyyy-MM-dd HH:mm:ss"));
			newJobConfigVo.setModifiedUser (modifiedUser);
			newJobConfigVo.setModifiedUserCode (modifiedUserCode);
			String newJobConfigId = this.addBeforeJobConfig(targetNodeId, newJobConfigVo, jobConfigVo.getJobType());

			// 判断是不是父节点
			this.isParentNode (treeNodeId, planConfigId, jobConfigVo, newJobConfigId);
			//向前增加一个节点之后删除原来那个节点
			this.deleteByJobConfigId (treeNodeId);
		} catch (Exception e) {
			throw new TspException (TspPluginMsgEnum.UPDATE_ERROR.getCode (), "向前迁移异常", e);
		}
	}

	@Override
	public void addMoveAfter (String treeNodeId, String targetNodeId, String planConfigId) {
		try {
			//得到修改人的信息
			String modifiedUser = userAndOrgService.getCurrentUser ().getName ();
			String modifiedUserCode = userAndOrgService.getCurrentUser ().getCode ();

			// 要移动那个节点
			AllJobVo jobConfigVo = this.findVoById (treeNodeId);
			// 获得移动这个节点新的对象
			AllJobVo newJobConfigVo = jobConfigVo.getClass ().newInstance ();
			BeanUtils.copyProperties (jobConfigVo, newJobConfigVo, new String[]{"id",
					"nextJobId", "parentId", "prevJobId", "planConfig"});

			newJobConfigVo.setModifiedTime (DateUtil
					.getCurrDate ("yyyy-MM-dd HH:mm:ss"));
			newJobConfigVo.setModifiedUser (modifiedUser);
			newJobConfigVo.setModifiedUserCode (modifiedUserCode);
			String newJobConfigId = this.addAfterJobConfig (targetNodeId, newJobConfigVo, jobConfigVo.getJobType ());
			// 判断是不是父节点
			this.isParentNode (treeNodeId, planConfigId, jobConfigVo, newJobConfigId);
			//向后拖动一个节点之后删除原来那个节点
			this.deleteByJobConfigId (treeNodeId);
		} catch (Exception e) {
			throw new TspException (TspPluginMsgEnum.UPDATE_ERROR.getCode (), "向后迁移异常", e);
		}
	}

	public JobConfig getJobConfigByVo (JobConfigVo allJobVo, String type) {
		try{
			JobConfig jobConfig;
		if("parallel".equals(type)){
			ParallelJobConfigVo parallelJobConfigVo= BeanConverter.convert(new ParallelJobConfigVo(), allJobVo);
			jobConfig=po2voer.transferVo(parallelJobConfigVo);
		}else if("serial".equals(type)) {
			SerialJobConfigVo serialJobConfigVo = BeanConverter.convert(new SerialJobConfigVo(), allJobVo);
			jobConfig = po2voer.transferVo(serialJobConfigVo);
		}else if("task".equals(type)){
				TaskJobConfigVo taskJobConfigVo= BeanConverter.convert(new TaskJobConfigVo(), allJobVo);
				jobConfig=po2voer.transferVo(taskJobConfigVo);
		} else if(type.equals("jar")){
			JarJobConfigVo jarJobConfigVo= BeanConverter.convert(new JarJobConfigVo(), allJobVo);
			jobConfig=po2voer.transferVo(jarJobConfigVo);
		}else if(type.equals("kjb")){
			KjbJobConfigVo kjbJobConfigVo= BeanConverter.convert(new KjbJobConfigVo(), allJobVo);
			jobConfig=po2voer.transferVo(kjbJobConfigVo);
		}else  if(type.equals("ktr")){
			KtrJobConfigVo ktrJobConfigVo= BeanConverter.convert(new KtrJobConfigVo(), allJobVo);
			jobConfig=po2voer.transferVo(ktrJobConfigVo);
		}else  if(type.equals("shell")){
			ShellJobConfigVo shellJobConfigVo= BeanConverter.convert(new ShellJobConfigVo(), allJobVo);
			jobConfig=po2voer.transferVo(shellJobConfigVo);
		}else  if(type.equals("storepro")){
			StoreProJobConfigVo storeProJobConfigVo= BeanConverter.convert(new StoreProJobConfigVo(), allJobVo);
			if (StoreProDataBaseTypeEnum.ORACLE.getCode ().equals (storeProJobConfigVo.getDataBaseType ().trim ())) {
				storeProJobConfigVo.setDataBaseType (StoreProDataBaseTypeEnum.ORACLE.getCode ());
			} else if (StoreProDataBaseTypeEnum.DB2.getCode ().equals (storeProJobConfigVo.getDataBaseType ().trim ())) {
				storeProJobConfigVo.setDataBaseType (StoreProDataBaseTypeEnum.DB2.getCode ());
			} else if (StoreProDataBaseTypeEnum.mysql.getCode ().equals (storeProJobConfigVo.getDataBaseType ().trim ())) {
				storeProJobConfigVo.setDataBaseType (StoreProDataBaseTypeEnum.mysql.getCode ());
			} else if (StoreProDataBaseTypeEnum.POSTGRESQL.getCode ().equals (storeProJobConfigVo.getDataBaseType ().trim ())) {
				storeProJobConfigVo.setDataBaseType (StoreProDataBaseTypeEnum.POSTGRESQL.getCode ());
			}
			jobConfig=po2voer.transferVo(storeProJobConfigVo);
		}else  if(type.equals("bat")){
			BatJobConfigVo batJobConfigVo= BeanConverter.convert(new BatJobConfigVo(), allJobVo);
			jobConfig=po2voer.transferVo(batJobConfigVo);
		}else  if(type.equals("exe")){
			ExeJobConfigVo exeJobConfigVo= BeanConverter.convert(new ExeJobConfigVo(), allJobVo);
			jobConfig=po2voer.transferVo(exeJobConfigVo);
		}else  if(type.equals("http")){
			HttpJobConfigVo httpJobConfigVo= BeanConverter.convert(new HttpJobConfigVo(), allJobVo);
			jobConfig=po2voer.transferVo(httpJobConfigVo);
		} else if (type.equals ("plan")) {
			PlanJobConfigVo planJobConfigVo = BeanConverter.convert (new PlanJobConfigVo (), allJobVo);
			jobConfig = po2voer.transferVo (planJobConfigVo);
		}else if(type.equals ("turnover")){
			TurnoverJobConfigVo turnoverVo=BeanConverter.convert(new TurnoverJobConfigVo(), allJobVo);
			jobConfig=po2voer.transferVo(turnoverVo);
		}else {
			DataStageJobConfigVo dataStageJobConfigVo= BeanConverter.convert(new DataStageJobConfigVo(), allJobVo);
			jobConfig=po2voer.transferVo(dataStageJobConfigVo);
		}
			return jobConfig;
		} catch (Exception e) {
			e.printStackTrace ();
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException ();
			tspe.setSeriousness (TaskSchedulingPlatformException.ERROR);
			tspe.setBriefDescription (e.getMessage ());
			throw tspe;
		}
	}

	private void isParentNode (String treeNodeId, String planConfigId,
							   JobConfigVo job, String newJobConfigId) {
		if (("serial").equals (job.getJobType ()) || ("parallel").equals (job.getJobType ())) {
			//得到修改人的信息
			String modifiedUser = userAndOrgService.getCurrentUser ().getName ();
			String modifiedUserCode = userAndOrgService.getCurrentUser ().getCode ();

			List<JobConfig> lists = jobConfigService.getJobConfigList (treeNodeId, planConfigId);
			if (lists.size () != 0) {
				for (int i = 0; i < lists.size (); i++) {
					lists.get (i).setParentJobId (newJobConfigId);
					lists.get (i).setModifiedTime (
							DateUtil.getCurrDate ("yyyy-MM-dd HH:mm:ss"));
					lists.get (i).setModifiedUser (modifiedUser);
					lists.get (i).setModifiedUserCode (modifiedUserCode);
					jobConfigService.update (lists.get (i));
				}
			}
		}
	}
}
