package com.sunyard.frameworkset.plugin.tsp.spi.client.service;

import com.sunyard.frameworkset.plugin.tsp.spi.entity.config.JobServerConfigVO;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import org.summercool.hsf.annotation.RemoteServiceContract;

@RemoteServiceContract
public interface TspNettyClientService {

	/**
	 * 获取资源信息
	 * @return
	 */
	ResultMessage getResources();
	
	/**
	 * 创建作业
	 * @param jobInst
	 */
	void createJob(JobInst jobInst);
	
	/**
	 * 刷新客户端配置
	 */
	ResultMessage refresh(JobServerConfigVO jobServerConfig);
	
	
	/**
	 * 获取日志内容
	 * @param planInst
	 * @param jobId
	 * @return
	 */
	ResultMessage getLogContext(String planInst, String jobId);


	ResultMessage stopJob(String jobId);
	ResultMessage pauseJob(String jobId);

	ResultMessage continueJob(String jobId);
	
}
