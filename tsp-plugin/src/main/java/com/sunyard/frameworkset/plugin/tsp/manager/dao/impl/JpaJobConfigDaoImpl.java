package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.JobConfigDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.jobconfig.JobConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.jobconfig.JobConfigQo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * 作业池 jdbc实现类
 * <p/>
 * Author: Created by code generator
 * Date: Tue Jan 03 08:54:20 CST 2017
 */
@Repository
public class JpaJobConfigDaoImpl extends JpaBaseDaoImpl<JobConfig, String, JobConfigQo> implements JobConfigDao {
	public List<JobConfig> getJobConfigList (String parentId, String planConfigId) {
		List<JobConfig> list = this.find ("from JobConfig where parentJobId=? and planConfig.id=?", JobConfig.class, parentId, planConfigId);
		return list;
	}

	public JobConfig getJobConfigByRoot (String root, String planConfigId) {
		JobConfig jobConfig = this.findBySingle ("from JobConfig As obj where obj.parentJobId=? and obj.nextJobId is null and obj.planConfig.id=?", root, planConfigId);
		return jobConfig;
	}

	@Override
	public List<JobConfig> getListByPlanConfigIdAndType (String planConfigId, String type) {
		return this.find ("from JobConfig where planConfig.id=? and Type=?", JobConfig.class, planConfigId, type);
	}

	@Override
	public List<JobConfig> getJobConfigListByPlanConfigId (String planConfigId) {
		List<JobConfig> list = this.find ("from JobConfig where  planConfig.id=?", JobConfig.class, planConfigId);
		return list;
	}

	@Override
	public List<Object[]> checkReleaseJobValidation (String planConfigId) {
		StringBuilder sql = new StringBuilder (
				"select p.ID,p.NAME from (select ID ,NAME  from TSP_JOB_CONFIG  where (TYPE = 'serial' or TYPE='parallel') and PLAN_CONFIG_ID=?")
				.append (") p left join TSP_JOB_CONFIG c on c.PARENT_JOB_ID = p.ID where c.TYPE is NUll");
		Query reportTypedQuery = getEntityManager ().createNativeQuery (sql.toString ());
		reportTypedQuery.setParameter (1, planConfigId);
		return reportTypedQuery.getResultList ();
	}

	@Override
	public JobConfig getLastJobConfig (String parentId, String planConfigId) {
		return this.findBySingle ("from JobConfig where parentJobId=? and nextJobId is null and planConfig.id=?", JobConfig.class, parentId, planConfigId);
	}
}
