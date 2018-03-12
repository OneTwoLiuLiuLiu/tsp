package com.sunyard.frameworkset.plugin.tsp.execute.kettle.plugin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.RepositoryPluginType;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.RepositoriesMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositoryMeta;
import org.pentaho.di.repository.RepositoryOperation;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

public class KettlePlugin {
	 static {
		try {
			KettleEnvironment.init();
		} catch (KettleException e) {
			e.printStackTrace();
			throw new RuntimeException("Kettle环境变量初始化错误");
		}
	}
	
	public static boolean runKtrJob(Map<String, String> map){
		String rep = map.get("rep");
		String user = map.get("user");
		String pass = map.get("pass");
		String dir = map.get("dir");
		String tran = map.get("tran");
		String file = map.get("file");
		String params = map.get("params");
		RepositoryMeta repositoryMeta = null;
		Trans trans = null;
		TransMeta transMeta = new TransMeta();
		Repository repository = null;
		try {
				// 检查rep
				if (!StringUtils.isEmpty(rep)) {
					RepositoriesMeta repsinfo = new RepositoriesMeta();
					repsinfo.readData();
					// 找到所需资源库
					repositoryMeta = repsinfo.findRepository(rep);
					if (repositoryMeta != null) {
						repository = PluginRegistry.getInstance().loadClass(RepositoryPluginType.class, repositoryMeta,Repository.class);
						repository.init(repositoryMeta);
						// 连接资源库
						repository.connect(user != null ? user : null,pass != null ? pass : null);
						repository.getSecurityProvider().validateAction(RepositoryOperation.EXECUTE_TRANSFORMATION);
						// 默认为根路径
						RepositoryDirectoryInterface directory = repository.loadRepositoryDirectoryTree();
						if (!StringUtils.isEmpty(dir)) {
							directory = directory.findDirectory(dir);
						}
						if (directory != null) {
							if (!StringUtils.isEmpty(tran)) {
								transMeta = repository.loadTransformation(tran,directory, null, true, null);
								trans = new Trans(transMeta);
								trans.setRepository(repository);
							}
						} else {
							repositoryMeta = null;
							throw new RuntimeException("资源库:" + rep + "与路径:"+ dir + "不匹配");
						}
					} else {
						throw new RuntimeException("您输入的资源库名称:【" + rep+ "】,不存在，请核对.");
					}
				}else if(!StringUtils.isEmpty(file) && tran == null) {// 加载 file
					transMeta = new TransMeta(file);
					trans = new Trans(transMeta);
				}
		} catch (Exception e) {
			e.printStackTrace();
			trans = null;
			transMeta = null;
			if (repository != null) {
				repository.disconnect();
			}
			throw new RuntimeException(e.getMessage());
		}
		if (trans == null) {
			throw new RuntimeException("查找不到trans,trans名称为:【" + tran+ "】,请检查您的资源库路径或您的转换名.");
		}
		Result result = null;
		try {
			trans.setRepository(repository);
			trans.getTransMeta().setRepository(repository);
			Map<String, String> namedParams = null;
			if (!StringUtils.isEmpty(params)) {
				namedParams = generalJobform(params);
				String[] jobParams = transMeta.listParameters();
				for (String jobParam : jobParams) {
					String value= namedParams.get(jobParam);
					if(value != null){
						trans.setParameterValue(jobParam, value);
						trans.getTransMeta().setParameterValue(jobParam, value);
					}
				}
			}
			trans.getTransMeta().setInternalKettleVariables(trans);
			trans.copyParametersFrom(trans.getTransMeta());
			trans.activateParameters();
			trans.execute(null);
			trans.waitUntilFinished();
			result = trans.getResult();
			if (result.getResult()) {
				return true;
			} else {
				throw new RuntimeException((KettleLogStore.getAppender().getBuffer(trans.getLogChannelId(), false).toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}	
	}
	
	
	public static boolean runKjbJob(Map<String, String> map) {
		String rep = map.get("rep");
		String user = map.get("user");
		String pass = map.get("pass");
		String dir = map.get("dir");
		String jobName = map.get("jobName");
		String file = map.get("file");
		String params = map.get("params");
		RepositoryMeta repositoryMeta = null;
		Job job = null;
		JobMeta jobMeta = new JobMeta();
		Repository repository = null;
		try {	
				if (!StringUtils.isEmpty(rep)) {// 检查rep
					RepositoriesMeta repsinfo = new RepositoriesMeta();
					repsinfo.readData();
					repositoryMeta = repsinfo.findRepository(rep);// 找到所需资源库
					if (repositoryMeta != null) {
						repository = PluginRegistry.getInstance().loadClass(RepositoryPluginType.class, repositoryMeta,Repository.class);
						repository.init(repositoryMeta);
						repository.connect(user != null ? user : null,pass != null ? pass : null);
						repository.getSecurityProvider().validateAction(RepositoryOperation.EXECUTE_JOB);
						// 默认为根路径
						RepositoryDirectoryInterface directory = repository.loadRepositoryDirectoryTree();
						if (!StringUtils.isEmpty(dir)) {
							directory = directory.findDirectory(dir);
						}
						if (directory != null) {
							// 加载job
							if (!StringUtils.isEmpty(jobName)) {
								// 读取最后一次的
								jobMeta = repository.loadJob(jobName,directory, null, null);
								job = new Job(repository, jobMeta);
							}
						} else {
							// directory为null的话，即数据库资源库为null
							repositoryMeta = null;
							throw new RuntimeException("资源库:" + rep + "与路径:"+ dir + "不匹配");
						}
					} else {
						throw new RuntimeException("您输入的资源库名称:【" + rep+ "】,不存在，请重新设置.");
					}
				}else if(!StringUtils.isEmpty(file) && job == null) {// 加载 file
					jobMeta = new JobMeta(file, null, null);
					job = new Job(null, jobMeta);
				}
		} catch (Exception e) {
			e.printStackTrace();
			job = null;
			jobMeta = null;
			if (repository != null) {
				repository.disconnect();
			}
			throw new RuntimeException(e.getMessage());
		}
		if (job == null) {
			throw new RuntimeException("查找不到job,job名称为:【" + jobName+ "】,请检查您的资源库路径或您的作业名.");
		}
		Result result = null;
		try {
			job.setRepository(repository);
			job.getJobMeta().setRepository(repository);
			Map<String, String> namedParams = null;
			if (!StringUtils.isEmpty(params)) {
				namedParams = generalJobform(params);
				String[] jobParams = jobMeta.listParameters();
				for (String jobParam : jobParams) {
					String value= namedParams.get(jobParam);
					if(value != null){
						job.setParameterValue(jobParam, value);
						job.getJobMeta().setParameterValue(jobParam, value);
					}
				}
			}
			job.copyParametersFrom(job.getJobMeta());
			job.activateParameters();
			job.start();
			job.waitUntilFinished();
			result = job.getResult();
			if (result.getResult()) {
				return true;
			} else {
				throw new RuntimeException((KettleLogStore.getAppender().getBuffer(job.getLogChannelId(), false).toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}
		
	private static Map<String, String> generalJobform(String params) {
		Map<String, String> map = new HashMap<String, String>();
		if(params!=null && !"".equals(params)){
			String[] runParams = params.split(";");
			for (String runParam : runParams) {
				if("".equals(runParam.trim())){
					continue;
				}
				String[] p = runParam.split("=");
				if(p.length!=2){
					throw new RuntimeException("参数格式错误,请检查参数格式;参数为:"+params);
				}else{
					map.put(p[0].trim(), p[1].trim());
				}
			}
		}
		return map;
	}

}
