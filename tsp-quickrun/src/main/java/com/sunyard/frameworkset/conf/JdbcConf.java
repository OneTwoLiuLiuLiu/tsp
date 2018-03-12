package com.sunyard.frameworkset.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Write class comments here
 * User: liulu
 * Date: 2016/8/10 10:00
 * version $Id : JdbcConf.java, v 0.1 Exp $
 */
@Configuration
@Profile( "dev")
public class JdbcConf {

	@Value( "${inceptor.url:url}" )
	private String url;

	@Value( "${inceptor.driver:org.apache.hive.jdbc.HiveDriver}" )
	private String driverClassName;

	@Value( "${inceptor.username:none}" )
	private String username;

	@Value( "${inceptor.password:none}" )
	private String password;

	@Value( "${pool.minIdle}" )
	private String minIdle;

	@Value( "${pool.maxActive}" )
	private String maxActive;

	@Value( "${pool.initialSize}" )
	private String initialSize;

	@Value( "${pool.minEvictableIdleTimeMillis}" )
	private String minEvictableIdleTimeMillis;

	@Value( "${pool.maxOpenPreparedStatements}" )
	private String maxOpenPreparedStatements;

	@Value( "${pool.timeBetweenEvictionRunsMillis}" )
	private String timeBetweenEvictionRunsMillis;

	@Value( "${inceptor.enable:false}" )
	private boolean enableInceptor;

	@Bean
	public DataSource inceptorDataSource () throws SQLException {
		if ( enableInceptor ) {
			DruidDataSource inceptorDataSource = new DruidDataSource ();
			inceptorDataSource.setUrl ( url );
			inceptorDataSource.setDriverClassName ( driverClassName );
			inceptorDataSource.setUsername ( username );
			inceptorDataSource.setPassword ( password );
			inceptorDataSource.setFilters ( "stat" );
			inceptorDataSource.setTimeBetweenEvictionRunsMillis ( Long.parseLong ( timeBetweenEvictionRunsMillis ) );
			inceptorDataSource.setValidationQuery ( "SELECT 'x' FROM dual" );
			inceptorDataSource.setMinIdle ( Integer.parseInt ( minIdle ) );
			inceptorDataSource.setMaxActive ( Integer.parseInt ( maxActive ) );
			inceptorDataSource.setInitialSize ( Integer.parseInt ( initialSize ) );
			inceptorDataSource.setMinEvictableIdleTimeMillis ( Long.parseLong ( minEvictableIdleTimeMillis ) );
			inceptorDataSource.setMaxOpenPreparedStatements ( Integer.parseInt ( maxOpenPreparedStatements ) );
			return inceptorDataSource;
		}
		return null;

	}

	@Bean
	public JdbcOperations inceptorJdbcTemplate ( @Qualifier( "inceptorDataSource" ) DataSource inceptorDataSource ) {
		if ( inceptorDataSource == null ) {
			return null;
		} else {
			return new JdbcTemplate ( inceptorDataSource );
		}
	}
}
