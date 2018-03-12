package com.sunyard.frameworkset.plugin.tsp.manager.controller;

import com.sunyard.frameworkset.log.Logger;
import com.sunyard.frameworkset.log.LoggerFactory;
import com.sunyard.frameworkset.plugin.bizlog.core.ThreadLocalLogStorage;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.JobServerConfigManager;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobServerConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.JobServerConfigVo;
import com.sunyard.frameworkset.util.pages.PageList;
import com.sunyard.frameworkset.util.result.GenericResult;
import com.sunyard.frameworkset.web.springmvc.controller.PluginBaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2016/12/29 0029 上午 9:52
 * version $Id: JobServerConfigController.java, v 0.1 Exp $
 */
@Controller
@RequestMapping("/tsp/jobserverconfig")
public class JobServerConfigController extends PluginBaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(JobServerConfigController.class);
    @Autowired
    private JobServerConfigManager jobServerConfigManager;

    @RequestMapping({"","/list"})
    public String list(JobServerConfigQo qo,Model model){
        //qo.setPageSize(1);
        PageList<JobServerConfigVo> result=jobServerConfigManager.queryPageList(qo);

        //处理作业服务器状态
        for(JobServerConfigVo vo :result){
            Boolean isonline=false;
            try {
                 isonline = jobServerConfigManager.jobServerIsAlive(vo.getHostname());
            }catch (Exception e){
                isonline=false;
            }
            if(isonline)
                vo.setIsOnline("在线");
            else
                vo.setIsOnline("断开");
        }

        model.addAttribute(RESULT,result);
        model.addAttribute( "qo", qo);
        return "templates/jobserverconfig_list";
    }

    //判断作业服务器的连接是否存在
    @RequestMapping({"/jobserverisalive"})
    public String jobServerIsAlive(String hostname, Model model){
        model.addAttribute(RESULT,jobServerConfigManager.jobServerIsAlive(hostname));
        return "add";
    }


    @RequestMapping({"/updatejobserver"})
    public String updateJobServer(JobServerConfigVo jobServerConfigVo,Model model){
        if(!CollectionUtils.isEmpty(jobServerConfigVo.getJobTypes())){
            //设置jobServerConfig的 runJobType 和 runJobTypeCn
            String runJobTypes="";
            for(String jobType: jobServerConfigVo.getJobTypes()){
                runJobTypes+=jobType+",";
            }
            //移除最后一个英文逗号
            if(StringUtils.endsWith(runJobTypes,",")) runJobTypes=runJobTypes.substring(0,runJobTypes.length()-1);
            jobServerConfigVo.setRunJobType(runJobTypes);
            jobServerConfigManager.refreshClientResource(jobServerConfigVo);
        }else{
            //如果都不选，需要做处理
        }
        return "redirect:" + getPluginUrl("/tsp/jobserverconfig/list");
    }

    /**
     * 删除作业服务器
     * @param jobServerConfigVo
     * @return
     */
    @RequestMapping("/delete")
    public String delete(JobServerConfigVo jobServerConfigVo, Model model) {
        try {
            jobServerConfigManager.deleteById(jobServerConfigVo.getId());
            model.addAttribute(RESULT_SUCCESS, true);
        }catch (TspException e){
            model.addAttribute(RESULT_SUCCESS, false);
            model.addAttribute(RESULT_MSG, e.getMessage());
        }
        return "delete";
    }

    /**
     * 删除多个作业服务器
     * @param jobServerConfigId
     * @return
     */
    @RequestMapping("/deleteall")
    public String deleteAll(Model model,String... jobServerConfigId) {
        try {
            jobServerConfigManager.deleteByIds(jobServerConfigId);
            model.addAttribute(RESULT_SUCCESS, true);
        }catch (TspException e){
            model.addAttribute(RESULT_SUCCESS, false);
            model.addAttribute(RESULT_MSG, e.getMessage());
        }
        return "redirect:" + getPluginUrl("/tsp/jobserverconfig/list");
    }

    /**
     * 更新作业服务器状态：启用 不启用
     * @param jobServerConfigVo
     * @param model
     * @return
     */
    @RequestMapping("/changestatus")
    public String changestatus(JobServerConfigVo jobServerConfigVo, Model model) {
        try {
            Boolean ret = jobServerConfigManager.changestatus(jobServerConfigVo.getId(), jobServerConfigVo.getStatus());
            if (ret) {
                model.addAttribute(RESULT_SUCCESS, true);
            }
        }catch (TspException e){
            model.addAttribute(RESULT_SUCCESS, false);
            model.addAttribute(RESULT_MSG, e.getMessage());
        }
        return "changestatus";
    }

}
