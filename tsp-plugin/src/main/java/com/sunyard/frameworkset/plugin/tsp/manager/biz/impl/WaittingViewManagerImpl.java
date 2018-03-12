package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.WaittingViewManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.WaittingView;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingViewQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.WaittingViewVo;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * 等待表 manager
 *
 * Author: Created by code generator
 * Date: Thu Jan 05 10:51:07 CST 2017
 */
@Component
@Transactional
public class WaittingViewManagerImpl extends BaseManagerImpl<WaittingViewVo, WaittingView, String, WaittingViewQo> implements WaittingViewManager {
}
