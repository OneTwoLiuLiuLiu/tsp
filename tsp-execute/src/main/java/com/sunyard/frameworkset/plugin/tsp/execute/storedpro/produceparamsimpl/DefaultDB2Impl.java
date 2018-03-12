package com.sunyard.frameworkset.plugin.tsp.execute.storedpro.produceparamsimpl;


import com.sunyard.frameworkset.plugin.tsp.execute.storedpro.produceparams.DBMg;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DefaultDB2Impl implements DBMg {
   
	public void execute(String procedureParam, Connection conn,
			String procedureName, String user) {

		CallableStatement cs=null;
		ResultSet rs=null;
		try {
			if(procedureParam==null||"".equals(procedureName)){
				List<String> ordinalPositions=new ArrayList<String>();
				List<Integer> dataTypes=new ArrayList<Integer>();
				rs = conn.getMetaData().getProcedureColumns(null, user.toUpperCase(), procedureName.toUpperCase(), null);	
				while(rs.next()){
					ordinalPositions.add(rs.getString("COLUMN_NAME"));
					dataTypes.add(rs.getInt("DATA_TYPE"));
				}
				StringBuffer sb=new StringBuffer("{ call ");
				sb.append(procedureName);
				sb.append("(");
				for(int i=0;i<ordinalPositions.size();i++){
					if(i!=ordinalPositions.size()-1){
						sb.append("?,");
					}else{
						sb.append("?");
					}
				}
				sb.append(") }");
				String str=sb.toString();
				cs=conn.prepareCall(str);
				for(int i=0;i<ordinalPositions.size();i++){
					cs.registerOutParameter(ordinalPositions.get(i), dataTypes.get(i));	
				}
				cs.execute();
			}else{

				String[] strs=procedureParam.split(",");
				//输入和输入输出的
				List<String> ordinalPositions=new ArrayList<String>();
				List<Integer> dataTypes=new ArrayList<Integer>();
				List<Integer> columnTypes=new ArrayList<Integer>();
				//输出
				List<String> ordinalPosition=new ArrayList<String>();
				List<Integer> dataType=new ArrayList<Integer>();
				rs = conn.getMetaData().getProcedureColumns(null, user.toUpperCase(), procedureName.toUpperCase(), null);
				while(rs.next()){
					short columnType=rs.getShort("COLUMN_TYPE");
					if(columnType==4){
						ordinalPosition.add(rs.getString("COLUMN_NAME"));
						dataType.add(rs.getInt("DATA_TYPE"));
					}else{
						ordinalPositions.add(rs.getString("COLUMN_NAME"));
						dataTypes.add(rs.getInt("DATA_TYPE"));
					}
				}
				
				StringBuffer sb=new StringBuffer("{ call ");
				sb.append(procedureName);
				sb.append("(");
				for(int i=0;i<ordinalPositions.size()+ordinalPosition.size();i++){
					if(i!=ordinalPositions.size()+ordinalPosition.size()-1){
						sb.append("?,");
					}else{
						sb.append("?");
					}
				}
				sb.append(") }");
				String str=sb.toString();
				cs=conn.prepareCall(str);
				for(int i=0;i<ordinalPositions.size();i++){
					cs.setObject(ordinalPositions.get(i), strs[i], dataTypes.get(i));
				} 
				for(int i=0;i<ordinalPosition.size();i++){
					cs.registerOutParameter(ordinalPosition.get(i),dataType.get(i));
				}
				cs.execute();
			}
			
		}catch (Exception e) {
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
			tspe.setBriefDescription("给存储过程赋值时发现错误"+e.getMessage());
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
