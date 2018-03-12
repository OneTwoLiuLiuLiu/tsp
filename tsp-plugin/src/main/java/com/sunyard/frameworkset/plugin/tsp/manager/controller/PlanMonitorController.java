package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.JobMonitorManager;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.PlanInstanceManager;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.PlanMonitorManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanMonitor;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.ZTreeNode;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanMonitorQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.JobMonitorVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanMonitorVo;
import com.sunyard.frameworkset.util.pages.PageList;
import com.sunyard.frameworkset.web.springmvc.controller.PluginBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


/**
 * 计划监控 controller
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 11:06:54 CST 2017
 */
@Controller
@RequestMapping("/tsp/planmonitor")
public class PlanMonitorController extends PluginBaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger( PlanMonitorController.class );

	@Autowired
	private PlanInstanceManager planInstanceManager;

	@Autowired
	private PlanMonitorManager planMonitorManager;

	@Autowired
	private JobMonitorManager jobMonitorManager;

    @RequestMapping({"","/list"})
    public String list(PlanMonitorQo qo, Model model) {
		//注释掉的为使用native执行原生sql方法
		PageList<PlanMonitorVo> result = planMonitorManager.queryPageList(qo);
		model.addAttribute(RESULT, result);
		model.addAttribute("planMonitorQo",qo);
		return "templates/planmonitor_list";
	}
	//结束
	@RequestMapping("/endplan")
	public String endPlan(String planInstId,Model model){
		try{
			Boolean result = planInstanceManager.endPlan(planInstId);
			model.addAttribute(RESULT_SUCCESS,result);
		}catch (TspException e){
			model.addAttribute(RESULT_MSG, e.getMessage());
		}
		return "end_plan";
	}
	//运行
	@RequestMapping("/runplan")
	public String runningPlan(String planInstId,Model model){
		try{
			Boolean result = planInstanceManager.runPlan(planInstId);
			model.addAttribute(RESULT_SUCCESS,result);
		}catch (TspException e){
			model.addAttribute(RESULT_MSG, e.getMessage());
		}
		return "run_plan";
	}

	//暂停
	@RequestMapping("/pauseplan")
	public String pausePlan(String planInstId,Model model){
		try{
			Boolean result = planInstanceManager.pausePlan(planInstId);
			model.addAttribute(RESULT_SUCCESS,result);
		}catch (TspException e){
			model.addAttribute(RESULT_MSG, e.getMessage());
		}
		return "pause_plan";
	}
	//查看
	@RequestMapping("/scanplan")
	public String scanPlan(JobMonitorQo qo,Model model){
//		List<PlanMonitorVo> result = planMonitorManager.queryPageList(qo);
//		model.addAttribute(RESULT,result);
		PageList<JobMonitorVo> result = jobMonitorManager.queryPageList(qo);
		model.addAttribute(RESULT,result);
		return "scan_plan";
	}

	@RequestMapping("/getplantree")
	public String getPlanTree (PlanConfigQo qo, Model model) {
		List<ZTreeNode> list = new ArrayList<ZTreeNode>();
		try {
			list = jobMonitorManager.getZTree (qo,qo.getId ());
			model.addAttribute (RESULT, list);
		} catch (Exception e) {
			e.printStackTrace ();
		}
		return "templates/planmonitor_list";
	}
}
