package com.sunyard.frameworkset.plugin.tsp.execute.exe;

import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.ExeJobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import com.sunyard.frameworkset.plugin.tsp.spi.jobexecute.JobExecute;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * 对于exe可执行器的几点说明,由于在java调用exe可以返回一个值来判断调用exe是否结束
 * 但是由于exe文件的多样性,官方规定返回值为0的时候代表调用成功,但是对于不同的exe文件,
 * 其正确调用exe的返回值不一定是0,但是在程序的时候我们有需要对其做一个判断,看调用是否
 * 成功,由于没有统一的标准,因此不好做判断,在此在前台设置一个属性,这个属性用于规定要调用
 * 的exe文件的正确调用的返回值,在程序里面动态的根据这个值进行判断,看程序是否执行正确,这
 * 里举两个例子,(1)对于自己写的java的exe文件,当返回为0的时候,代表其调用成功,符合官网
 * 标准。(2)对于QQ，如果打开的这个QQExe文件打开的是第一个，则返回值为0代码成功，当打开
 * 的这个QQExe文件之前已经打开一个了，那么调用成功的返回值为-1，因此对于QQ的Exe文件其
 * 正确的返回值可能是多个(3)对于谷歌浏览器这个Exe文件，无论打开的时候的无论之前是否打开过
 * 该浏览器，其成功调用之后的返回值都是0，返回官网标准。
 */
public class ExeJobExecute extends JobExecute {
		
	 private static final String ERRORCODE="JBET009";
	 private static final Logger logger = LoggerFactory.getLogger(ExeJobExecute.class);
	 
	 public ResultMessage execute(JobInst jobinst){
		 ResultMessage rm=new ResultMessage();
		 Process pro=null;
		 try{
			 	ExeJobInst exeJobInst=(ExeJobInst)jobinst;
			 	logger.info("开始运行Exe作业;实例信息:【{}】", exeJobInst);
			 	
			 	//获取返回值
			 	String returnValues=exeJobInst.getReturnValues();
			 	//获取运行参数
				String exeParam=exeJobInst.getRunParams();
				String str="";
				if(exeParam!=null&&!("".equals(exeParam))){
					String[] strs=exeParam.split(",");
					StringBuilder sb=new StringBuilder();
					for(int i=0;i<strs.length;i++){
							sb.append(" "+strs[i]);	
					}
					str=sb.toString();
				}
			 	//获取文件路径
			 	String command=exeJobInst.getFilePath()+str;
			 	pro=Runtime.getRuntime().exec(command);
			 	
			 	//获取输入流的信息
			 	InputStream in=pro.getInputStream();
				String info = this.processStream(in,"gbk");
				logger.info(info);
				
				Integer a=pro.waitFor(); 
				String returnValue=a.toString();
				
				InputStream in1=pro.getErrorStream();
				String info1 = this.processStream(in1,"gbk");
				if(returnValues.contains(returnValue)){
					rm.setSuccess(true);
					logger.info("运行Exe作业结束;实例信息:【{}】", exeJobInst);
				}
				else{
					throw new RuntimeException("运行Exe作业中Exe文件出错"+"---------------------a="+a+"-------作业名称为:"+exeJobInst.getName()+"------------------------------------");
				}	
		 }catch(Exception e){
				e.printStackTrace();
				logger.error("", e);
				TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
				tspe.setErrorCode(ERRORCODE);
				tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
				tspe.setBriefDescription(e.getMessage());
				rm.setSuccess(false);
				rm.setResult(tspe);
				logger.error("运行exe作业出错:【{}】", tspe.getMessage()); 
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
	 
	 private static String processErrorStream(InputStream in,String charset) throws Exception {
		   InputStreamReader isr=new InputStreamReader(in,charset);
	       BufferedReader br=new BufferedReader(isr);
	       br.mark(1);
	       br.reset();
	       StringBuilder sb = new StringBuilder(); 
	       String line=null;
	        while ((line=br.readLine())!=null) { 
	            sb.append(line+" ");
	        } 
	        return sb.toString();
	    } 
	 
	
	 //调用电脑的exe文件
	 private void computer(){
		 ExeJobExecute eje=new ExeJobExecute(); 
		 ExeJobInst exeJobInst=new ExeJobInst();
		 //exeJobInst.setFilePath("C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
		 //exeJobInst.setFilePath("D:\\Program Files\\Mozilla Firefox\\firefox.exe");
		 exeJobInst.setFilePath("D:\\Program Files (x86)\\Tencent\\QQ\\QQProtect\\Bin\\QQProtect.exe");
		 eje.execute(exeJobInst); 
	 }
	 //正确运行
	 private void right(){
		 ExeJobExecute eje=new ExeJobExecute(); 
		 ExeJobInst exeJobInst=new ExeJobInst();
		 exeJobInst.setFilePath("G:/exe1/tsp-exe.exe");
		 exeJobInst.setRunParams("100 200 300");
		 eje.execute(exeJobInst); 
	 }
	 //目标文件存在，但是在运行目标文件时候出错
	 private void exceptionExe(){
		 ExeJobExecute eje=new ExeJobExecute(); 
		 ExeJobInst exeJobInst=new ExeJobInst();
		 exeJobInst.setFilePath("G:/exe1/tsp-exe.exe 100 200");
		 eje.execute(exeJobInst); 
	 }
	 //目标文件不存在
	 private void noExe(){
		 ExeJobExecute eje=new ExeJobExecute(); 
		 ExeJobInst exeJobInst=new ExeJobInst();
		 exeJobInst.setFilePath("G:/exe1/tsp-exe1.exe 100 200");
		 eje.execute(exeJobInst);
	 }
	 
	 public static void main(String[] args){
		 //ExeJobExecute eje=new ExeJobExecute();
		 //eje.right();
		 ExeJobExecute eje=new ExeJobExecute(); 
		 MyThread1 thread1=new MyThread1(eje);
		 MyThread2 thread2=new MyThread2(eje);
		 MyThread3 thread3=new MyThread3(eje);
		 thread1.start();
		 thread2.start();
		 thread3.start();
	 }

	@Override
	public String getType() {
		return "exe";
	}

	@Override
	public void stop () {

	}

	@Override
	public void pause () {

	}
}

class MyThread1 extends Thread{
	ExeJobExecute eje;
	public MyThread1(ExeJobExecute eje) {
		this.eje=eje;
	}
	
 	public void run(){
		ExeJobInst exeJobInst=new ExeJobInst();
		exeJobInst.setName("谷歌");
		exeJobInst.setFilePath("C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
	   // exeJobInst.setRunParams("100,100,100");
		exeJobInst.setReturnValues("0");
		eje.execute(exeJobInst); 
 }
}

class MyThread2 extends Thread{
	ExeJobExecute eje;
	public MyThread2(ExeJobExecute eje) {
		this.eje=eje;
	}
 	public void run(){
		ExeJobInst exeJobInst=new ExeJobInst();
		exeJobInst.setName("QQ");
		exeJobInst.setReturnValues("0");
		exeJobInst.setFilePath("D:\\Program Files (x86)\\Tencent\\QQ\\QQProtect\\Bin\\QQProtect.exe");
		//exeJobInst.setRunParams("200,200,200");
		exeJobInst.setReturnValues("0,-1");
		eje.execute(exeJobInst); 
 }
}

class MyThread3 extends Thread{
	
	ExeJobExecute eje;
	public MyThread3(ExeJobExecute eje) {
		this.eje=eje;
	}
 	public void run(){
		ExeJobInst exeJobInst=new ExeJobInst();
		exeJobInst.setName("exe");
		exeJobInst.setReturnValues("1");
		exeJobInst.setFilePath("G:/exe1/tsp-exe(1).exe");
		exeJobInst.setRunParams("300,300,300");
		exeJobInst.setReturnValues("0");
		eje.execute(exeJobInst); 
 }
}

