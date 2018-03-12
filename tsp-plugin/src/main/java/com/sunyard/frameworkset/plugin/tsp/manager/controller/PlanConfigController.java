package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.core.runtime.RunBinder;
import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.bizlog.core.ThreadLocalLogStorage;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.PlanConfigManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.ZTreeNode;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.TspPluginMsgEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanConfigVo;
import com.sunyard.frameworkset.web.springmvc.controller.PluginBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 计划配置 controller
 * <p/>
 * Author: Created by code generator
 * Date: Tue Jan 03 10:27:53 CST 2017
 */
@Controller
@RequestMapping("/tsp/planconfig")
public class PlanConfigController extends PluginBaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger (PlanConfigController.class);

	@Autowired
	private PlanConfigManager planConfigManager;

	@RequestMapping({"", "/list"})
	public String list (PlanConfigQo qo, Model model) {
		model.addAttribute (RESULT, planConfigManager.queryPageList (qo));
		model.addAttribute("planConfigQo",qo);
		return "templates/planconfig";
	}


	@RequestMapping("/add")
	public String addPlan (PlanConfigVo planConfigVo, Model model) {
		LOGGER.debug ("新增计划配置");
		planConfigVo.setId (null);
		try {
			planConfigManager.addPlan (planConfigVo);
			model.addAttribute (RESULT, true);
		} catch (TspException e) {
			model.addAttribute (RESULT_SUCCESS, false);
			model.addAttribute (RESULT_MSG, e.getMessage ());
		}
		return "add";
	}

	@RequestMapping("/get")
	public String get (String id, Model model) {
		PlanConfigVo planConfigVo = planConfigManager.findById (id);
		planConfigVo.setJobConfigVos (null);
		model.addAttribute (RESULT, planConfigVo);
		return "get";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update (String suffix, PlanConfigVo vo, Model model) {
		planConfigManager.updatePlan (vo);
		model.addAttribute(RESULT, true);
		try {
			planConfigManager.updatePlan (vo);
			model.addAttribute (RESULT, true);
		} catch (TspException e) {
			model.addAttribute (RESULT_SUCCESS, false);
			model.addAttribute (RESULT_MSG, e.getMessage ());
		}
		return "update";
	}

	@RequestMapping("/delete")
	public String deleteOne (Model model, String... planConfigId) {
		try {
			planConfigManager.deleteByIds (planConfigId);
			model.addAttribute (RESULT, true);

		} catch (Exception e) {
			model.addAttribute (RESULT_SUCCESS, false);
			model.addAttribute (RESULT_MSG, TspPluginMsgEnum.DELETE_ERROR.getName ());
		}
		if (ThreadLocalLogStorage.getClientInfo ().getUrl ().endsWith (".json")) {
			return "";
		}
		return "redirect:" + getPluginUrl ("/tsp/planconfig/list");
	}

	@RequestMapping("/startplan")
	public String startPlan (String id, Model model) {
		try {
			planConfigManager.startPlan (id);
			model.addAttribute (RESULT, true);
		} catch (TspException e) {
			model.addAttribute(RESULT_SUCCESS, false);
			model.addAttribute (RESULT_MSG, e.getMessage ());
		}
		return "start_plan";
	}

	@RequestMapping("/getjobs")
	public String getJobs (PlanConfigVo planConfigvo, Model model) {
		List<JobConfig> lists = planConfigManager.getJobs (planConfigvo.getId ());
		if (RunBinder.hasErrors ()) {
			model.addAttribute (RESULT_SUCCESS, false);
			model.addAttribute (RESULT_MSG, RunBinder.getErrors ().get (0));
		} else {
			if (lists.size () != 0) {
				model.addAttribute (RESULT_SUCCESS, true);
			} else {
				model.addAttribute (RESULT_SUCCESS, false);
				model.addAttribute (RESULT_MSG, TspPluginMsgEnum.PLAN_NO_JOB_ERROR.getName ());
			}
		}
		return "";
	}

	@RequestMapping("/release")
	public String releasePlan (PlanConfigVo planConfigvo, Model model) {
		try {
			planConfigManager.releasePlan(planConfigvo.getId());
			model.addAttribute (RESULT,true);
		} catch (TspException e) {
			model.addAttribute (RESULT_SUCCESS, false);
			model.addAttribute (RESULT_MSG, e.getMessage ());
		}
		return "";
	}

	@RequestMapping("/show")
	public String showVersion (PlanConfigVo planConfigvo, Model model) {
		try {
			planConfigManager.releasePlan (planConfigvo.getId ());
			model.addAttribute(RESULT_SUCCESS,true);
		} catch (TspException e) {
			model.addAttribute(RESULT_SUCCESS,false);
			model.addAttribute(RESULT_MSG,e.getMessage());
		}
		return "";
	}

	@RequestMapping("/restartplan")
	public String reStartPlan(String beginTime,String endTime,String planConfigId,Model model){
		try {
			planConfigManager.runPlan (beginTime, endTime, planConfigId);
			model.addAttribute(RESULT_SUCCESS, true);
		}catch (TspException e){
			model.addAttribute(RESULT_SUCCESS,false);
			model.addAttribute(RESULT_MSG,e.getMessage());
		}
		return "restart_plan";
	}

	@RequestMapping("/stopplan")
	public String stopPlan(String planConfigId,Model model){
		try{
			planConfigManager.stopPlan (planConfigId);
			model.addAttribute(RESULT_SUCCESS,true);
		}catch (TspException e){
			model.addAttribute (RESULT_SUCCESS, false);
			model.addAttribute(RESULT_MSG,e.getMessage());
		}
		return "stop_plan";
	}

	@RequestMapping("/getrecipients")
	@ResponseBody
	public List<ZTreeNode> getRecipients (String id, Model model) {
		List<ZTreeNode> result = new ArrayList<ZTreeNode> ();
		result=planConfigManager.getRecipients (id);
		model.addAttribute (RESULT, result);
		return result;
	}

	@RequestMapping("/addjobconfigbyxml")
	public String addJobConfigByXML (@RequestParam("jobconfigfile") MultipartFile uploadFile, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (request instanceof MultipartHttpServletRequest) {
			try {
				InputStream is = uploadFile.getInputStream ();
				planConfigManager.addJobConfigByXML (is);
			} catch (TspException e) {
				LOGGER.error ("生成作业配置出错");
			} catch (Exception e) {
				LOGGER.error ("生成作业配置出错");
			}
		}
		return "redirect:" + getPluginUrl ("/tsp/planconfig/list");
	}
}
