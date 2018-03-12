package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.JobMonitorManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.ZTreeNode;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.JobMonitorVo;
import com.sunyard.frameworkset.web.springmvc.controller.PluginBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.summercool.hsf.exception.HsfOperationException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tsp/jobmonitor")
public class JobMonitorController extends PluginBaseController {
	private final static Logger LOGGER = LoggerFactory.getLogger (JobMonitorController.class);

	// 作业监控中错误代码
	private static final String ERRORCODE = "PAPP004";

	@Autowired
	private  JobMonitorManager jobMonitorManager;


	@RequestMapping({"","/list"})
	public String list(JobMonitorQo qo,Model model){
		List<JobMonitorVo> result=jobMonitorManager.queryPageList(qo);
		model.addAttribute("jobMonitorQo",qo);
		model.addAttribute(RESULT,result);
		return "templates/jobmonitor_list";
	}
	@RequestMapping("/restart")
	public String restart(JobMonitorQo qo,Model model){
		try{
			boolean result=jobMonitorManager.reRun(qo);
			if(result){
				model.addAttribute(RESULT_SUCCESS,true);
			}else{
				model.addAttribute(RESULT_SUCCESS,false);
				model.addAttribute(RESULT_MSG,"作业不存在");
			}
		}catch (TspException e){
			model.addAttribute(RESULT_SUCCESS,false);
			model.addAttribute(RESULT_MSG, e.getMessage());
		}
		return "re_start";
	}
    @RequestMapping("/manualpass")
	public String manualPass(String id,Model model){
		try{
			boolean result=jobMonitorManager.manualPass(id);
			if(result){
				model.addAttribute(RESULT_SUCCESS,true);
			}else{
				model.addAttribute(RESULT_SUCCESS,false);
				model.addAttribute(RESULT_MSG,"作业不存在");
			}
		}catch (TspException e){
			model.addAttribute(RESULT_SUCCESS,false);
			model.addAttribute(RESULT_MSG, e.getMessage());
		}
		return "manual_pass";

	}
    @RequestMapping("/stop")
	public String stopJob(JobMonitorQo qo,Model model){
		try{
			boolean result=jobMonitorManager.stopJob(qo);
			if(result){
				model.addAttribute(RESULT_SUCCESS,true);
			}else{
				model.addAttribute(RESULT_SUCCESS,false);
				model.addAttribute(RESULT_MSG,"作业不存在");
			}
		}catch (TspException e){
			model.addAttribute(RESULT_SUCCESS,false);
			model.addAttribute(RESULT_MSG, e.getMessage());
		}
		return "stop_job";
	}
	@RequestMapping("/pause")
	public String pauseJob(JobMonitorQo qo,Model model){
		try{
			boolean result=jobMonitorManager.pauseJob(qo);
			if(result){
				model.addAttribute(RESULT_SUCCESS,true);
			}else{
				model.addAttribute(RESULT_SUCCESS,false);
				model.addAttribute(RESULT_MSG,"作业不存在");
			}
		} catch (TspException e){
			model.addAttribute(RESULT_SUCCESS,false);
			model.addAttribute(RESULT_MSG, e.getMessage());
		}
		return "stop_job";
	}
	@RequestMapping("/continue")
	public String continueJob(JobMonitorQo qo,Model model){
		try{
			boolean result=jobMonitorManager.continueRun(qo);
			if(result){
				model.addAttribute(RESULT_SUCCESS,true);
			}else{
				model.addAttribute(RESULT_SUCCESS,false);
				model.addAttribute(RESULT_MSG,"作业不存在");
			}
		}catch (TspException e){
			model.addAttribute(RESULT_SUCCESS,false);
			model.addAttribute(RESULT_MSG, e.getMessage());
		}
		return "continue_job";
	}
	@RequestMapping("/getjoblog")
	public String getJobLog(String hostname,String planInstId,String jobId,Model model){
		try {
			String context = jobMonitorManager.getJobLog(hostname, planInstId, jobId);
			model.addAttribute(RESULT_SUCCESS,true);
			model.addAttribute(RESULT,context==null?"":context);
		}catch (Exception e){
			model.addAttribute(RESULT_SUCCESS,false);
			model.addAttribute(RESULT_MSG, e.getMessage());
		}
		return "get_job_log";
	}

	@RequestMapping("/getplantree")
	public String getPlanTree (PlanConfigQo qo, Model model) {
		List<ZTreeNode> list = new ArrayList<ZTreeNode>();
		try {
			list = jobMonitorManager.getZTrees (qo,qo.getId ());
			model.addAttribute (RESULT, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "templates/jobmonitor_list";
	}
}