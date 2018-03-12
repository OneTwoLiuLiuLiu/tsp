package com.sunyard.frameworkset.plugin.tsp.execute.storedpro.dbutil;


import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;

public class UrlAndDriverFactory {
   public static String getUrl(String dataBaseType,String dataBaseIp,String dataBasePort,String dataBaseName){
	   
	   if(dataBaseType==null){
		   TaskSchedulingPlatformException tspe=new TaskSchedulingPlatformException();
		   tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
		   tspe.setBriefDescription("数据库连接参数中数据库类型不能为空");
		   throw tspe;
	   }
	   else if(dataBaseIp==null){
		   TaskSchedulingPlatformException tspe=new TaskSchedulingPlatformException();
		   tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
		   tspe.setBriefDescription("数据库连接参数中ip号不能为空");
		   throw tspe;   
	   }
	   else if(dataBasePort==null){
		   TaskSchedulingPlatformException tspe=new TaskSchedulingPlatformException();
		   tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
		   tspe.setBriefDescription("数据库连接参数中端口不能为空");
		   throw tspe; 
	   }else if(dataBaseName==null){
		   TaskSchedulingPlatformException tspe=new TaskSchedulingPlatformException();
		   tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
		   tspe.setBriefDescription("数据库连接参数中数据库名不能为空");
		   throw tspe; 
	   }else{
		   if("mysql".equals(dataBaseType)||"db2".equals(dataBaseType)||"postgresql".equals(dataBaseType)){
			   return "jdbc:"+dataBaseType+"://"+dataBaseIp+":"+dataBasePort+"/"+dataBaseName;
		   }
		   else if("oracle".equals(dataBaseType)){
			   return "jdbc:"+dataBaseType+":thin:@"+dataBaseIp+":"+dataBasePort+":"+dataBaseName;   
		   }else{
			    throw new RuntimeException("你要找的数据库驱动类型不存在");
		   }
	   }
   }
   
   public static String getDriver(String dataBaseType){
	   
	   if(dataBaseType==null){
		   TaskSchedulingPlatformException tspe=new TaskSchedulingPlatformException();
		   tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
		   tspe.setBriefDescription("数据库连接参数中数据库类型不能为空");
		   throw tspe;
	   }
	   else if("oracle".equals(dataBaseType))
		{
			return "oracle.jdbc.driver.OracleDriver";
		}
		else if("db2".equals(dataBaseType)){
			return "com.ibm.db2.jcc.DB2Driver";
		}
		else if("mysql".equals(dataBaseType)){
			return "com.mysql.jdbc.Driver";
		}else if("postgresql".equals(dataBaseType)){
			return "org.postgresql.Driver";
		}else {
			throw new RuntimeException("你要的数据库类型的驱动不存在");
		}
   }
}
