package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.bizlog.core.ThreadLocalLogStorage;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.JobConnectPoolConfigMannager;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.TspPluginMsgEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobConnectPoolConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.JobConnectPoolConfigVo;
import com.sunyard.frameworkset.web.springmvc.controller.PluginBaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by pc on 2018/3/1.
 */
@Controller
@RequestMapping("tsp/jobconnectpoolconfig")
public class JobConnetPoolConfigController extends PluginBaseController{

    private final static Logger LOGGER = LoggerFactory.getLogger( JobConnetPoolConfigController.class );

    @Autowired
    private JobConnectPoolConfigMannager jobConnectPoolConfigMannager;

    @RequestMapping({"","/list"})
    public String list(JobConnectPoolConfigQo qo, Model model){
        model.addAttribute(RESULT, jobConnectPoolConfigMannager.queryPageList(qo));
        model.addAttribute("JobConnectPoolConfigQo",qo);
        return "templates/jobconnectpoolconfig";
    }

    @RequestMapping("/add")
    public String addDataSource (JobConnectPoolConfigVo jobConnectPoolConfigVo, Model model) {
        LOGGER.debug ("新增作业连接池配置");

        try{
            jobConnectPoolConfigMannager.addDataSource(jobConnectPoolConfigVo);
            model.addAttribute (RESULT, true);
        }catch (TspException e){
            model.addAttribute (RESULT_SUCCESS, false);
            model.addAttribute (RESULT_MSG, e.getMessage ());
        }
        return StringUtils.endsWith(ThreadLocalLogStorage.getClientInfo().getUrl(), ".json")? "" : "redirect:/jobconnectpoolconfig/list";
    }

    @RequestMapping("/delete")
    public String deleteDataSource(Model model,String... jobConnectPoolConfigId){
        try{
            jobConnectPoolConfigMannager.deleteByIds(jobConnectPoolConfigId);
            model.addAttribute(RESULT,true);
        }catch (TspException e){
            model.addAttribute(RESULT_SUCCESS,false);
            model.addAttribute(RESULT_MSG,e.getMessage());
        }

        return StringUtils.endsWith(ThreadLocalLogStorage.getClientInfo().getUrl(), ".json")? "" : "redirect:/jobconnectpoolconfig/list";
    }

    @RequestMapping("/getById")
    public String getById(Model model,String jobConnectPoolConfigId){
        model.addAttribute(RESULT,jobConnectPoolConfigMannager.findById(jobConnectPoolConfigId));
        return "";
    }

    @RequestMapping("/update")
    public String update(JobConnectPoolConfigVo jobConnectPoolConfigVo,Model model){
        try {
            jobConnectPoolConfigMannager.updataDataSource(jobConnectPoolConfigVo);
            model.addAttribute(RESULT,true);
        }catch (TspException e){
            model.addAttribute (RESULT_SUCCESS, false);
            model.addAttribute (RESULT_MSG, e.getMessage ());
        }
        return "update";
    }

}
