package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.bizlog.core.ThreadLocalLogStorage;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.PlanConfigParamManager;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigParamQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanConfigParamVo;
import com.sunyard.frameworkset.web.springmvc.controller.AbstractBaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 *
 * Author: Created by code generator
 * Date: Mon Jan 29 15:16:04 CST 2018
 */
@Controller
@RequestMapping("/tsp/planconfigParam")
public class PlanConfigParamController extends AbstractBaseController {

	private final static Logger LOGGER = LoggerFactory.getLogger( PlanConfigParamController.class );

	@Autowired
	private PlanConfigParamManager planconfigParamManager;

    @RequestMapping({"", "/list"})
    public String list(PlanConfigParamQo planConfigParamQo, Model model) {
		model.addAttribute(RESULT, planconfigParamManager.queryPageList(planConfigParamQo));
		return "templates/planconfigParam";
	}

	@RequestMapping(value = "/input", method = RequestMethod.GET)
	public String add(String id, Model model) {
		if (StringUtils.isNotBlank(id)) {
			model.addAttribute(RESULT, planconfigParamManager.findById(id));
		}
		return "templates/input";
	}

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add (PlanConfigParamVo vo,Model model) {
		vo.setId (null);
		planconfigParamManager.create(vo);
		model.addAttribute(RESULT_SUCCESS,true);
		return StringUtils.endsWith(ThreadLocalLogStorage.getClientInfo().getUrl(), ".json")? "" : "redirect:/planconfigParam/list";
	}

    @RequestMapping("/get")
    public String get (String id, Model model) {
		model.addAttribute(RESULT, planconfigParamManager.findById(id));
		return "";
	}

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update (PlanConfigParamVo vo) {
		PlanConfigParamVo old=planconfigParamManager.findById(vo.getId());
		vo.setPlanConfigId(old.getPlanConfigId());
		planconfigParamManager.update(vo);
		return StringUtils.endsWith(ThreadLocalLogStorage.getClientInfo().getUrl(), ".json")? "" : "redirect:/planconfigParam/list";
	}

    @RequestMapping("/delete")
    public String delete (String...ids) {
		planconfigParamManager.deleteByIds(ids);
		return StringUtils.endsWith(ThreadLocalLogStorage.getClientInfo().getUrl(), ".json")? "" : "redirect:/planconfigParam/list";
	}

}
