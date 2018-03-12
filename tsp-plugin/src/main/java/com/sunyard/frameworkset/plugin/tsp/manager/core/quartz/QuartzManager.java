package com.sunyard.frameworkset.plugin.tsp.manager.core.quartz;

import com.sunyard.frameworkset.plugin.tsp.manager.enums.TspPluginMsgEnum;
import com.sunyard.frameworkset.plugin.tsp.manager.exception.TspException;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("quartzManager")
@Transactional
public class QuartzManager {

	@Autowired(required = false)
	private Scheduler sched;

	private static final String GROUP = "SUNYARDGROUP";
	
	private static final String ERRORCODE="QUTZ001";
	
	public void addJob(Class<? extends Job> clazz, String jobName, String time,
			Map<String, Object> dataMap) {
		try {
			//JobDetail jobDetail = new JobDetail(jobName, QuartzManager.GROUP, clazz);
			//jobDetail.getJobDataMap().putAll(dataMap);
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName,QuartzManager.GROUP).setJobData(new JobDataMap(dataMap)).build();

			// 触发器
			//Trigger trigger =new CronTrigger(jobName, QuartzManager.GROUP, jobName, QuartzManager.GROUP, time);
			Trigger trigger =TriggerBuilder.newTrigger().withIdentity(jobName,QuartzManager.GROUP).withSchedule(CronScheduleBuilder.cronSchedule(time)).build();

			//sched.start();

			//sched.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			e.printStackTrace();
//			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
//			tspe.setErrorCode(ERRORCODE);
//			tspe.setBriefDescription("添加QUARTZ任务出错:" + e.getMessage());
//			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
//			tspe.setDiagnosticInference("请检查周期表达式是否正确,表达式为:【"+time+"】");
			throw new TspException(TspPluginMsgEnum.QUARRZ_ERROR.getCode(),"添加QUARTZ任务出错:"+e.getMessage()+"请检查周期表达式是否正确，表达式为:【"+time+"】",e);
		}
	}

	@SuppressWarnings({ "unchecked" })
	public void modifyJobTime(String jobName, String time){
		try {
			//Trigger oldTrigger = sched.getTrigger(jobName,QuartzManager.GROUP);
			//Trigger newTrigger =new CronTrigger(jobName, QuartzManager.GROUP, jobName, QuartzManager.GROUP, time);
			Trigger newTrigger =TriggerBuilder.newTrigger().withIdentity(jobName,QuartzManager.GROUP).withSchedule(CronScheduleBuilder.cronSchedule(time)).build();

			//sched.rescheduleJob(jobName,QuartzManager.GROUP, (Trigger) newTrigger);
			//sched.rescheduleJob(newTrigger.getKey(),newTrigger);

		} catch (Exception e) {
			e.printStackTrace();
//			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
//			tspe.setErrorCode(ERRORCODE);
//			tspe.setBriefDescription("修改QUARTZ任务时间出错:"+e.getMessage());
//			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
//			tspe.setDiagnosticInference("请确保已经发布过该计划;请检查计划周期表达式是否正确,表达式为:【"+time+"】;");
//			throw tspe;
			throw new TspException(TspPluginMsgEnum.QUARRZ_ERROR.getCode(),"修改QUARTZ任务出错:"+e.getMessage()+"请确保已经发布过该计划;请检查计划周期表达式是否正确,表达式为:【"+time+"】",e);
		}
	}

	/**
	 * 删除作业
	 * @param jobName
	 * @throws SchedulerException
	 */
	public  void removeJob(String jobName){
		try {
			TriggerKey triggerKey=new TriggerKey(jobName,QuartzManager.GROUP);
			JobKey jobKey=new JobKey(jobName,QuartzManager.GROUP);

			//sched.pauseTrigger(triggerKey);// 停止触发器
			//sched.unscheduleJob(triggerKey);// 移除触发器
			//sched.deleteJob(jobKey);
		} catch (Exception e) {
			e.printStackTrace();
//			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
//			tspe.setErrorCode(ERRORCODE);
//			tspe.setBriefDescription("移除QUARTZ任务出错:"+e.getMessage());
//			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
//			tspe.setDiagnosticInference("请确保已经发布过该计划");
//			throw tspe;
			throw new TspException(TspPluginMsgEnum.QUARRZ_ERROR.getCode(),"移除QUARTZ任务出错:"+e.getMessage()+"请确保已经发布过该计划",e);
		}
	}

	/**
	 * 判断该作业是否存在
	 * @param jobName
	 * @return
	 * @throws SchedulerException
	 */
	public boolean echeckExistsxits(String jobName){
		try {
			JobKey jobKey=new JobKey(jobName, QuartzManager.GROUP);
			//JobDetail  jd=sched.getJobDetail(jobKey);
			JobDetail  jd=null;
			if(jd==null){
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
//			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
//			tspe.setErrorCode(ERRORCODE);
//			tspe.setBriefDescription("判断是否存在QUARTZ任务出错:"+e.getMessage());
//			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
//			throw tspe;
			throw new TspException(TspPluginMsgEnum.QUARRZ_ERROR.getCode(),"判断是否存在QUARTZ任务出错:"+e.getMessage(),e);
		}
	}
	
}
