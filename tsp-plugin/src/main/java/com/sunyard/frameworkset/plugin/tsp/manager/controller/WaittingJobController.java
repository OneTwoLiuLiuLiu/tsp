package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.WaittingJobManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.WaittingView;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingJobQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingViewQo;
import com.sunyard.frameworkset.web.springmvc.controller.AbstractBaseController;
import com.sunyard.frameworkset.web.springmvc.controller.PluginBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 等待表 controller
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Controller
@RequestMapping("/tsp/waittingjob")
public class WaittingJobController extends PluginBaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger( WaittingJobController.class );

	@Autowired
	private WaittingJobManager waittingJobManager;

    @RequestMapping({"","/list"})
    public String list(WaittingViewQo qo, Model model) {
		return "templates/waitingjob_list";
	}


}
