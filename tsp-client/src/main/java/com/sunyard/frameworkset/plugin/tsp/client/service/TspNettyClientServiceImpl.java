package com.sunyard.frameworkset.plugin.tsp.client.service;


import com.sunyard.frameworkset.plugin.tsp.client.TspNettyClient;
import com.sunyard.frameworkset.plugin.tsp.spi.client.service.TspNettyClientService;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.config.JobServerConfigVO;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;

public class TspNettyClientServiceImpl implements TspNettyClientService {

	public ResultMessage getResources() {
		TspNettyClient client = TspNettyClient.getInst();
		 return client.getResources();
	}

	public void createJob(JobInst jobInst) {
		TspNettyClient client = TspNettyClient.getInst();
		client.runJob(jobInst);
	}

	public ResultMessage refresh(JobServerConfigVO jobServerConfig) {
		TspNettyClient client = TspNettyClient.getInst();
		ResultMessage rm = client.refresh(jobServerConfig);
		return rm;
	}

	public ResultMessage getLogContext(String planInst,String jobId){
		TspNettyClient client = TspNettyClient.getInst();
		ResultMessage rm = client.getLogContext (planInst, jobId);
		return rm;
	}

	public ResultMessage stopJob ( String jobId) {
		TspNettyClient client = TspNettyClient.getInst();
		ResultMessage rm = client.stopJob ( jobId);
		return rm;
	}

	public ResultMessage pauseJob ( String jobId) {
		TspNettyClient client = TspNettyClient.getInst();
		ResultMessage rm = client.pauseJob ( jobId);
		return rm;
	}

	public ResultMessage continueJob ( String jobId) {
		TspNettyClient client = TspNettyClient.getInst();
		ResultMessage rm = client.continueJob ( jobId);
		return rm;
	}

}
