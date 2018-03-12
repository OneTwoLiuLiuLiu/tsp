package com.sunyard.frameworkset.plugin.tsp.execute.jar;

import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JarJobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import com.sunyard.frameworkset.plugin.tsp.spi.jobexecute.JobExecute;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class JarJobExecute extends JobExecute {

	private static final String ERRORCODE = "JBET007";
	private static final Logger logger = LoggerFactory.getLogger (JarJobExecute.class);
	private Process pro = null;
	private Boolean isPause = false;

	public ResultMessage execute (JobInst jobinst) {
		ResultMessage rm = new ResultMessage ();
		try {
			this.isPause = false;
			JarJobInst jarJobInst = (JarJobInst) jobinst;
			logger.info ("开始运行jar作业;实例信息:【{}】", jarJobInst);

			//获得虚拟机最小初始化值
			String min = jarJobInst.getInitialMemoryValue ();
			//获取虚拟机最大值
			String max = jarJobInst.getMaxMemoryValue ();
			//获取文件路径
			String path = jarJobInst.getFilePath ();
			//获取运行参数
			String jarParam = jarJobInst.getRunParams ();
			String str = jobinst.getJobId ();
			if (jarParam != null && !("".equals (jarParam))) {
				String[] strs = jarParam.split (",");
				StringBuilder sb = new StringBuilder ();
				for (int i = 0; i < strs.length; i++) {
					sb.append (" " + strs[ i ]);
				}
				sb.append (" "+str);
				str = sb.toString ();
			}

			//执行命令
			String command = "java " + "-Xms" + min + "m" + " -Xmx" + max + "m" + " -jar " + path + " " + str;
			pro = Runtime.getRuntime ().exec (command);


			//获取输入流
			InputStream in = pro.getInputStream ();
			String info = this.processStream (in, "GBK");
			logger.info (info);


			//获取错误流
			InputStream errors = pro.getErrorStream ();
			String error = this.processStream (errors, "GBK");

			pro.waitFor ();
			if (error != null && !"".equals (error)) {
				TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException ();
				tspe.setErrorCode (ERRORCODE);
				tspe.setSeriousness (TaskSchedulingPlatformException.ERROR);
				tspe.setBriefDescription (error);
				rm.setSuccess (false);
				rm.setResult (tspe);
				logger.error ("运行jar作业中jar文件出错:【{}】", tspe.getMessage ());
			} else {
				rm.setSuccess (true);
				logger.info ("运行jar作业结束;实例信息:【{}】", jarJobInst);
			}
		} catch (InterruptedException e) {
			pro.destroy ();
			rm.setSuccess (true);
			logger.info ("暂停成功");
			return rm;
		} catch (Exception e) {
			e.printStackTrace ();
			logger.error ("", e);
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException ();
			tspe.setErrorCode (ERRORCODE);
			tspe.setSeriousness (TaskSchedulingPlatformException.ERROR);
			tspe.setBriefDescription (e.getMessage ());
			rm.setSuccess (false);
			rm.setResult (tspe);
			logger.error ("运行jar作业出错:【{}】", tspe.getMessage ());
		}
		return rm;
	}

	@Override
	public Boolean getIsPause () {
		return isPause;
	}

	@Override
	public void setIsPause (Boolean isPause) {
		this.isPause = isPause;
	}

	public void stop () {
		pro.destroy ();
		this.isPause = false;
		logger.info ("jar作业已停止————————————————————————————————");
	}

	public void pause () {
		pro.destroy ();
		this.isPause = true;
		logger.info ("jar作业已暂停————————————————————————————————");
	}

	private static String processStream (InputStream in, String charset) throws Exception {
		InputStreamReader isr = new InputStreamReader (in, charset);
		BufferedReader br = new BufferedReader (isr);
		StringBuilder sb = new StringBuilder ();
		String line = null;
		while ((line = br.readLine ()) != null) {
			sb.append (line + " ");
		}
		return sb.toString ();
	}


	//正确运行
//		 private void rightJar(){
//			 JarJobExecute jje=new JarJobExecute();
//			 JarJobInst jji = new JarJobInst();
//			 jji.setFilePath("G:/jar/tsp-jar1.jar");
//			 jji.setMaxMemoryValue("128");
//			 jji.setInitialMemoryValue("64");
//			 jji.setRunParams("100,200");
//			 jje.execute(jji);
//		 }
//
//		 //内存问题的验证
//		 private void memory(){
//			 JarJobExecute jje=new JarJobExecute();
//			 JarJobInst jji = new JarJobInst();
//			 jji.setFilePath("G:/jar/tsp-jar.jar");
//			 jji.setMaxMemoryValue("256");
//			 jji.setInitialMemoryValue("128");
//			 jji.setRunParams("100,200");
//			 jje.execute(jji);
//		 }
//		 //正确运行，并带有参数，但是传参数错误
//		 private void rightJarBut(){
//			 JarJobExecute jje=new JarJobExecute();
//			 JarJobInst jji = new JarJobInst();
//			 jji.setFilePath("G:/jar/tsp-jar1.jar");
//			 jji.setMaxMemoryValue("128");
//			 jji.setInitialMemoryValue("64");
//			 jji.setRunParams("100");
//			 jje.execute(jji);
//		 }
//
//		 //目标文件存在但是在运行目标文件时候出差
//		 private void exceptionJar(){
//			 JarJobExecute jje=new JarJobExecute();
//			 JarJobInst jji = new JarJobInst();
//			 jji.setFilePath("G:/jar/tsp-jar.jar");
//			 jji.setMaxMemoryValue("128");
//			 jji.setInitialMemoryValue("64");
//			 jji.setRunParams("100,200,300");
//			 jje.execute(jji);
//		 }
//		 //目标文件不存在
//		 private  void noJar(){
//			 JarJobExecute jje=new JarJobExecute();
//			 JarJobInst jji = new JarJobInst();
//			 jji.setFilePath("G:/jar/tsp-jar3.jar");
//			 jji.setMaxMemoryValue("128");
//			 jji.setInitialMemoryValue("64");
//			 jji.setRunParams("100,200,300");
//			 jje.execute(jji);
//		 }

	public static void main (String[] args) {
		MyThread1 thread1 = new MyThread1 ();
		//MyThread1 thread2=new MyThread1();
		//MyThread1 thread3=new MyThread1();

		thread1.start ();
		thread1.interrupt ();
		//thread2.start();
		//thread3.start();

	}

	@Override
	public String getType () {
		return "jar";
	}

}

class MyThread1 extends Thread {
	public void run () {
		JarJobExecute jje = new JarJobExecute ();
		JarJobInst jji = new JarJobInst ();
		jji.setFilePath ("E:\\fdap-runable-component\\fdap-runable\\target\\fdap-runable\\fdap-runable-1.0-SNAPSHOT.jar");
		jji.setMaxMemoryValue ("128");
		jji.setInitialMemoryValue ("64");
		//jji.setRunParams("100,200");
		jje.execute (jji);

	}
}

class MyThread2 extends Thread {
	public void run () {
		JarJobExecute jje = new JarJobExecute ();
		JarJobInst jji = new JarJobInst ();
		jji.setFilePath ("G:/jar/tsp-jar1(1).jar");
		jji.setMaxMemoryValue ("128");
		jji.setInitialMemoryValue ("64");
		jji.setRunParams ("100,300");
		jje.execute (jji);
	}
}

class MyThread3 extends Thread {
	public void run () {
		JarJobExecute jje = new JarJobExecute ();
		JarJobInst jji = new JarJobInst ();
		jji.setFilePath ("G:/jar/tsp-jar1(2).jar");
		jji.setMaxMemoryValue ("128");
		jji.setInitialMemoryValue ("64");
		jji.setRunParams ("200,300");
		jje.execute (jji);
	}
}
