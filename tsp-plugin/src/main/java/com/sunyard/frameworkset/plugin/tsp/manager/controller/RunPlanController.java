package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.PlanInstanceManager;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.RunPlanManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanInstance;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanInstanceQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.RunPlanQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanInstanceVo;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import com.sunyard.frameworkset.util.pages.PageList;
import com.sunyard.frameworkset.web.springmvc.controller.PluginBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * 手动执行计划 controller
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Controller
@RequestMapping("/tsp/runplan")
public class RunPlanController extends PluginBaseController {

	//计划配置中错误代码
	private static final String ERRORCODE="PAPP001";

	private final static Logger LOGGER = LoggerFactory.getLogger( RunPlanController.class );

	@Autowired
	private RunPlanManager runPlanManager;

	@Autowired
	private PlanInstanceManager planInstanceManager;

    @RequestMapping({"","/list"})
    public String list(RunPlanQo qo, Model model) {
		model.addAttribute(RESULT, runPlanManager.queryPageList(qo));
		return "templates/runplan-list";
	}

	@RequestMapping("/rerunplan")
	public String reRunPlan(String runPlanId,Model model){
		try {
			runPlanManager.reRunPlan(runPlanId);
			model.addAttribute(RESULT_SUCCESS,true);
		}catch (TspException e){
//			TaskSchedulingPlatformException tspe=new TaskSchedulingPlatformException();
//			tspe.setErrorCode(ERRORCODE);
//			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
//			tspe.setBriefDescription(e.getMessage());
			model.addAttribute(RESULT_SUCCESS, false);
			model.addAttribute(RESULT_MSG,e.getMessage());
		}
		return "re_run_plan";
	}

	@RequestMapping("/stopplan")
	public String stopPlan(String runPlanId,Model model){
		try{
			runPlanManager.stopPlan(runPlanId);
			model.addAttribute(RESULT_SUCCESS,true);
		}catch (TspException e){
//			TaskSchedulingPlatformException tspe=new TaskSchedulingPlatformException();
//			tspe.setErrorCode(ERRORCODE);
//			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
//			tspe.setBriefDescription(e.getMessage());
			model.addAttribute(RESULT_SUCCESS, false);
			model.addAttribute(RESULT_MSG,e.getMessage());
		}
		return "stop_plan";
	}

	@RequestMapping("/getplaninstance")
	public String getPlanInstance(PlanInstanceQo qo,Model model){
		model.addAttribute(RESULT, planInstanceManager.queryPageList(qo));
		return "get_plan_instance";
	}
}
