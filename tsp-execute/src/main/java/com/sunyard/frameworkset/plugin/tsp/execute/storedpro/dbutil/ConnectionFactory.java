package com.sunyard.frameworkset.plugin.tsp.execute.storedpro.dbutil;


import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ConnectionFactory {

    public static Map<String,Connection> map=new ConcurrentHashMap<String,Connection>();

    public static Connection getConnectionMap(String driver,String url,String user,String pwd) throws Exception{
        Connection conn =map.get(url);
        if(conn==null){
            conn=ConnectionFactory.createConnection(driver,url,user,pwd);
            map.put(url, conn);
        }else{
            if(conn.isClosed()){
                conn=ConnectionFactory.createConnection(driver,url,user,pwd);
                map.put(url, conn);
            }
        }
        return conn;
    }
    public synchronized static Connection createConnection(String driver,String url,String user,String pwd){
        Connection conn=null;
        try {
            Class.forName(driver);
            conn= DriverManager.getConnection(url,user,pwd);
        }catch (Exception e) {
            e.printStackTrace();
            TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
            tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
            tspe.setBriefDescription("数据库连接失败"+e.getMessage());
            throw tspe;
        }
        return conn;
    }

    public static void closeConnection(Connection conn){
        if(conn!=null){
            try{
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
                TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
                tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
                tspe.setBriefDescription("数据库关闭失败"+e.getMessage());
                throw tspe;
            }
        }
    }
}
