package com.sunyard.frameworkset.plugin.tsp.execute.shell;

import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.ShellJobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import com.sunyard.frameworkset.plugin.tsp.spi.jobexecute.JobExecute;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ShellJobExecute extends JobExecute {

	private static final String ERRORCODE = "JBET004";

	private static final Logger logger = LoggerFactory.getLogger(ShellJobExecute.class);

	public ResultMessage execute(JobInst jobinst) {
		logger.info("开始运行shell脚本;实例信息:【{}】", jobinst);
		ResultMessage rm = new ResultMessage();
		try {
			ShellJobInst shellJobInst = (ShellJobInst) jobinst;
			String command = "sh " + shellJobInst.getFilePath() + " "+ shellJobInst.getRunParams();
			Process pro = null;
			pro = Runtime.getRuntime().exec(command);
			int result=pro.waitFor();
			InputStream in = pro.getInputStream();
			String info = this.processStream(in, Charset.defaultCharset().toString());
			logger.info(info);
			InputStream errors = pro.getErrorStream();
			String error = this.processStream(errors, Charset.defaultCharset().toString());
			logger.error(error);
			logger.info("返回值为:【{}】",result);
			if(!shellJobInst.getReturnValues().contains(String.valueOf(result))){
				logger.info("返回值不在约定的正确返回值之内,正确的返回值为:【{}】",shellJobInst.getReturnValues());
				throw new RuntimeException("返回值不在约定的正确返回值之内");
			}else{
				rm.setSuccess(true);
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
			logger.error("shell脚本运行出错:【{}】", tspe.getMessage());
		}
		return rm;
	}

	 private static String processStream(InputStream in,String charset) throws Exception { 
	       InputStreamReader isr=new InputStreamReader(in,charset);
	       BufferedReader br=new BufferedReader(isr);
	       StringBuilder sb = new StringBuilder(); 
	       String line=null;
	        while ((line=br.readLine())!=null) { 
	            sb.append(line+" ");
	        } 
	        return sb.toString();
	    } 

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入shell脚本的文件路径");
		String filePath = sc.next();

		System.out.println("请输入参数以空格分隔");
		String param = sc.next();
		
		ShellJobExecute sje = new ShellJobExecute();
		ShellJobInst sji = new ShellJobInst();
		sji.setFilePath(filePath);
		sji.setRunParams(param);
		sje.execute(sji);
	}

	@Override
	public String getType() {
		return "shell";
	}

	@Override
	public void stop () {

	}

	@Override
	public void pause () {

	}

}
