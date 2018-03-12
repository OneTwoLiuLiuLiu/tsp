package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.plugin.tsp.manager.qo.SystemParamsConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.SystemParamsConfigVo;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.SystemParamsConfigManager;
import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import com.sunyard.frameworkset.web.springmvc.controller.AbstractBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 系统参数 controller
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Controller
@RequestMapping("/tsp/systemParamsConfig")
public class SystemParamsConfigController extends AbstractBaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger( SystemParamsConfigController.class );

	@Autowired
	private SystemParamsConfigManager systemParamsConfigManager;

    @RequestMapping({"","/list"})
    public String list(SystemParamsConfigQo qo, Model model) {
		model.addAttribute(RESULT, systemParamsConfigManager.queryPageList(qo));
		return "templates/systemParamsConfig";
	}

	@RequestMapping(value = "/input", method = RequestMethod.GET)
	public String add(String id, Model model) {
		if (StringUtils.isNotBlank(id)) {
			model.addAttribute(RESULT, systemParamsConfigManager.findById(id));
		}
		return "templates/input";
	}

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add (String suffix, SystemParamsConfigVo vo) {
		vo.setId (null);
		systemParamsConfigManager.create(vo);
		return StringUtils.equals(suffix, ".json") ? "" : "redirect:/systemParamsConfig/list";
	}

    @RequestMapping("/get")
    public String get (String id, Model model) {
		model.addAttribute(RESULT, systemParamsConfigManager.findById(id));
		return "";
	}

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update (String suffix, SystemParamsConfigVo vo) {
		systemParamsConfigManager.update(vo);
		return StringUtils.equals(suffix, ".json") ? "" : "redirect:/systemParamsConfig/list";
	}

    @RequestMapping("/delete")
    public String delete (String suffix, String...ids) {
		systemParamsConfigManager.deleteByIds(ids);
		return StringUtils.equals(suffix, ".json") ? "" : "redirect:/systemParamsConfig/list";
	}

}
