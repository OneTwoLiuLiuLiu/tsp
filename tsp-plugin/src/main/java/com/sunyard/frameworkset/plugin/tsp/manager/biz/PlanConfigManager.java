package com.sunyard.frameworkset.plugin.tsp.manager.biz;

import com.sunyard.frameworkset.core.manager.BaseManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.PlanConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.ZTreeNode;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.PlanConfigVo;

import java.io.InputStream;
import java.util.List;

/**
 * 计划配置 manager 接口
 * <p/>
 * Author: Created by code generator
 * Date: Tue Jan 03 10:27:53 CST 2017
 */
public interface PlanConfigManager extends BaseManager<PlanConfigVo, PlanConfig, String, PlanConfigQo> {
	void addPlan (PlanConfigVo vo);

	void releasePlan (String id);

	/**
	 * 启动计划
	 *
	 * @param id
	 */
	void startPlan (String id);

	/**
	 * 根据planConfigId查找所有任务，判断能否发布
	 *
	 * @param planConfigId
	 * @return
	 */
	List<JobConfig> getJobs (String planConfigId);

	/**
	 * 运行计划
	 * @param beginTime
	 * @param endTime
	 * @param planConfigId
	 */
	void runPlan(String beginTime,String endTime,String planConfigId);

	/**
	 * 结束计划
	 * @param planConfigId
	 */
	void stopPlan(String planConfigId);

	List<ZTreeNode> getRecipients(String id);

	void updatePlan(PlanConfigVo planConfigVo);

	void addJobConfigByXML (InputStream is);
}
