package com.sunyard.frameworkset.plugin.tsp.spi.server.service;

import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import org.summercool.hsf.annotation.RemoteServiceContract;

@RemoteServiceContract
public interface TspNettyServerService {

	
	/**
	 * 处理作业结果
	 * @param jobResult
	 */
	public void dealWithJobResult(ResultMessage jobResult);
	
	
	/**
	 * 确保任务已经运行
	 * @param jobInst
	 * @return 
	 */
	public String checkRunning(JobInst jobInst);
	
}
