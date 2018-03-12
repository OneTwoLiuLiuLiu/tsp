package com.sunyard.frameworkset.plugin.tsp.manager.service.impl;

import com.sunyard.frameworkset.core.service.BaseServiceImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.JobConfigDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.ZTreeNode;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.ParallelJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.SerialJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.TaskJobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig.JobConfigQo;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.DiscriminatorValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JobConfigServiceImpl extends BaseServiceImpl<JobConfig, String, JobConfigQo> implements JobConfigService {

	@Autowired
	JobConfigDao jobConfigDao;

	public List<ZTreeNode> getChildrenNode (String parentId, String planId) {

		List<JobConfig> jobs = jobConfigDao.getJobConfigList (parentId, planId);
//		List<JobConfig> jobsSort = this.sort (jobs);
		List<ZTreeNode> lists = this.tranJobConfigToZtreeNode (jobs);
		for (ZTreeNode list : lists) {
			list.setChildren (this.getChildrenNode (list.getId (), planId));
		}
		return lists;
	}

	public List<ZTreeNode> getAllChildrenNode(String parentId, String planId) {

		List<JobConfig> jobs = jobConfigDao.getJobConfigList (parentId, planId);
		List<ZTreeNode> lists = this.tranJobConfigToZtreeNode(jobs);

		for(int i=0;i<jobs.size();i++){
			String parId = jobs.get(i).getId();
			List<ZTreeNode> list1 = getChildrenNode(parId,planId);
			if(list1.size()>0){
				lists.addAll(list1);
			}
		}


		for (ZTreeNode list : lists) {
			list.setChildren(this.getChildrenNode(list.getId(), planId));
		}
		return lists;
	}

	private List<JobConfig> sort (List<JobConfig> jobs) {
		// 排序
		List<JobConfig> list = new ArrayList<JobConfig> ();
		JobConfig job = null;
		for (JobConfig jobConfig : jobs) {
			if (jobConfig.getPrevJobId () == null) {
				list.add (jobConfig);
				job = jobConfig;
				jobs.remove (jobConfig);
				break;
			}
		}
		for (int k = 0; k < jobs.size (); k++) {
			for (int j = 0; j < jobs.size (); j++) {
				if (job.getId ().equals (jobs.get (j).getPrevJobId ())) {
					job = jobs.get (j);
					list.add (job);
					jobs.remove (jobs.get (j));
					k--;
					break;
				}
			}
		}
		return list;
	}

	private List<ZTreeNode> tranJobConfigToZtreeNode (List<JobConfig> jobconfigs) {
		List<ZTreeNode> result = new ArrayList<ZTreeNode> ();
		ZTreeNode node = null;
		for (JobConfig jobconfig : jobconfigs) {
			node = new ZTreeNode ();
			node.setId (jobconfig.getId ());
			node.setName (jobconfig.getName ());
			node.setJobType(jobconfig.getJobType());
			DiscriminatorValue jobConfigType = jobconfig.getClass ()
					.getAnnotation (DiscriminatorValue.class);
			String type = jobConfigType.value ();
			if (jobconfig instanceof TaskJobConfig) {
				node.setIsParent (true);
				node.setJobConfigType (type);
				// node.setIconSkin("ui-icon-shell");
			} else {
				node.setIsParent (false);
				node.setJobConfigType (type);
				// node.setIconSkin("ui-icon-shell");
			}
			node.setIconSkin (jobconfig.getIcon ());
			result.add (node);
		}
		return result;
	}

	@Override
	public JobConfig getJobInformation (String jobConfigId) {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>> ();
		JobConfig jobConfig = this.findById (jobConfigId);
		return jobConfig;
	}

	@Override
	public JobConfig getJobConfigByRoot (String root, String planConfigId) {
		JobConfig jobConfig = jobConfigDao.getJobConfigByRoot (root, planConfigId);
		return jobConfig;
	}

	@Override
	public List<JobConfig> findListByPlanConfigId (String planConfigId) {
		return jobConfigDao.getJobConfigListByPlanConfigId (planConfigId);
	}

	@Override
	public List<Object[]> checkReleaseJobValidation (String planConfigId) {
		return jobConfigDao.checkReleaseJobValidation (planConfigId);
	}

	@Override
	public List<JobConfig> getListByPlanConfigIdAndType (String planConfigId, String type) {
		return jobConfigDao.getListByPlanConfigIdAndType (planConfigId, type);
	}

	@Override
	public List<JobConfig> getJobConfigList (String parentId, String planId) {
		return jobConfigDao.getJobConfigList (parentId, planId);
	}

	@Override
	public JobConfig getLastJobConfig (String parentId, String planId) {
		return jobConfigDao.getLastJobConfig (parentId, planId);
	}
}
