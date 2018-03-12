package com.sunyard.frameworkset.plugin.tsp.execute.storedpro;


import com.sunyard.frameworkset.plugin.tsp.execute.storedpro.dbutil.ConnectionFactory;
import com.sunyard.frameworkset.plugin.tsp.execute.storedpro.dbutil.UrlAndDriverFactory;
import com.sunyard.frameworkset.plugin.tsp.execute.storedpro.produceparams.DBMg;
import com.sunyard.frameworkset.plugin.tsp.execute.storedpro.produceparamsimpl.DB2MgImpl;
import com.sunyard.frameworkset.plugin.tsp.execute.storedpro.produceparamsimpl.MysqlMgImpl;
import com.sunyard.frameworkset.plugin.tsp.execute.storedpro.produceparamsimpl.OracleMgImpl;
import com.sunyard.frameworkset.plugin.tsp.execute.storedpro.produceparamsimpl.PostGreSqlMgImpl;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.StoreProJobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import com.sunyard.frameworkset.plugin.tsp.spi.jobexecute.JobExecute;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;


public class StoreProJobExecute extends JobExecute {

    private static final String ERRORCODE="JBET010";
    private static final Logger logger = LoggerFactory.getLogger(StoreProJobExecute.class);

    public ResultMessage execute(JobInst jobinst) {
        //logger.info("开始运行存储过程作业;实例信息:【{}】",jobinst);
        ResultMessage rm=new ResultMessage();
        CallableStatement cs=null;
        Connection conn=null;
        StoreProJobInst sp=null;
        try {
        sp=(StoreProJobInst)jobinst;
        logger.info("开始运行存储过程作业;存储过程实例信息:"+";存储过程数据库类型="+sp.getDataBaseType()+";存储过程数据库名字="+sp.getDataBaseName()
                +";存储过程数据库端口号="+sp.getDataBasePort()+";存储过程数据库Ip="+sp.getDataBaseIp()+";存储过程的名字为="+sp.getName());

        //数据库类型,并生成数据库驱动
        String dataBaseType=sp.getDataBaseType();
        if("2".equals(dataBaseType)){
            dataBaseType="mysql";
        }else if("1".equals(dataBaseType)){
            dataBaseType="db2";
        }else if("0".equals(dataBaseType)){
            dataBaseType="oracle";
        }else if("3".equals(dataBaseType)){
            dataBaseType="postgresql";
        }
        String dataBaseDriver= UrlAndDriverFactory.getDriver(dataBaseType);

        //获取数据库的ip，端口号，数据库名字，并生成数据库Url
        String dataBaseIp=sp.getDataBaseIp();
        String dataBasePort=sp.getDataBasePort();
        String dataBaseName=sp.getDataBaseName();
        String dataBaseUrl=UrlAndDriverFactory.getUrl(dataBaseType, dataBaseIp, dataBasePort, dataBaseName);

        //获取数据库用户名
        String user=sp.getDataBaseUser();
        //获取数据库密码
        String pwd=sp.getDataBasePwd();
        //获取存储过程名字
        String procedureName=sp.getProcedureName();
        //连接数据库，得到连接
        conn= ConnectionFactory.getConnectionMap(dataBaseDriver, dataBaseUrl, user, pwd);

        //处理参数，拼sql
        //获取存储过程参数，并做相应的处理
        String procedureParam=sp.getRunParams();

        DBMg dBMg=null;
        if("postgresql".equals(dataBaseType)){
            dBMg=new PostGreSqlMgImpl();
            }
        else if("oracle".equals(dataBaseType)){
            dBMg=new OracleMgImpl();
            }
        else if("mysql".equals(dataBaseType)){
            dBMg=new MysqlMgImpl();
            }
        else if("db2".equals(dataBaseType)){
            dBMg=new DB2MgImpl();
        }
        dBMg.execute(procedureParam, conn, procedureName, user);
        logger.info("运行存储过程作业完成;存储过程实例信息:"+";存储过程数据库类型="+sp.getDataBaseType()+";存储过程数据库名字="+sp.getDataBaseName()
                    +";存储过程数据库端口号="+sp.getDataBasePort()+";存储过程数据库Ip="+sp.getDataBaseIp()+";存储过程的名字为="+sp.getName());
        } catch (Exception e) {
            e.printStackTrace();
            TaskSchedulingPlatformException tspe=null;
            //加上这句话以后logback日志中才会输出堆栈信息
            logger.error("", e);
            if(e instanceof TaskSchedulingPlatformException){
                tspe=((TaskSchedulingPlatformException) e);
                tspe.setErrorCode(ERRORCODE);
                rm.setSuccess(false);
                rm.setResult(e);
                logger.error("存储过程运行出错:"+"存储过程作业Id="+sp.getJobId()+"存储过程作业名字="+sp.getName()
                            +"存储过程名字="+sp.getProcedureName()+e.getMessage());
            }else{
                tspe = new TaskSchedulingPlatformException();
                tspe.setErrorCode(ERRORCODE);
                tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
                tspe.setBriefDescription(e.getMessage());
                rm.setSuccess(false);
                rm.setResult(tspe);
                logger.error("存储过程运行出错:"+"存储过程作业Id="+sp.getJobId()+"存储过程作业名字="+sp.getName()
                        +"存储过程名字="+sp.getProcedureName()+tspe.getErrorMessage());
            }
            throw tspe;
        }finally{
            if(cs!=null){
                try {
                    cs.close();
                } catch (Exception e){
                    e.printStackTrace();
                    TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
                    tspe.setErrorCode(ERRORCODE);
                    tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
                    tspe.setBriefDescription("数据库关闭连接失败"+e.getMessage());
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
                    tspe.setErrorCode(ERRORCODE);
                    tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
                    tspe.setBriefDescription("数据库关闭连接失败"+e.getMessage());
                }
            }
        }
        return rm;
    }

    public static void main(String[] args){
        //StoreProJobExecute.db2Test();
        //StoreProJobExecute.mysqlTest();
        //StoreProJobExecute.oracleTest();
        //Thread thread1=new MyThread1();
        Thread thread4=new MyThread4();
        //MyThread4 thread4=new MyThread4();
        //thread1.start();
        //thread4.start();
        thread4.start();
    }

    @Override
    public String getType() {

        return "storepro";
    }

    @Override
    public void stop () {

    }

    @Override
    public void pause () {

    }
}

//postgresql数据库的测试
    class MyThread4 extends Thread{
        public void run(){
            StoreProJobExecute storeProJobExecute=new StoreProJobExecute();
            StoreProJobInst storeProJobInst=new StoreProJobInst();
            storeProJobInst.setDataBaseType("3");
            storeProJobInst.setDataBaseName("jsd1410");
            storeProJobInst.setDataBaseIp("localhost");
            storeProJobInst.setDataBasePort("5432");
            storeProJobInst.setDataBaseUser("postgres");
            storeProJobInst.setDataBasePwd("123456");
            storeProJobInst.setProcedureName("sp_users_select");
            //storeProJobInst.setRunParams("110,zhouli,2898");
            storeProJobExecute.execute(storeProJobInst);
        }
    }


//db2数据库的测试
class MyThread3 extends Thread{
    public void run(){
        StoreProJobExecute storeProJobExecute=new StoreProJobExecute();
        StoreProJobInst storeProJobInst=new StoreProJobInst();
        storeProJobInst.setDataBaseType("1");
        storeProJobInst.setDataBaseName("tsp");
        storeProJobInst.setDataBaseIp("172.16.16.235");
        storeProJobInst.setDataBasePort("50000");
        storeProJobInst.setDataBaseUser("tsp");
        storeProJobInst.setDataBasePwd("tsp123");
        storeProJobInst.setProcedureName("insertemp");
        storeProJobInst.setRunParams("110,wangwu,21");
        storeProJobExecute.execute(storeProJobInst);
    }
}

//mysql数据库的测试
class MyThread1 extends Thread{
    public void run(){
        StoreProJobExecute storeProJobExecute=new StoreProJobExecute();
        StoreProJobInst storeProJobInst=new StoreProJobInst();
        storeProJobInst.setDataBaseType("2");
        storeProJobInst.setDataBaseName("test");
        storeProJobInst.setDataBaseIp("localhost");
        storeProJobInst.setDataBasePort("3306");
        storeProJobInst.setDataBaseUser("root");
        storeProJobInst.setDataBasePwd("123456");
        storeProJobInst.setProcedureName("selectemp");
        //storeProJobInst.setRunParams("110,wangwu,21,0845010123");
        storeProJobExecute.execute(storeProJobInst);
    }
}

//oracle数据库的测试
class MyThread2 extends Thread{
    public void run(){
        StoreProJobExecute storeProJobExecute=new StoreProJobExecute();
        StoreProJobInst storeProJobInst=new StoreProJobInst();
        storeProJobInst.setDataBaseType("0");
        storeProJobInst.setDataBaseName("xe");
        storeProJobInst.setDataBaseIp("localhost");
        storeProJobInst.setDataBasePort("1521");
        storeProJobInst.setDataBaseUser("system");
        storeProJobInst.setDataBasePwd("123456");
        storeProJobInst.setProcedureName("insertemp");
        storeProJobInst.setRunParams("110,zww");
        storeProJobExecute.execute(storeProJobInst);
    }
}
	
	
	
	
