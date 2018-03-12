package com.sunyard.frameworkset.plugin.tsp.execute.datastage;

import com.sun.jna.NativeLong;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.DataStageInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import com.sunyard.frameworkset.plugin.tsp.spi.jobexecute.JobExecute;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import com.sunyard.frameworkset.plugin.tsp.execute.datastage.vmdsapi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class DataStageJobExecute extends JobExecute {
	@Override
	public Boolean getIsPause() {
		return super.getIsPause();
	}

	@Override
	public void setIsPause(Boolean isPause) {
		super.setIsPause(isPause);
	}

	@Override
	public ResultMessage execute(JobInst jobinst) {
		return null;
	}

	@Override
	public String getType() {
		return null;
	}

	@Override
	public void stop() {

	}

	@Override
	public void pause() {

	}
/*private static final String ERRORCODE = "JBET003";

	private static final Logger logger = LoggerFactory.getLogger(DataStageJobExecute.class);

	private static Map<String, DSPROJECT> projects = new ConcurrentHashMap<String, DSPROJECT>();

	private static vmdsapi dsapi = vmdsapi.INSTANCE;

	private Properties pro;

	public DataStageJobExecute(){
		InputStream in =	DataStageJobExecute.class.getClassLoader().getResourceAsStream("errorcode.properties");
		pro= new Properties();
		try {
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("加载errorcode.properties文件出错!");
		}
	}

	public ResultMessage execute(JobInst jobinst) {
		logger.info("开始运行datastage作业;实例信息:【{}】", jobinst);
		ResultMessage rm = new ResultMessage();
		DSJOB job=null;
		DSPARAMINFO paraminfo= null;
		DSPARAM param = null;
		int ret=0;
		Date start = new Date();
		try {
			DataStageInst dataStageInst = (DataStageInst) jobinst;
			String projectName = dataStageInst.getDsProjectName();
			String jobName = dataStageInst.getDsJobName();
			if(projectName == null || "".equals(projectName)){
				throw new IllegalAccessError("datastage运行的工程名称不能为空");
			}
			if(jobName == null || "".equals(jobName)){
				throw new IllegalAccessError("datastage运行的作业名称不能为空");
			}
			DSPROJECT project=projects.get(projectName);
			if(project == null){
				logger.info("创建project,projectNmae:【{}】",projectName);
				project=dsapi.DSOpenProjectEx(1,projectName);
				projects.put(projectName, project);
			}else{
				logger.info("查看project是否已经断开");
				DSPROJECTINFO projectInfo = new DSPROJECTINFO();
				ret=dsapi.DSGetProjectInfo(project, vmdsapi.DSJ_PROJECTNAME, projectInfo);
				if(ret != vmdsapi.DSJE_NOERROR){
					logger.info("【{}】已经断开连接,尝试重新连接",projectName);
					project=dsapi.DSOpenProjectEx(1,projectName);
					projects.put(projectName, project);
				}
			}
			job = dsapi.DSOpenJob(project,jobName);
			ret=dsapi.DSLockJob(job);
			checkRet(ret,"DSLockJob");
			DSJOBINFO jobinfo = new DSJOBINFO();
			ret=dsapi.DSGetJobInfo(job, vmdsapi.DSJ_JOBSTATUS, jobinfo);
			checkRet(ret,"DSGetJobInfo");
			if(jobinfo.info.jobStatus == vmdsapi.DSJS_RUNFAILED){
				logger.debug("作业状态码为:"+jobinfo.info.jobStatus+";开始重置作业");
				dsapi.DSRunJob(job, vmdsapi.DSJ_RUNRESET);
				dsapi.DSUnlockJob(job);
				dsapi.DSCloseJob(job);
				job=dsapi.DSOpenJob(project, jobName);
				ret=dsapi.DSLockJob(job);
				checkRet(ret,"DSGetJobInfo");
				logger.debug("作业重置结束");
			}
			ret=dsapi.DSSetJobLimit(job, vmdsapi.DSJ_LIMITROWS, 0);
			logger.debug("设置DSJ_LIMITROWS为:0");
			checkRet(ret,"DSSetJobLimit:DSJ_LIMITROWS");
			ret=dsapi.DSSetJobLimit(job, vmdsapi.DSJ_LIMITWARN, 0);
			logger.debug("设置DSJ_LIMITWARN为:0");
			checkRet(ret,"DSSetJobLimit:DSJ_LIMITWARN");
			String params=dataStageInst.getRunParams();
			if(params!=null && !"".equals(params)){
				logger.info("开始设置参数;params:【{}】",params);
				String[] runParams = params.split(";");
				for (String runParam : runParams) {
					if("".equals(runParam.trim())){
						continue;
					}
					String[] p = runParam.split("=");
					if(p.length!=2){
						throw new IllegalAccessException("参数格式错误,请检查参数格式");
					}
					paraminfo = new DSPARAMINFO();
					ret=dsapi.DSGetParamInfo(job, p[0].trim(), paraminfo);
					checkRet(ret,"DSGetParamInfo:"+p[0].trim());
					param = this.getParam(paraminfo,p[1].trim());
					ret=dsapi.DSSetParam(job, p[0].trim(), param);
					checkRet(ret,"DSSetParam:"+p[0].trim());
					logger.info("设置参数:【{}】成功,参数值为:【{}】",p[0].trim(),p[1].trim());
				}
				logger.info("参数设置结束");
			}
			logger.info("开始运行作业");
			ret=dsapi.DSRunJob(job, vmdsapi.DSJ_RUNNORMAL);
			checkRet(ret,"DSRunJob");
			while(true){
				Thread.sleep(100);
				dsapi.DSGetJobInfo(job, vmdsapi.DSJ_JOBSTATUS,jobinfo);
				int flag=jobinfo.info.jobStatus;
				if(flag == vmdsapi.DSJS_RUNFAILED){
					getEventLog(job,start);
					throw new RuntimeException(jobinst.getName()+"作业运行出错;");
				}else if(flag ==vmdsapi.DSJS_RUNOK || flag == vmdsapi.DSJS_RUNWARN){
					getEventLog(job,start);
					logger.info(jobinst.getName()+"运行成功");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
			tspe.setErrorCode(ERRORCODE);
			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
			tspe.setBriefDescription(e.getMessage());
			rm.setSuccess(false);
			rm.setResult(tspe);
			logger.error("datastage运行出错:【{}】", tspe.getMessage());
		}finally{
			if(job!=null){
				dsapi.DSUnlockJob(job);
				dsapi.DSCloseJob(job);
			}
		}
		return rm;
	}



	private void getEventLog(vmdsapi.DSJOB dsjob,Date start){
		NativeLong st=new NativeLong(start.getTime()/1000);
		DSLOGEVENT event = new DSLOGEVENT();
		Date  end= new Date();
		NativeLong ed=new NativeLong(end.getTime()/1000);
		int r= dsapi.DSFindFirstLogEntry(dsjob,99 ,st , ed, 1000, event);
		checkRet(r, "DSFindFirstLogEntry");
		dowithEvent(event);
		while(dsapi.DSFindNextLogEntry(dsjob, event)==dsapi.DSJE_NOERROR){
			dowithEvent(event);
		};

	}

	private void dowithEvent(DSLOGEVENT event){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String a= event.message.getString(0);
		Long t=event.timestamp.longValue()*1000;
		Date date = new Date(t);
		String  b=sdf.format(date);
		logger.info("{}  {}",b,a);
	}


	private void checkRet(int ret,String message){
		if(ret != vmdsapi.DSJE_NOERROR){
			throw new RuntimeException(message+"方法执行有错;错误码为:"+ret+";错误信息为:"+pro.getProperty(String.valueOf(ret)));
		}
	}

	*//**
	 * 根据参数信息,创建参数
	 * @param paraminfo
	 * @param value
	 * @return
	 *//*
	private DSPARAM getParam(DSPARAMINFO paraminfo,String value) {
		DSPARAM param = new DSPARAM();
*//*		DSPARAMVALUE paramvalue = new DSPARAMVALUE();
		param.paramValue=paramvalue;*//*
		int paramType = paraminfo.paramType;
		param.paramType=paramType;
		switch(paramType){
		case vmdsapi.DSJ_PARAMTYPE_DATE:
			param.pDate=value;
			break;
		case vmdsapi.DSJ_PARAMTYPE_FLOAT:
			param.pFloat = Float.valueOf(value);
			break;
		case vmdsapi.DSJ_PARAMTYPE_ENCRYPTED:
			param.pEncrypt=value;
			break;
		case vmdsapi.DSJ_PARAMTYPE_INTEGER:
			param.pInt=Integer.valueOf(value);
			break;
		case vmdsapi.DSJ_PARAMTYPE_LIST:
			param.pListValue=value;
			break;
		case vmdsapi.DSJ_PARAMTYPE_PATHNAME:
			param.pPath=value;
			break;
		case vmdsapi.DSJ_PARAMTYPE_STRING:
			param.pString=value;
			break;
		case vmdsapi.DSJ_PARAMTYPE_TIME:
			param.pDate=value;
			break;
		}
		return param;
	}

	public static void main(String[] args) {
		DataStageInst dsi = new DataStageInst();
		dsi.setName("datastage作业");
		dsi.setDsProjectName("dstage1");
		dsi.setDsJobName("dbtest");
		dsi.setRunParams(" filepath = /sunyard/jna/whj.del ;");
		DataStageJobExecute dsje = new DataStageJobExecute();
		dsje.execute(dsi);
	}


	@Override
	public String getType() {
		return "datastage";
	}

	@Override
	public void stop () {

	}

	@Override
	public void pause () {

	}*/
}
