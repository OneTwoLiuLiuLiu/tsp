package com.sunyard.frameworkset.plugin.tsp.execute.kettle;

import com.sunyard.frameworkset.plugin.tsp.execute.kettle.utils.ClassLoaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoLoaderJar {
	private static final Logger logger = LoggerFactory.getLogger(AutoLoaderJar.class);
	public static void LoaderJar() throws Exception {
		logger.info("开始加载kettle所需的jar包");
		String libPath = PropertyManager.getVaule("libraries");
		String[] libPaths = libPath.split(";");
		for (String string : libPaths) {
			ClassLoaderUtil.loadJarPath(string);
		}		
		logger.info("加载kettle所需jar包结束");
	}

	public static void main(String[] args) throws Exception {
		LoaderJar();
	}
}
