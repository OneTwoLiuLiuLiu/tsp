package com.sunyard.frameworkset.plugin.tsp.manager.biz;

import com.sunyard.frameworkset.core.manager.BaseManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.WaittingJob;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.WaittingView;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingJobQo;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.WaittingViewQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.WaittingJobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.WaittingViewVo;

import java.util.List;

/**
 * 等待表 manager 接口
 *
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
public interface WaittingJobManager extends BaseManager<WaittingJob, WaittingJobVo, String, WaittingJobQo> {
}
