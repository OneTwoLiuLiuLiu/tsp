package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.WaittingViewManager;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingViewQo;
import com.sunyard.frameworkset.web.springmvc.controller.PluginBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 等待表 controller
 *
 * Author: Created by code generator
 * Date: Thu Jan 05 10:51:07 CST 2017
 */
@Controller
@RequestMapping("/tsp/waittingview")
public class WaittingViewController extends PluginBaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger( WaittingViewController.class );

	@Autowired
	private WaittingViewManager waittingViewManager;

	@RequestMapping({"","/list"})
	public String list(WaittingViewQo qo, Model model) {
		model.addAttribute(RESULT, waittingViewManager.queryPageList(qo));
		model.addAttribute("waittingViewQo",qo);
		return "templates/waittingview_list";
	}


}
