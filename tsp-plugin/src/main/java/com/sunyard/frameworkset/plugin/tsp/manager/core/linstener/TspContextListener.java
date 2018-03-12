package com.sunyard.frameworkset.plugin.tsp.manager.core.linstener;

import com.sunyard.frameworkset.plugin.tsp.manager.core.service.init.InitService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * 当spring容器加载完之后触发初始化环境
 * 
 * @author whj
 * 
 */
@Component
public class TspContextListener implements ApplicationListener<ContextRefreshedEvent> {

	
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
		 //防止重复执行。
        if(event.getApplicationContext().getParent() == null){
        	InitService initService=event.getApplicationContext().getBean(InitService.class);
        	initService.init();
        }
	}

}
