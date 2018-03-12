package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.JobPoolManager;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobPoolQo;
import com.sunyard.frameworkset.web.springmvc.controller.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 作业池 controller
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Controller
@RequestMapping("/tsp/jobpool")
public class JobPoolController extends AbstractBaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger( JobPoolController.class );

	@Autowired
	private JobPoolManager jobPoolManager;

    @RequestMapping({"","/list"})
    public String list(JobPoolQo qo, Model model) {
		model.addAttribute(RESULT, jobPoolManager.queryPageList(qo));
		return "templates/jobpool_list";
	}


}
