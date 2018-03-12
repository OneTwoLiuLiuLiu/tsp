package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.PoolViewManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PoolView;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PoolViewQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PoolViewVo;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * 作业池 manager
 *
 * Author: Created by code generator
 * Date: Thu Jan 05 10:51:07 CST 2017
 */
@Component
@Transactional
public class PoolViewManagerImpl extends BaseManagerImpl<PoolViewVo, PoolView, String, PoolViewQo> implements PoolViewManager {
}
