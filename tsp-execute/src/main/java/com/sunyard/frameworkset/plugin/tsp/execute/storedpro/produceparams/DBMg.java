package com.sunyard.frameworkset.plugin.tsp.execute.storedpro.produceparams;

import java.sql.Connection;


public interface DBMg {
	 public void execute(String procedureParam, Connection conn, String procedureName, String user);
}
