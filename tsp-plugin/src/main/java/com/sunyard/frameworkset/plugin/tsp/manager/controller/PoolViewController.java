package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.PoolViewManager;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PoolViewQo;
import com.sunyard.frameworkset.web.springmvc.controller.PluginBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 作业池 controller
 *
 * Author: Created by code generator
 * Date: Thu Jan 05 10:51:07 CST 2017
 */
@Controller
@RequestMapping("tsp/poolview")
public class PoolViewController extends PluginBaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger( PoolViewController.class );

	@Autowired
	private PoolViewManager poolViewManager;

    @RequestMapping({"","/list"})
    public String list(PoolViewQo qo, Model model) {
		model.addAttribute(RESULT, poolViewManager.queryPageList(qo));
		model.addAttribute("poolViewQo",qo);
		return "templates/poolview_list";
	}


}
