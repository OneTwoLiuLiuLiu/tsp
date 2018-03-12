package com.sunyard.frameworkset.plugin.tsp.manager.service.impl;

import com.sunyard.frameworkset.core.service.BaseServiceImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.WaittingJobDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.WaittingView;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingViewQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.WaittingJobService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.WaittingJob;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingJobQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.WaittingJobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.WaittingViewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 等待表 service
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Service
public class WaittingJobServiceImpl extends BaseServiceImpl<WaittingJob, String, WaittingViewQo> implements WaittingJobService {
    @Autowired
    private WaittingJobDao waittingJobDao;
}
