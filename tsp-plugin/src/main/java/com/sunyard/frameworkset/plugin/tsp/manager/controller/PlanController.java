package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.PlanManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanVo;
import com.sunyard.frameworkset.util.pages.PageList;
import com.sunyard.frameworkset.web.springmvc.controller.PluginBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * 计划 controller
 *
 * Author: Created by code generator
 * Date: Wed Jan 04 14:25:49 CST 2017
 */
@Controller
@RequestMapping("/tsp/plan")
public class PlanController extends PluginBaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger( PlanController.class );

	@Autowired
	private PlanManager planManager;

    @RequestMapping({"","/list"})
    public String list(PlanQo qo, Model model) {
		PageList<PlanVo> planVos = planManager.queryPageList(qo);
		model.addAttribute(RESULT, planVos);
		return "templates/plan";
	}
}
