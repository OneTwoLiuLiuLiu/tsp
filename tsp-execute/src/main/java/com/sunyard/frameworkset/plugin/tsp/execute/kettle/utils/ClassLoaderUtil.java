package com.sunyard.frameworkset.plugin.tsp.execute.kettle.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public abstract class ClassLoaderUtil {
	
	private final static Logger log = LoggerFactory.getLogger(ClassLoaderUtil.class);
	
    private static URLClassLoader system = (URLClassLoader) ClassLoader.getSystemClassLoader();  
	
	private static Method addURL ;
	
	static {
		try {
			Method add = URLClassLoader.class.getDeclaredMethod("addURL",
					new Class[] { URL.class });
			add.setAccessible(true);  
			addURL = add;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}
	  
  
    /** 
     * 寰幆閬嶅巻鐩綍锛屾壘鍑烘墍鏈夌殑JAR鍖�
     */  
    private static final void loopFiles(File file, List<File> files) {  
        if (file.isDirectory()) {  
            File[] tmps = file.listFiles();  
            for (File tmp : tmps) {  
                loopFiles(tmp, files);  
            }  
        } else {  
            if (file.getAbsolutePath().endsWith(".jar") || file.getAbsolutePath().endsWith(".zip")) {  
                files.add(file);  
            }  
        }  
    }  
	  
    /** 
     * <pre> 
     * 鍔犺浇JAR鏂囦欢 
     * </pre> 
     * 
     * @param file 
     */  
    public static final void loadJarFile(File file) {  
        try {  
            addURL.invoke(system, new Object[] { file.toURI().toURL() });  
            System.out.println("加载的jar包路径:" + file.getAbsolutePath());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * <pre> 
     * 浠庝竴涓洰褰曞姞杞芥墍鏈塉AR鏂囦欢 
     * </pre> 
     * 
     * @param path 
     */  
    public static final void loadJarPath(String path) {  
        List<File> files = new ArrayList<File>();  
        File lib = new File(path);  
        loopFiles(lib, files);  
        for (File file : files) {  
            loadJarFile(file);  
        }
    }
    
    /**
     * 鍔犺浇涓�釜class
     * @param className		绫诲悕
     * @return
     */
    public static Class<?> loadClass(String className){
        Class<?> clazz = null;
        if (className == null) {
            return null;
        }
        ClassNotFoundException ex = null;        
        try {
            clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
        	ex = e;
        }
        if (clazz == null) {
            try {
				Class.forName(className);
			} catch (ClassNotFoundException e) {
				ex = e;
			}
        }        
        if(ex!=null){
        	log.error("load class error", ex);
        }
        return clazz;
    }
    
    

}
