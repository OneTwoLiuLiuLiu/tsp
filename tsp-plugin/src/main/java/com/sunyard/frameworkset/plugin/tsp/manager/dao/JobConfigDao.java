package com.sunyard.frameworkset.plugin.tsp.manager.dao;

import com.sunyard.frameworkset.core.dao.BaseDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig.JobConfigQo;

import java.util.List;

public interface JobConfigDao extends BaseDao<JobConfig, String, JobConfigQo> {
	/**
	 * 根据parentId和planConfigId查找所有jobConfig
	 *
	 * @param parentId
	 * @param planConfigId
	 * @return
	 */
	List<JobConfig> getJobConfigList (String parentId, String planConfigId);

	/**
	 * 根据planConfigId查找所有jobConfig
	 *
	 * @param planConfigId
	 * @return
	 */
	List<JobConfig> getJobConfigListByPlanConfigId (String planConfigId);

	/**
	 * 检查能否发布任务
	 *
	 * @param planConfigId
	 * @return
	 */
	List<Object[]> checkReleaseJobValidation (String planConfigId);

	/**
	 * 根据root查找jobConfig
	 *
	 * @param root
	 * @param planConfigId
	 * @return
	 */
	JobConfig getJobConfigByRoot (String root, String planConfigId);

	/**
	 * 根据planConfigId和type查找所有jobConfig
	 *
	 * @param planConfigId
	 * @param type
	 * @return
	 */
	List<JobConfig> getListByPlanConfigIdAndType (String planConfigId, String type);

	JobConfig getLastJobConfig (String parentId, String planConfigId);
}
