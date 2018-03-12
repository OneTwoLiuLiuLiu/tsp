package com.sunyard.frameworkset.plugin.tsp.manager.core.service.external;



import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.Plan;

import java.util.List;
import java.util.Map;


/**
 * 提供对外的接口
 * @author Administrator
 *
 */
public interface TaskSchedulePlatformService {

	/**
	 * 启动一个计划
	 * @param planId
	 * @param batchNo
	 * @return 计划实例Id
	 */
	public String StartPlan(String planId,String batchNo,Map<String, String> params);
	
	
	/**
	 * 获取所有的plan信息
	 * @return
	 */
	public List<Plan> getPlans();
	
	
	/**
	 * 根据Id获取Plan
	 * @param id
	 * @return
	 */
	public Plan getPlanById(String id);
	
	
	/**
	 * 根据计划实例ID获取该计划实例的状态
	 * 0-暂停;1-运行中;2-完成;3-结束;4-失败
	 * @param planInstId
	 * @return
	 */
	public  String getPlanInstStatus(String planInstId);
	
	
}
