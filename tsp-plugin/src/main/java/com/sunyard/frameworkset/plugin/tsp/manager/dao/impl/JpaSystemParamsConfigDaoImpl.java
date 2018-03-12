package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.SystemParamsConfigDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.SystemParamsConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.SystemParamsConfigQo;
import org.springframework.stereotype.Repository;

/**
 * 系统参数 jdbc实现类
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Repository
public class JpaSystemParamsConfigDaoImpl  extends JpaBaseDaoImpl<SystemParamsConfig,String,SystemParamsConfigQo> implements SystemParamsConfigDao{

}
