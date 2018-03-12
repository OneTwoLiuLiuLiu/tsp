package com.sunyard.frameworkset.plugin.tsp.manager.service.impl;

import com.sunyard.frameworkset.core.service.BaseServiceImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.PlanDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.NoticeWayEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.StartWayEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.PlanService;
import com.sunyard.frameworkset.plugin.tsp.manager.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 计划 service
 * <p/>
 * Author: Created by code generator
 * Date: Wed Jan 04 14:25:48 CST 2017
 */
@Service
public class PlanServiceImpl extends BaseServiceImpl<Plan, String, PlanQo> implements PlanService {

	@Autowired
	private PlanDao planDao;

	@Override
	public Integer getMaxVersionByPlanConfigId (String planConfigId) {
		return planDao.getMaxVersionByPlanConfigId (planConfigId);
	}

	@Override
	public Plan findByPlanConfigIdAndVersion (String planConfigId, Integer version) {
		return planDao.findByPlanConfigIdAndVersion (planConfigId, version);
	}


	@Override
	@Transactional
	public Plan releasePlan (String planConfigId, PlanConfig planConfig, String deployUser, String deployUserCode) {
		Plan plan = new Plan ();
		plan.setName (planConfig.getName ());
		if (NoticeWayEnum.Message.getCode ().equals (planConfig.getNoticeWay ().trim ())) {
			plan.setNoticeWay (NoticeWayEnum.Message.getCode ());
		} else if (NoticeWayEnum.MAIL.getCode ().equals (planConfig.getNoticeWay ().trim ())) {
			plan.setNoticeWay (NoticeWayEnum.MAIL.getCode ());
		} else if (NoticeWayEnum.MAIL_AND_MESSAGE.getCode ().equals (planConfig.getNoticeWay ().trim ())) {
			plan.setNoticeWay (NoticeWayEnum.MAIL_AND_MESSAGE.getCode ());
		}
		if(StartWayEnum.NewTaskCancel.getCode().equals(planConfig.getStartKind())){
 			plan.setStartKind(StartWayEnum.NewTaskCancel.getCode());
		}else if(StartWayEnum.NewTaskWait.getCode().equals(planConfig.getStartKind())){
			plan.setStartKind(StartWayEnum.NewTaskWait.getCode());
		}else if(StartWayEnum.NewTaskExcute.getCode().equals(planConfig.getStartKind())){
			plan.setStartKind(StartWayEnum.NewTaskExcute.getCode());
		}
		plan.setPeriod (planConfig.getPeriod ());
		plan.setRecipients (planConfig.getRecipients ());
		plan.setRecipientsCn (planConfig.getRecipientsCn ());
		plan.setStartKind(planConfig.getStartKind());
		plan.setPlanConfig (planConfig);
		plan.setDeployTime (DateUtil.getCurrDate ("yyyy-MM-dd HH:mm:ss"));
		Integer maxversion = this.getMaxVersionByPlanConfigId (planConfigId);
		plan.setVersion (maxversion + 1);
		plan.setDeployUser (deployUser);
		plan.setDeployUserCode (deployUserCode);
		this.create (plan);
		return plan;
	}

	@Override
	public Plan getPlanByPlanConfigId (String planConfigId) {
		return planDao.getPlanByPlanConfigId (planConfigId);
	}
}
