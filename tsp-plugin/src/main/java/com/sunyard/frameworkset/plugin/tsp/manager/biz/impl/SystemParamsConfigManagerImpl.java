package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.SystemParamsConfigManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.SystemParamsConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.SystemParamsConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.SystemParamsConfigVo;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * 系统参数 manager
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Component
@Transactional
public class SystemParamsConfigManagerImpl extends BaseManagerImpl<SystemParamsConfigVo, SystemParamsConfig, String, SystemParamsConfigQo> implements SystemParamsConfigManager {
    @Override
    public String get(String name) {
        return null;
    }
}
