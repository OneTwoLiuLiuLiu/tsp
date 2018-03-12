package com.sunyard.frameworkset.plugin.tsp.manager.core.service.init;


import org.springframework.stereotype.Service;

/**
 * 初始化服务,当容器启动的时候初始化作业和常量
 * @author whj
 *
 */
public interface InitService {

	public void init();
	
}
