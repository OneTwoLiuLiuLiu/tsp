package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.WaittingViewDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.WaittingView;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingViewQo;
import org.springframework.stereotype.Repository;

/**
 * 等待表 jdbc实现类
 *
 * Author: Created by code generator
 * Date: Thu Jan 05 10:51:07 CST 2017
 */
@Repository
public class JpaWaittingViewDaoImpl  extends JpaBaseDaoImpl<WaittingView,String,WaittingViewQo> implements WaittingViewDao{

}
