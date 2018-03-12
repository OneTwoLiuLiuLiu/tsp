package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.JobConfigManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.ZTreeNode;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig.JobConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.AllJobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.TurnoverJobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig.*;
import com.sunyard.frameworkset.util.converter.BeanConverter;
import com.sunyard.frameworkset.web.springmvc.controller.PluginBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 计划配置 controller
 * <p/>
 * Author: Created by code generator
 * Date: Tue Jan 03 10:27:53 CST 2017
 */
@Controller
@RequestMapping("/tsp/jobconfig")
public class JobConfigController extends PluginBaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger (JobConfigController.class);

	private static final String ERRORCODE = "PAPP001";

	@Autowired
	private JobConfigManager jobConfigManager;

	@RequestMapping("/getjobpage")
	public String list (JobConfigQo qo, Model model) {
		model.addAttribute ("planConfigId", qo.getId ());
		return "templates/jobconfig";
	}

	@RequestMapping("/getjobtree")
	public String getJobTree (JobConfigQo qo, Model model) {
		List<ZTreeNode> list = new ArrayList<ZTreeNode> ();
		try {
			list = jobConfigManager.getZTree (qo.getPlanConfigId (), qo.getId ());
			model.addAttribute (RESULT, list);
		} catch (Exception e) {
			e.printStackTrace ();
		}
		return "templates/jobconfig";
	}

	@RequestMapping("/getjobconfig")
	public String getJobConfig (JobConfigQo qo, Model model) throws IllegalAccessException, InstantiationException {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>> ();
		lists = jobConfigManager.getJobConfig (qo.getId ());
		model.addAttribute (RESULT, lists);
		return "templates/jobconfig";
	}

	//新增不同type的作业
	@RequestMapping("/addnewjobconfig")
	public String addNewJobConfig (AllJobVo allJobVo, String root, String planConfigId, Model model) throws IllegalAccessException, InstantiationException {
		jobConfigManager.addNewJobConfig (root, allJobVo, planConfigId, allJobVo.getJobType ());
		return "redirect:" + getPluginUrl ("/tsp/jobconfig/getjobpage") + "?id=" + planConfigId;
	}

	//新增前不同type的作业
	@RequestMapping("/addbeforejobconfig")
	public String addBeforeJobConfig (String root, AllJobVo allJobVo, String planConfigId) throws IllegalAccessException, InstantiationException {
		jobConfigManager.addBeforeJobConfig (root, allJobVo, allJobVo.getJobType ());
		return "redirect:" + getPluginUrl ("/tsp/jobconfig/getjobpage") + "?id=" + planConfigId;
	}

	//新增后不同type的作业
	@RequestMapping("/addafterjobconfig")
	public String addAfterJobConfig (String root, AllJobVo allJobVo, String planConfigId) throws IllegalAccessException, InstantiationException {
		jobConfigManager.addAfterJobConfig (root, allJobVo, allJobVo.getJobType ());
		return "redirect:" + getPluginUrl ("/tsp/jobconfig/getjobpage") + "?id=" + planConfigId;
	}

	@RequestMapping({"/deletejobconfig"})
	public String deleteJobConfig (String id, String planConfigId,Model model) {
		try {
			jobConfigManager.deleteByJobConfigId(id);
			model.addAttribute(RESULT_SUCCESS,true);
		}catch (TspException e){
			model.addAttribute(RESULT_SUCCESS,false);
			model.addAttribute(RESULT_MSG,e.getMessage());
		}
		return "deletejobconfig";
	}

	@RequestMapping({"/getplanconfigs"})
	public String getPlanConfigs (Model model) {
		List<Map<String, Object>> list = jobConfigManager.getSharedPlanConfigs ();
		model.addAttribute (RESULT, list);
		return "getplanconfigs";
	}

	@RequestMapping({"/getonejobconfig"})
	public String getOneJobConfig(String id,String type, Model model){
		try{
			AllJobVo allJobVo = jobConfigManager.findVoById (id);
		if(type.equals("jar")){
			JarJobConfigVo jarJobConfigVo= BeanConverter.convert(new JarJobConfigVo(), allJobVo);
			model.addAttribute(RESULT, jarJobConfigVo);
		}else if(type.equals("kjb")){
			KjbJobConfigVo kjbJobConfigVo= BeanConverter.convert(new KjbJobConfigVo(), allJobVo);
			model.addAttribute(RESULT, kjbJobConfigVo);
		}else  if(type.equals("ktr")){
			KtrJobConfigVo ktrJobConfigVo= BeanConverter.convert(new KtrJobConfigVo(), allJobVo);
			model.addAttribute(RESULT, ktrJobConfigVo);
		}else  if(type.equals("shell")){
			ShellJobConfigVo shellJobConfigVo= BeanConverter.convert(new ShellJobConfigVo(), allJobVo);
			model.addAttribute(RESULT, shellJobConfigVo);
		}else  if(type.equals("storepro")){
			StoreProJobConfigVo storeProJobConfigVo= BeanConverter.convert(new StoreProJobConfigVo(), allJobVo);
			model.addAttribute(RESULT, storeProJobConfigVo);
		}else  if(type.equals("bat")){
			BatJobConfigVo batJobConfigVo= BeanConverter.convert(new BatJobConfigVo(), allJobVo);
			model.addAttribute(RESULT, batJobConfigVo);
		}else  if(type.equals("exe")){
			ExeJobConfigVo exeJobConfigVo= BeanConverter.convert(new ExeJobConfigVo(), allJobVo);
			model.addAttribute(RESULT, exeJobConfigVo);
		}else  if(type.equals("http")){
			HttpJobConfigVo httpJobConfigVo= BeanConverter.convert(new HttpJobConfigVo(), allJobVo);
			model.addAttribute(RESULT, httpJobConfigVo);
		}else if(type.equals("turnover")){
			TurnoverJobConfigVo turnoverVo= BeanConverter.convert(new TurnoverJobConfigVo(), allJobVo);
			model.addAttribute(RESULT, turnoverVo);
		} else if (type.equals("task")) {
			TaskJobConfigVo taskJobConfigVo = BeanConverter.convert(new TaskJobConfigVo(), allJobVo);
			model.addAttribute(RESULT, taskJobConfigVo);
		}else{
			DataStageJobConfigVo dataStageJobConfigVo= BeanConverter.convert(new DataStageJobConfigVo(), allJobVo);
			model.addAttribute(RESULT, dataStageJobConfigVo);
		}
			model.addAttribute(RESULT_SUCCESS, true);
		} catch (TspException e) {
			model.addAttribute (RESULT_SUCCESS, false);
			model.addAttribute (RESULT_MSG, e.getMessage ());
		}
		return "get_One_Job_Config";
	}

	@RequestMapping("/updatejobconfig")
	public String updateJobConfig(String root, AllJobVo allJobVo, String planConfigId,Model model) throws IllegalAccessException, InstantiationException{
		try {
			jobConfigManager.updateJobConfig(root, allJobVo, allJobVo.getJobType());
			model.addAttribute (RESULT_MSG, "更新成功");
			model.addAttribute(RESULT_SUCCESS,true);
		}catch (TspException e) {
			model.addAttribute (RESULT_SUCCESS, false);
			model.addAttribute (RESULT_MSG, e.getMessage ());
		}

		return "update_job_config";
	}

	@RequestMapping("/addmovenewjob")
	public String addMoveNewJob (String treeNodeId, String targetNodeId, String planConfigId, Model model) {
		try {
			jobConfigManager.addMoveNew (treeNodeId, targetNodeId, planConfigId);
			model.addAttribute (RESULT_SUCCESS, true);
		} catch (TspException e) {
			model.addAttribute (RESULT_SUCCESS, false);
			model.addAttribute (RESULT_MSG, e.getMessage ());
		}
		return "addmovenewjob";
	}

	@RequestMapping("/addmovebeforejob")
	public String addMoveBeforeJob (String treeNodeId, String targetNodeId, String planConfigId, Model model) {
		try {
			jobConfigManager.addMoveBefore (treeNodeId, targetNodeId, planConfigId);
			model.addAttribute (RESULT_SUCCESS, true);
		} catch (TspException e) {
			model.addAttribute (RESULT_SUCCESS, false);
			model.addAttribute (RESULT_MSG, e.getMessage ());
		}
		return "addmovebeforejob";
	}

	@RequestMapping("/addmoveafterjob")
	public String addMoveAfterJob (String treeNodeId, String targetNodeId, String planConfigId, Model model) {
		try {
			jobConfigManager.addMoveAfter (treeNodeId, targetNodeId, planConfigId);
			model.addAttribute (RESULT_SUCCESS, true);
		} catch (TspException e) {
			model.addAttribute (RESULT_SUCCESS, false);
			model.addAttribute (RESULT_MSG, e.getMessage ());
		}
		return "addmoveafterjob";
	}
}
