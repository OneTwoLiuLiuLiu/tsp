package com.sunyard.frameworkset;

import com.sunyard.frameworkset.core.Version;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by liyingdan on 2016/8/2.
 */
@SpringBootApplication
@EnableScheduling
@ImportResource( { "classpath:*-context.xml"} )
@Profile ( "dev" )
@ComponentScan("${scan.packages}")
public class QuickRun extends SpringBootServletInitializer {

	public static void main ( String[] args ) {
		SpringApplication.run ( QuickRun.class, args );
		System.out.println ( "------------------------------------------------------------------------" );
		System.out.println ( "========================================================================" );
		System.out.println ( "--------------fap platform " + Version.getVersionString () + " started------------" );
		System.out.println ( "========================================================================" );
		System.out.println ( "------------------------------------------------------------------------" );
	}

	protected SpringApplicationBuilder configure ( SpringApplicationBuilder builder ) {
		return builder.sources ( QuickRun.class );
	}
}
