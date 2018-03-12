package com.sunyard.frameworkset.plugin.tsp.execute.http;

import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.HttpJobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import com.sunyard.frameworkset.plugin.tsp.spi.jobexecute.JobExecute;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;


public class HttpJobExecute extends JobExecute {
	
	   private static final String ERRORCODE="JBET006";
	   private static final Logger logger = LoggerFactory.getLogger(HttpJobExecute.class);
		
	public ResultMessage execute(JobInst jobinst){

		 ResultMessage rm=new ResultMessage();
		 try{
			 
			 HttpJobInst httpJobInst=(HttpJobInst)jobinst;
			 logger.info("开始运行http作业;实例信息:【{}】", httpJobInst);
			 
			 //获取http的url
			 String httpUrl=httpJobInst.getHttpUrl();
			 
			 //创建http请求方式
			 HttpClient httpClient=new DefaultHttpClient();

			 //创建post请求方式
			 HttpPost httpPost=new HttpPost(httpUrl);
			 
			 /*
			  * 获取运行参数及解析传参数,参数在前台输入的格式为
			  * A=B,C=D这样的格式，首先根据逗号进行split
			  * 这样得到一个个键值对，在根据等于去split，这
			  * 样可以把键值对分开，放入map中去
			  * 参数之间用逗号隔开
			  */
			 String runParams=httpJobInst.getRunParams();
			 if(runParams!=null&&!("".equals(runParams))){
				 String[] strs=runParams.split(",");
				 Map<String,Object> map=new HashMap<String,Object>();
				 for(String str:strs){
					 if(str!=null){
						 String[] strs1=str.split("=");
						 map.put(strs1[0],strs1[1]);
					 }
				 }
				 List<NameValuePair> nvp=new ArrayList<NameValuePair>();
				 Set<Entry<String, Object>> sets=map.entrySet();
				 Iterator<Entry<String,Object>> iterator=sets.iterator();
				 while(iterator.hasNext()){
					Entry<String,Object> entry=iterator.next();	 
					nvp.add(new BasicNameValuePair(entry.getKey(),(String)entry.getValue()));	 
				 }
				 httpPost.setEntity(new UrlEncodedFormEntity(nvp));
			 }
			
			 //发送请求,并得到相应返回值
			 HttpEntity entity=null;
			 HttpResponse response=httpClient.execute(httpPost);
			 
			 //得到状态码
			 StatusLine statusLine=response.getStatusLine();
			 int statusCode=statusLine.getStatusCode();
			 String strStatusCode=""+statusCode;
			 
			 //得到消息实体里面的内容，这个里面的内容可能是正确的，也可能是错误的
			 entity=response.getEntity();
			 InputStream is=entity.getContent();
	    	 Scanner scn=new Scanner(is);
	    	 StringBuffer sb=new StringBuffer();
	    	 while(scn.hasNextLine()){
	    		 String text = scn.nextLine();
	    		 sb.append(text);
	    	 }
	    	 String str=sb.toString();
	    	 rm.setSuccess(true);
			 logger.info(str);
			 if(strStatusCode.startsWith("4")||strStatusCode.startsWith("5")){
				 TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
				 tspe.setErrorCode(ERRORCODE);
				 tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
				 tspe.setBriefDescription(str);
				 rm.setSuccess(false);
				 rm.setResult(tspe);
				 logger.error("运行http作业出错:【{}】", tspe.getMessage());	
				}else{
				 logger.info("运行http作业结束:【{}】",httpJobInst);
				}
		 }catch(Exception e){
			e.printStackTrace();
			logger.error("", e);
			TaskSchedulingPlatformException tspe=new TaskSchedulingPlatformException();
			tspe.setErrorCode(ERRORCODE);
			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
			tspe.setBriefDescription(e.getMessage());
			rm.setSuccess(false);
			rm.setResult(tspe);
			logger.error("运行http作业出错:【{}】", tspe.getMessage());
		 }
		 return rm;
	}

	public static void main(String[] args){
		//MyThread1 thread1=new MyThread1();
		//MyThread2 thread2=new MyThread2();
		MyThread3 thread3=new MyThread3();
		//MyThread4 thread4=new MyThread4();
		//MyThread5 thread5=new MyThread5();
		//thread1.start();
		//thread2.start();
		thread3.start();
		//thread4.start();
		//thread5.start();
	}

	@Override
	public String getType() {
		return "http";
	}

	@Override
	public void stop () {

	}

	@Override
	public void pause () {

	}

}

class MyThread1 extends Thread{
public void run(){
HttpJobExecute httpJobExecute=new HttpJobExecute();
        HttpJobInst httpJobInst=new HttpJobInst();
        httpJobInst.setHttpUrl("http://localhost:8088/httpclient/hello");
        httpJobInst.setRunParams("name=sb,id=2,age=5");
        httpJobExecute.execute(httpJobInst);
}
}

class MyThread2 extends Thread{
    public void run(){
        HttpJobExecute httpJobExecute=new HttpJobExecute();
        HttpJobInst httpJobInst=new HttpJobInst();
        httpJobInst.setHttpUrl("http://localhost:8088/tsp-service/jobConfigController/getJobPage");
        //httpJobInst.setRunParams(runParams)
        httpJobExecute.execute(httpJobInst);
    }
}

//8a11a87a-6441-4bc6-8ca7-ae0604c318ca
//{"name":"142","runParams":"参数之间用逗号隔开","ignoreErr":"2","retryCnt":"3","retrySec":"60","httpUrl":"14"}
class MyThread3 extends Thread{
public void run(){
        HttpJobExecute httpJobExecute=new HttpJobExecute();
            HttpJobInst httpJobInst=new HttpJobInst();
            httpJobInst.setHttpUrl("http://localhost:8088/tsp-service/jobConfigController/getJonConfig");
            //httpJobInst.setRunParams("type=http;id=8a11a87a-6441-4bc6-8ca7-ae0604c318ca;jsonData={name:142,runParams:参数之间用逗号隔开,ignoreErr:2,retryCnt:3,retrySec:60,httpUrl:14}");
            httpJobExecute.execute(httpJobInst);
}
}

class MyThread4 extends Thread{
    public void run(){
         HttpJobExecute httpJobExecute=new HttpJobExecute();
            HttpJobInst httpJobInst=new HttpJobInst();
            httpJobInst.setHttpUrl("http://localhost:8088/httpclient/hello");
            httpJobInst.setRunParams("name=sb,id=2,age=5");
            httpJobExecute.execute(httpJobInst);
    }
}

class MyThread5 extends Thread{
    public void run(){
         HttpJobExecute httpJobExecute=new HttpJobExecute();
            HttpJobInst httpJobInst=new HttpJobInst();
            httpJobInst.setHttpUrl("http://localhost:8088/httpclient/hello");
            httpJobInst.setRunParams("name=sb,id=2,age=5");
            httpJobExecute.execute(httpJobInst);
    }
}
