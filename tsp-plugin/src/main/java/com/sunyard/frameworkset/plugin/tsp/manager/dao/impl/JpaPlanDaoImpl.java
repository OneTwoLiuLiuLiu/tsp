package com.sunyard.frameworkset.plugin.tsp.manager.dao.impl;

import com.sunyard.frameworkset.dao.jpa.JpaBaseDaoImpl;
import com.sunyard.frameworkset.plugin.tsp.manager.dao.PlanDao;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.Plan;
import com.sunyard.frameworkset.plugin.tsp.manager.qo.PlanQo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 计划 jdbc实现类
 * <p/>
 * Author: Created by code generator
 * Date: Wed Jan 04 14:25:48 CST 2017
 */
@Repository
public class JpaPlanDaoImpl extends JpaBaseDaoImpl<Plan, String, PlanQo> implements PlanDao {
	@Override
	public Integer getMaxVersionByPlanConfigId (String planConfigId) {
//		List<Plan> plans = this.findByQueryString("from Plan where planConfig.id=?", planConfigId);
//		if (plans.size()>0) {
//			String hql = "select max (version) from Plan where planConfig.id =" + planConfigId;
//			return (Integer) this.getEntityManager ().createQuery (hql).getSingleResult ();
//		} else {
//			return -1;
//		}
//		String hql = "select max (version) from Plan where planConfig.id =?";
//		return  (Integer)this.getEntityManager().createQuery()
		String hql="select max (version) from Plan where planConfig.id =?";
		Integer maxVersion = (Integer)this.getEntityManager().createQuery(hql).setParameter(1,planConfigId).getSingleResult();
		if(maxVersion!=null){
			return maxVersion;
		}else {
			return 0;
		}
	}

	@Override
	public Plan findByPlanConfigIdAndVersion (String planConfigId, Integer version) {
		return this.findBySingle ("from Plan where planConfig.id=? and version=?", planConfigId, version);
	}


	@Override
	public Plan getPlanByPlanConfigId (String planConfigId) {
		return this.findBySingle ("from Plan where planConfig.id=?", planConfigId);
	}
}
