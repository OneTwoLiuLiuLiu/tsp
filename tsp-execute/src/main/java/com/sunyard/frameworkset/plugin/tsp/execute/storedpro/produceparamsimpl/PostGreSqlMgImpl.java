package com.sunyard.frameworkset.plugin.tsp.execute.storedpro.produceparamsimpl;


import com.sunyard.frameworkset.plugin.tsp.execute.storedpro.produceparams.DBMg;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PostGreSqlMgImpl  implements DBMg {

	public void  execute(String procedureParam,Connection conn, String procedureName, String user) {
			CallableStatement cs=null;
			String[] strs=null;
			ResultSet rs=null;
			try{
				//无参数
			if(procedureParam==null||"".equals(procedureParam)){
	
					StringBuffer sb=new StringBuffer("{?=call ");
					sb.append(procedureName);
					sb.append("() }");
					String str=sb.toString();
					conn.setAutoCommit(false);
					cs=conn.prepareCall(str);
					rs=conn.getMetaData().getProcedureColumns(null, "%", procedureName, null);
					int columnType=0;
					while(rs.next()){
						columnType=rs.getInt("DATA_TYPE");	
					}
						cs.registerOutParameter(1,columnType);
						
				} 
				  //有参数
			      else{
			    	  strs=procedureParam.split(",");
						StringBuffer sb=new StringBuffer("{?= call ");
							sb.append(procedureName);
							sb.append("(");
							for(int i=0;i<strs.length;i++){
								if(i!=strs.length-1){
									sb.append("?,");
								}else{
									sb.append("?");
								}
							}
							sb.append(") }");
							String str=sb.toString();
							conn.setAutoCommit(false);
							cs=conn.prepareCall(str);
					rs=conn.getMetaData().getProcedureColumns(null, "%", procedureName, null);
					List<Integer> dataTypes=new ArrayList<Integer>();
					List<Integer> dataTypes1=new ArrayList<Integer>();
					while(rs.next()){
						dataTypes.add(rs.getInt("DATA_TYPE"));
					}
					dataTypes1=dataTypes.subList(1,dataTypes.size());
					cs.registerOutParameter(1, dataTypes.get(0));
					for(int i=0;i<strs.length;i++){
						cs.setObject(i+2, strs[i], dataTypes1.get(i));
					}
				}
			cs.execute();
			conn.commit();
			}
			catch (Exception e) {
					e.printStackTrace();
					TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
					tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
					tspe.setBriefDescription("给postgresql数据库存储过程赋值时报错"+e.getMessage());
					throw tspe;
				}finally{
					try {
						if(rs!=null){
							rs.close();	
						}
						if(cs!=null){
							cs.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
						TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
						tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
						tspe.setBriefDescription("数据库关闭时发现错误"+e.getMessage());
						throw tspe;
					}
				
				}
			}
	}