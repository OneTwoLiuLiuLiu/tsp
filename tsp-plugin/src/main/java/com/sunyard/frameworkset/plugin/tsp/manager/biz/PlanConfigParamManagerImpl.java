package com.sunyard.frameworkset.plugin.tsp.manager.biz;

import com.sunyard.frameworkset.core.manager.BaseManagerImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfigParam;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigParamQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanConfigParamVo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 *
 * Author: Created by code generator
 * Date: Mon Jan 29 15:16:04 CST 2018
 */
@Component
@Transactional
public class PlanConfigParamManagerImpl extends BaseManagerImpl<PlanConfigParamVo, PlanConfigParam, String, PlanConfigParamQo> implements PlanConfigParamManager {
}
