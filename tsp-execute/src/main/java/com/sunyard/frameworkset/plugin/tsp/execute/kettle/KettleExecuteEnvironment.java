package com.sunyard.frameworkset.plugin.tsp.execute.kettle;

import com.sunyard.frameworkset.plugin.tsp.execute.kettle.utils.ClassLoaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

public class KettleExecuteEnvironment {
	private static boolean isInit = false;
	private static final Logger logger = LoggerFactory.getLogger(KettleExecuteEnvironment.class);
	public static Method ktrExcuteMethod;
	public static Method kjbExcuteMethod;

	public Method getKtrExcuteMethod() {
		return ktrExcuteMethod;
	}

	public Method getKjbExcuteMethod() {
		return kjbExcuteMethod;
	}

	public static synchronized void init() throws Exception {
		if (!isInit) {
			logger.info("开始初始化Kettle环境变量");
			PropertyManager.init();
			String KETTLE_HOME = System.getProperty("KETTLE_HOME");
			if (KETTLE_HOME == null) {
				KETTLE_HOME = PropertyManager.getVaule("kettleHome");
			}
			if (KETTLE_HOME != null) {
				if (new File(KETTLE_HOME).exists()) {
					System.setProperty("KETTLE_HOME", KETTLE_HOME);
					System.setProperty("DI_HOME", KETTLE_HOME);
					System.setProperty("KETTLE_JNDI_ROOT", KETTLE_HOME+File.separator+"simple-jndi");
				}else{
					throw new RuntimeException("KETTLE_HOME所对应的路径出错,请检查路径是否正确;路劲为:"+KETTLE_HOME);
				}
			}else{
				throw new RuntimeException("请设置KETTLE_HOME环境变量,或者在 配置文件中设置kettleHome变量");
			}
			AutoLoaderJar.LoaderJar();
			Class kettlePluginClass = ClassLoaderUtil.loadClass("com.sunyard.frameworkset.plugin.tsp.execute.kettle.plugin.KettlePlugin");
			ktrExcuteMethod = kettlePluginClass.getMethod("runKtrJob",Map.class);
			kjbExcuteMethod = kettlePluginClass.getMethod("runKjbJob",Map.class);
			logger.info("初始化kettle环境变量结束");
			isInit = true;
		}
	}

}
