package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.JobConnectPoolConfigMannager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobConnectPoolConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.enums.TspPluginMsgEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.JobConnectPoolConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobConnectPoolConfigService;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.JobConnectPoolConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by pc on 2018/3/2.
 */
@Component
@Transactional
public class JobConnectPoolConfigMannagerImpl extends BaseManagerImpl<JobConnectPoolConfigVo,JobConnectPoolConfig,String,JobConnectPoolConfigQo> implements JobConnectPoolConfigMannager {

    @Autowired
    private JobConnectPoolConfigService jobConnectPoolConfigService;

    @Override
    public void addDataSource(JobConnectPoolConfigVo jobConnectPoolConfigVo) {
        try{
            jobConnectPoolConfigVo.setTsp_data_source_config_id(null);
            jobConnectPoolConfigVo.setData_source_id("1234");
            this.create (jobConnectPoolConfigVo);
        } catch (Exception e) {
            throw new TspException(TspPluginMsgEnum.INSERT_ERROR.getCode (), "新增计划配置异常", e);
        }
    }

    @Override
    public void updataDataSource(JobConnectPoolConfigVo jobConnectPoolConfigVo) {
        try{
            JobConnectPoolConfigVo newJobConnectPoolConfigVo=this.findById(jobConnectPoolConfigVo.getTsp_data_source_config_id());
            newJobConnectPoolConfigVo.setData_source_name(jobConnectPoolConfigVo.getData_source_name());
            newJobConnectPoolConfigVo.setDb_name(jobConnectPoolConfigVo.getDb_name());
            newJobConnectPoolConfigVo.setDb_type(jobConnectPoolConfigVo.getDb_type());
            newJobConnectPoolConfigVo.setDb_port(jobConnectPoolConfigVo.getDb_port());
            newJobConnectPoolConfigVo.setDb_user(jobConnectPoolConfigVo.getDb_user());
            newJobConnectPoolConfigVo.setDb_pwd(jobConnectPoolConfigVo.getDb_pwd());
            newJobConnectPoolConfigVo.setEnable_flag(jobConnectPoolConfigVo.getEnable_flag());
            newJobConnectPoolConfigVo.setInit_count(jobConnectPoolConfigVo.getInit_count());
            newJobConnectPoolConfigVo.setMax_count(jobConnectPoolConfigVo.getMax_count());
            super.update(newJobConnectPoolConfigVo);
        }catch (TspException e){
            throw new TspException (TspPluginMsgEnum.UPDATE_ERROR.getCode (), "修改数据源异常", e);
        }
    }
}
