package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.PlanConfigManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.RunPlan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.ZTreeNode;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.ParallelJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.PlanJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.SerialJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.*;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.*;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanConfigVo;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.OrgVo;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserAndOrgService;
import com.sunyard.frameworkset.plugin.tsp.spi.user.service.UserVo;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 计划配置 manager
 * <p/>
 * Author: Created by code generator
 * Date: Tue Jan 03 10:27:53 CST 2017
 */
@Component
@Transactional
public class PlanConfigManagerImpl extends BaseManagerImpl<PlanConfigVo, PlanConfig, String, PlanConfigQo> implements PlanConfigManager {


	@Autowired
	private UserAndOrgService userAndOrgService;

	@Autowired
	private PlanService planService;

	@Autowired
	private JobService jobService;

	@Autowired
	private JobConfigService jobConfigService;

	@Autowired
	private PlanConfigService planConfigService;

	@Autowired
	private PlanScheduleService planScheduleService;

	@Autowired
	private RunPlanService runPlanService;

	@Override
	public void addPlan (PlanConfigVo vo) {
		try {
			vo.setCreateTime (DateUtil.getCurrDate ("yyyy-MM-dd HH:mm:ss"));
			String createUser = userAndOrgService.getCurrentUser ().getName ();
			String createUserCode = userAndOrgService.getCurrentUser ().getCode ();
			String stopUser = userAndOrgService.getCurrentUser ().getName ();
			String stopUserCode = userAndOrgService.getCurrentUser ().getCode ();
			if (NoticeWayEnum.Message.getCode ().equals (vo.getNoticeWay ().trim ())) {
				vo.setNoticeWay (NoticeWayEnum.Message.getCode ());
			} else if (NoticeWayEnum.MAIL.getCode ().equals (vo.getNoticeWay ().trim ())) {
				vo.setNoticeWay (NoticeWayEnum.MAIL.getCode ());
			} else if (NoticeWayEnum.MAIL_AND_MESSAGE.getCode ().equals (vo.getNoticeWay ().trim ())) {
				vo.setNoticeWay (NoticeWayEnum.MAIL_AND_MESSAGE.getCode ());
			}
			if(StartWayEnum.NewTaskCancel.getCode().equals(vo.getStartKind().trim())){
				vo.setStartKind(StartWayEnum.NewTaskCancel.getCode());
			}else if(StartWayEnum.NewTaskWait.getCode().equals(vo.getStartKind().trim())){
				vo.setStartKind(StartWayEnum.NewTaskWait.getCode());
			}else if(StartWayEnum.NewTaskExcute.getCode().equals(vo.getStartKind().trim())){
				vo.setStartKind(StartWayEnum.NewTaskExcute.getCode());
			}
			vo.setStatus (PlanConfigStatusEnum.STOP.getCode ());
			vo.setCreateUser (createUser);
			vo.setCreateUserCode (createUserCode);
			vo.setStopUser (stopUser);
			vo.setStopUserCode (stopUserCode);
			this.create (vo);
		} catch (Exception e) {
			throw new TspException (TspPluginMsgEnum.INSERT_ERROR.getCode (), "新增计划配置异常", e);
		}
	}

	@Override
	public void releasePlan (String planConfigId) {
		PlanConfig planConfig = baseService.findById (planConfigId);
		String deployUser = userAndOrgService.getCurrentUser ().getName ();
		String deployUserCode = userAndOrgService.getCurrentUser ().getCode ();
		String createUser = userAndOrgService.getCurrentUser ().getName ();
		String createUserCode = userAndOrgService.getCurrentUser ().getCode ();
		// 添加计划到计划表中
		Plan plan = planService.releasePlan (planConfigId, planConfig, deployUser,
				deployUserCode);
		CheckValidation (planConfigId, plan.getName ());
		// 添加作业到作业表中
		List<JobConfig> list = jobConfigService.findListByPlanConfigId (planConfigId);
		jobService.releaseJob (plan, list, createUser, createUserCode);
	}

	@Override
	public List<JobConfig> getJobs (String planConfigId) {
		List<JobConfig> lists = new ArrayList<JobConfig> ();
		List<JobConfig> lists1 = jobConfigService.getListByPlanConfigIdAndType (planConfigId, "plan");
		for (int i = 0; i < lists1.size (); i++) {
			if (lists1.get (i) instanceof PlanJobConfig) {
				PlanJobConfig planJobConfig = (PlanJobConfig) lists1.get (i);
				String callPlanConfigId = planJobConfig.getCallPlanConfigId ();
				Plan plan = planService.getPlanByPlanConfigId (callPlanConfigId);
				if (plan == null) {
					throw new TspException (TspPluginMsgEnum.PLAN_NO_JOB_ERROR.getCode (),
							planJobConfig.getCallPlanConfigName ()
									+ "计划还没有没有发布版本");
				}
			}
		}
		if (lists.size () > 0) {
			lists = lists1;
		} else {
			lists = jobConfigService.findListByPlanConfigId (planConfigId);
		}
		return lists;
	}

	@Override
	public void runPlan(String beginTime, String endTime, String planConfigId) {
		//PlanConfig planConfig = jpaCommonDaoImpl.findById (PlanConfig.class, planConfigId);
		PlanConfig planConfig = planConfigService.findById (planConfigId);
		//Integer maxversion = (Integer) jpaCommonDaoImpl.findByHqlSingle ("select max(version) from Plan where planConfig.id=?", planConfigId);
		Integer maxversion = planService.getMaxVersionByPlanConfigId (planConfigId);
		Plan plan = planService.findByPlanConfigIdAndVersion (planConfigId, maxversion);
		if (plan == null) {
//			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException ();
//			tspe.setBriefDescription (planConfig.getName () + "还没有被发布");
//			tspe.setSeriousness (tspe.WARNING);
//			tspe.setDiagnosticInference ("请先发布一个版本");
//			throw tspe;
			throw new TspException(TspPluginMsgEnum.DEPLOY_ERROR.getCode(),planConfig.getName ()+"还没有被发布,"+"请先发布一个版本");
		}
		RunPlan rp = new RunPlan ();
		rp.setPlanConfig(planConfig);
		rp.setEndTime (endTime);
		rp.setStartTime (beginTime);
		rp.setStatus (RunPlanStatusEnum.RUN.getCode());
		runPlanService.create (rp);
	}

	@Override
	public void stopPlan(String planConfigId) {
		PlanConfig planConfig = planConfigService.findById(planConfigId);
		Date date = new Date ();
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format (date);
		planConfig.setStopTime (str);
		planConfig.setStatus ("0");
		String stopUser = userAndOrgService.getCurrentUser ().getName ();
		String stopUserCode = userAndOrgService.getCurrentUser ().getCode ();
		planConfig.setStopUser (stopUser);
		planConfig.setStopUserCode (stopUserCode);
		planScheduleService.removePlanConfigFromQuartz (planConfigId);
		planConfigService.update (planConfig);
	}

	@Override
	public List<ZTreeNode> getRecipients (String id) {
		List<ZTreeNode> result = new ArrayList<ZTreeNode> ();
		List<OrgVo> orgVos = userAndOrgService.getSubOrgs (id);
		for (OrgVo orgVo : orgVos) {
			result.add (changeFromOrgVo (orgVo));
		}
		List<UserVo> uservos = userAndOrgService.getUserByOrg (id);
		for (UserVo uservo : uservos) {
			result.add (changeFromUserVo (uservo));
		}
		return result;
	}

	@Override
	public void updatePlan (PlanConfigVo planConfigVo) {
		try {
			PlanConfigVo newPlanConfigVo = this.findById (planConfigVo.getId ());
			newPlanConfigVo.setName (planConfigVo.getName ());
			newPlanConfigVo.setRecipients (planConfigVo.getRecipients ());
			newPlanConfigVo.setRecipientsCn (planConfigVo.getRecipientsCn ());
			if (NoticeWayEnum.Message.getCode ().equals (newPlanConfigVo.getNoticeWay ().trim ())) {
				newPlanConfigVo.setNoticeWay (NoticeWayEnum.Message.getCode ());
			} else if (NoticeWayEnum.MAIL.getCode ().equals (newPlanConfigVo.getNoticeWay ().trim ())) {
				newPlanConfigVo.setNoticeWay (NoticeWayEnum.MAIL.getCode ());
			} else if (NoticeWayEnum.MAIL_AND_MESSAGE.getCode ().equals (newPlanConfigVo.getNoticeWay ().trim ())) {
				newPlanConfigVo.setNoticeWay (NoticeWayEnum.MAIL_AND_MESSAGE.getCode ());
			}
			if(StartWayEnum.NewTaskCancel.getCode().equals(planConfigVo.getStartKind().trim())){
				newPlanConfigVo.setStartKind(StartWayEnum.NewTaskCancel.getCode());
			}else if(StartWayEnum.NewTaskWait.getCode().equals(planConfigVo.getStartKind().trim())){
				newPlanConfigVo.setStartKind(StartWayEnum.NewTaskWait.getCode());
			}else if(StartWayEnum.NewTaskExcute.getCode().equals(planConfigVo.getStartKind().trim())){
				newPlanConfigVo.setStartKind(StartWayEnum.NewTaskExcute.getCode());
			}
			newPlanConfigVo.setPeriod (planConfigVo.getPeriod ());
			newPlanConfigVo.setIsShare (planConfigVo.getIsShare ());
			if (IsShareEnum.SHARE.getCode ().equals (newPlanConfigVo.getIsShare ().trim ())) {
				newPlanConfigVo.setIsShare (IsShareEnum.SHARE.getCode ());
			} else if (IsShareEnum.UN_SHARE.getCode ().equals (newPlanConfigVo.getIsShare ().trim ())) {
				newPlanConfigVo.setIsShare (IsShareEnum.UN_SHARE.getCode ());
			}
			newPlanConfigVo.setDesc (planConfigVo.getDesc ());
			super.update (newPlanConfigVo);
		} catch (Exception e) {
			throw new TspException (TspPluginMsgEnum.UPDATE_ERROR.getCode (), "修改计划配置异常", e);
		}
	}

	@Override
	public void addJobConfigByXML (InputStream is) {
		SAXReader reader = new SAXReader ();
		//获取doc树
		Document doc = null;
		try {
			doc = reader.read (is);
			//doc = reader.read(file);
			//获取跟节点
			Element root = doc.getRootElement ();
			List<PlanConfig> emps = new ArrayList<PlanConfig> ();
			String name = root.attribute ("name").getValue ();
			String noticeWay = root.attribute ("noticeWay").getValue ();
			String period = root.attribute ("period").getValue ();
			String isShare = root.attribute ("isShare").getValue ();
			String status = root.attribute ("status").getValue ();
			String desc = root.attribute ("desc").getValue ();
			String createTime = DateUtil.getCurrDate ("yyyy-MM-dd HH:mm:ss");

			PlanConfig planConfig = new PlanConfig ();
			planConfig.setName (name);
			planConfig.setNoticeWay (noticeWay);
			planConfig.setPeriod (period);
			planConfig.setIsShare (isShare);
			planConfig.setStatus (status);
			planConfig.setDesc (desc);
			planConfig.setCreateTime (createTime);

			planConfigService.create (planConfig);
			String planConfigId = planConfig.getId ();
			List<Element> lists = new ArrayList<Element> ();
			lists = root.elements ();
			addJobConfig (lists, "root", planConfigId);
		} catch (Exception e) {
			throw new TspException (TspPluginMsgEnum.FILE_ERROR.getCode (), TspPluginMsgEnum.FILE_ERROR.getName (), e);
		}
	}

	private void addJobConfig (List<Element> lists, String parentId1, String planConfigId) throws Exception {

		PlanConfig planConfig1 = planConfigService.findById (planConfigId);

		for (Element element1 : lists) {
			//获取需要的新添加作业的类型
			String type = element1.attribute ("type").getValue ();

			//获取需要添加的新作业的前一个节点、后一个节点、父节点
			String parentJobId = parentId1;
			String nextJobId = null;
			String prevJobId = null;

			//新的作业实例、新的作业的id
			JobConfig newJobConfig = null;
			String newJobCobfigId = null;

			//之前的job、之前job的id
			JobConfig jobCobfig = jobConfigService.getLastJobConfig (parentId1, planConfigId);
			String jobCobfigId = null;


			//如果查询出来的那个job为空，则表示为这个父节点下面的第一个节点
			if (jobCobfig == null) {
				if ("parallel".equals (type)) {
					newJobConfig = new ParallelJobConfig ();
				} else if ("serial".equals (type)) {
					newJobConfig = new SerialJobConfig ();
				} else {
					newJobConfig = JobConfig.getJobConfigClassByType (type).newInstance ();
				}
				newJobConfig = newJobConfig.getJobConfigByElement (element1);
				newJobConfig.setNextJobId (nextJobId);
				newJobConfig.setParentJobId (parentJobId);
				newJobConfig.setPrevJobId (prevJobId);
				newJobConfig.setPlanConfig (planConfig1);
				jobConfigService.create (newJobConfig);
				newJobCobfigId = newJobConfig.getId ();
			} else {
				jobCobfigId = jobCobfig.getId ();
				if ("parallel".equals (type)) {
					newJobConfig = new ParallelJobConfig ();
				} else if ("serial".equals (type)) {
					newJobConfig = new SerialJobConfig ();
				} else {
					newJobConfig = JobConfig.getJobConfigClassByType (type).newInstance ();
				}
				newJobConfig = newJobConfig.getJobConfigByElement (element1);
				newJobConfig.setNextJobId (nextJobId);
				newJobConfig.setPrevJobId (jobCobfigId);
				newJobConfig.setParentJobId (parentJobId);
				newJobConfig.setPlanConfig (planConfig1);
				jobConfigService.create (newJobConfig);
				//获取新添加的那个节点的id，用于更新新增加节点之前那个节点
				newJobCobfigId = newJobConfig.getId ();
				//更新添加的那个job之前的那个节点信息
				jobCobfig.setNextJobId (newJobCobfigId);
				jobConfigService.update (jobCobfig);
			}
			if (type.equals ("serial") || type.equals ("parallel")) {
				List<Element> lists1 = new ArrayList<Element> ();
				lists = element1.elements ();
				addJobConfig (lists, newJobCobfigId, planConfigId);
			}
		}
	}

	private void CheckValidation (String planConfigId, String planName) {
		List<Object[]> list = jobConfigService.checkReleaseJobValidation (planConfigId);
		if (list.size () != 0) {
			StringBuilder r = new StringBuilder ("作业名:");
			for (Object[] o : list) {
				r.append (o[ 1 ]).append (";");
			}
			throw new TspException(TspPluginMsgEnum.DEPLOY_ERROR.getCode(),"作业配置计划不完整，您配置的计划【" + planName
					+ "】的作业【" + r + "】没有子作业，请重新配置再发布");
		}
	}

	@Override
	public void startPlan (String id) {
		PlanConfig planConfig = planConfigService.findById (id);
		Date date = new Date ();
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format (date);
		planConfig.setStartTime (str);
		planConfig.setStatus ("1");
		String startUser = userAndOrgService.getCurrentUser ().getName ();
		String startUserCode = userAndOrgService.getCurrentUser ().getCode ();
		planConfig.setStartUser (startUser);
		planConfig.setStartUserCode (startUserCode);
		planScheduleService.deployPlanConfigToQuartz (id);
		planConfigService.update (planConfig);
	}

	private ZTreeNode changeFromOrgVo (OrgVo orgVo) {
		ZTreeNode ztn = new ZTreeNode ();
		ztn.setName (orgVo.getName ());
		ztn.setId (orgVo.getCode ());
		ztn.setIsParent (true);
		return ztn;
	}

	private ZTreeNode changeFromUserVo (UserVo userVo) {
		ZTreeNode ztn = new ZTreeNode ();
		ztn.setName (userVo.getName ());
		ztn.setId (userVo.getCode ());
		ztn.setIsParent (false);
		return ztn;
	}
}
