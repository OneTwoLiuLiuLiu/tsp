package com.sunyard.frameworkset.plugin.tsp.manager.service;

import com.sunyard.frameworkset.core.service.BaseService;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.ZTreeNode;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig.JobConfigQo;

import java.util.List;

public interface JobConfigService extends BaseService<JobConfig, String, JobConfigQo> {
	/**
	 * 根据parentId和planId查找所有子节点
	 *
	 * @param parentId
	 * @param planId
	 * @return
	 */
	List<ZTreeNode> getChildrenNode (String parentId, String planId);

	/**
	 * 根据jobConfigId获取任务信息
	 *
	 * @param jobConfigId
	 * @return
	 */
	JobConfig getJobInformation (String jobConfigId);

	/**
	 * 根据root和planConfigId查找jobConfig
	 *
	 * @param root
	 * @param planConfigId
	 * @return
	 */
	JobConfig getJobConfigByRoot (String root, String planConfigId);

	/**
	 * 根据planConfigId查找所有jobConfig
	 *
	 * @param planConfigId
	 * @return
	 */
	List<JobConfig> findListByPlanConfigId (String planConfigId);

	/**
	 * 根据planConfigId检查能否发布任务
	 *
	 * @param planConfigId
	 * @return
	 */
	List<Object[]> checkReleaseJobValidation (String planConfigId);

	/**
	 * 根据planConfigId和type查找所有jobConfig
	 *
	 * @param planConfigId
	 * @param type
	 * @return
	 */
	List<JobConfig> getListByPlanConfigIdAndType (String planConfigId, String type);

	List<JobConfig> getJobConfigList (String parentId, String planId);

	JobConfig getLastJobConfig (String parentId, String planId);

	List<ZTreeNode> getAllChildrenNode(String parentId, String planConfigId);
}
