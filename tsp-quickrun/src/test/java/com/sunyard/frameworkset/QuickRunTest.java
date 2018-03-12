package com.sunyard.frameworkset;


import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * write comments
 * User : weixi
 * Date : 2016/11/8 11:26
 * File : QuickRunTest.java
 */
public class QuickRunTest {
	@Test
	public void main () throws Exception {
		QuickRun.main ( new String[]{} );
		while ( true ){
		//1 分钟
		Thread.sleep ( 1 * 60 * 1000 );
	}
}

	@Test
	public void stringutils(){
		String[] arr = StringUtils.splitByWholeSeparator ( "/favicon.ico,bootstrap/**,js/**,styles/**,images/**,plugin/*/*/static/**,plugin/*/*/images/**,plugin/*/*/js/**,plugin/*/*/css/**","," );
		for ( String path : arr ){
			System.out.println (path);
		}
	}


}
