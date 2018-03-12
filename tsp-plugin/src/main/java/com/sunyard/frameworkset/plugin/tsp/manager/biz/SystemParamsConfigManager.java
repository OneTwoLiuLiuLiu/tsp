package com.sunyard.frameworkset.plugin.tsp.manager.biz;

import com.sunyard.frameworkset.core.manager.BaseManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.SystemParamsConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.SystemParamsConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.SystemParamsConfigVo;

/**
 * 系统参数 manager 接口
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
public interface SystemParamsConfigManager extends BaseManager<SystemParamsConfigVo, SystemParamsConfig, String, SystemParamsConfigQo> {
    public String get(String name);
}
