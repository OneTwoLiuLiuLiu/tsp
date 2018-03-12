package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.PoolViewDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PoolView;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PoolViewQo;
import org.springframework.stereotype.Repository;

/**
 * 作业池 jdbc实现类
 *
 * Author: Created by code generator
 * Date: Thu Jan 05 10:51:07 CST 2017
 */
@Repository
public class JpaPoolViewDaoImpl  extends JpaBaseDaoImpl<PoolView,String,PoolViewQo> implements PoolViewDao{

}
