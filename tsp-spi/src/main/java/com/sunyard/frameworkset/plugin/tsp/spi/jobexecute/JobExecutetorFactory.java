package com.sunyard.frameworkset.plugin.tsp.spi.jobexecute;


import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;

import java.util.HashMap;
import java.util.Map;

public class JobExecutetorFactory {

	private static final String ERRORCODE="PNTC002";
	
	public static Map<String,JobExecute> executors = new HashMap<String, JobExecute>();
	
	public static void register(String type,JobExecute jobExecute){
		executors.put(type, jobExecute);
	}
	
	public static JobExecute getJobExecute(String type){
		JobExecute jobExecute = executors.get(type);
		if(jobExecute == null){
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
			tspe.setErrorCode(JobExecutetorFactory.ERRORCODE);
			tspe.setSeriousness(TaskSchedulingPlatformException.FATAL);
			tspe.setBriefDescription("找不到作业类型为"+type+"对应的作业执行器");
			throw tspe;
		}
		return jobExecute;
	}
	
	
	public static void clear(){
		executors.clear();
	}
}
