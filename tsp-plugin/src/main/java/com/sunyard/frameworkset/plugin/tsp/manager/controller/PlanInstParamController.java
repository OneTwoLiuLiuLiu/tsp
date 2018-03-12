package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.PlanInstParamManager;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanInstParamQo;
import com.sunyard.frameworkset.web.springmvc.controller.PluginBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 计划监控 controller
 *
 * Author: Created by code generator
 * Date: Wed Jan 04 13:22:34 CST 2017
 */
@Controller
@RequestMapping("/tsp/planinstParam")
public class PlanInstParamController extends PluginBaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger( PlanInstParamController.class );

	@Autowired
	private PlanInstParamManager planInstParamManager;

    @RequestMapping({"","/list"})
    public String list(PlanInstParamQo qo, Model model) {
		model.addAttribute (RESULT, planInstParamManager.queryPageList (qo));
		return "templates/planinstParam";
	}

}
