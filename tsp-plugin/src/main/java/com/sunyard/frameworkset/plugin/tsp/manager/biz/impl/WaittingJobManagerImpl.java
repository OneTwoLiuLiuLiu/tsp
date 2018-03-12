package com.sunyard.frameworkset.plugin.tsp.manager.biz.impl;

import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.WaittingJobManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.WaittingJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.WaittingView;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingJobQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingViewQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.WaittingJobService;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.WaittingJobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.WaittingViewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 等待表 manager
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Component
@Transactional
public class WaittingJobManagerImpl extends BaseManagerImpl<WaittingJob, WaittingJobVo, String, WaittingJobQo> implements WaittingJobManager {
    @Autowired
    private WaittingJobService waittingJobService;
}
