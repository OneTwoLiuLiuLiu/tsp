package com.sunyard.frameworkset.plugin.tsp.manager.biz;

import com.sunyard.frameworkset.core.manager.BaseManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.ZTreeNode;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig.JobConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.job.AllJobVo;
import com.sunyard.frameworkset.plugin.tsp.manager.vo.jobconfig.JobConfigVo;

import java.util.List;
import java.util.Map;

public interface JobConfigManager extends BaseManager<JobConfigVo, JobConfig, String, JobConfigQo> {

	List<ZTreeNode> getZTree (String planConfigId, String nodeId);

	List<Map<String, Object>> getJobConfig (String jobConfigId) throws IllegalAccessException, InstantiationException;

	/*
      * 在树的父节点的后面再追加一个子节点
      */
	String addNewJobConfig (String root, AllJobVo allJobVo, String planConfigId, String type);

	/*

	 */


	/*
	 * 往树节点的前面再添加一个节点
	 */
	String addBeforeJobConfig (String root, AllJobVo allJobVo, String type);


	/*
          * 往树节点的后面再添加一个节点
          */
	String addAfterJobConfig (String root, AllJobVo allJobVo, String type);

	void deleteByJobConfigId(String id);

	List<Map<String, Object>> getSharedPlanConfigs ();

	/*
      * 更新一个作业
      */
	void updateJobConfig(String root, AllJobVo allJobVo,String type);

	/*
      * 根据不同类型转成不同vo
      */
	AllJobVo findVoById (String id);

	void addMoveNew (String treeNodeId, String targetNodeId, String planConfigId);

	void addMoveBefore (String treeNodeId, String targetNodeId, String planConfigId);

	void addMoveAfter (String treeNodeId, String targetNodeId, String planConfigId);
}
