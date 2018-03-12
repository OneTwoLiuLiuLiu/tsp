package com.sunyard.frameworkset.plugin.tsp.execute.kettle;

import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.KjbJobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import com.sunyard.frameworkset.plugin.tsp.spi.jobexecute.JobExecute;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class KjbJobExecute extends JobExecute {
	private static final String ERRORCODE = "JBET001";
	private static final Logger logger = LoggerFactory
			.getLogger(KjbJobExecute.class);

	public KjbJobExecute() {
		try {
			KettleExecuteEnvironment.init();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ResultMessage execute(JobInst jobinst) {
		logger.info("开始运行Kjb转换;实例信息:【{}】", jobinst);
		KjbJobInst kjbJobInst = (KjbJobInst) jobinst;
		ResultMessage rm = new ResultMessage();
		try {
			executeKettle(kjbJobInst);
		} catch (Exception e) {
			e.printStackTrace();
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
			tspe.setErrorCode(ERRORCODE);
			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
			tspe.setBriefDescription("Kjb执行错误:" + e.getMessage());
			rm.setSuccess(false);
			rm.setResult(tspe);
			logger.error("kjb脚本运行出错", e);
		}
		return rm;
	}

	private void executeKettle(JobInst jobinst) throws Exception {
		logger.info("【开始执行Kjb任务】");
		Map<String, String> a = new HashMap<String, String>();
		KjbJobInst kjbJobInst = (KjbJobInst) jobinst;
		for (String name : getFiledName(kjbJobInst)) {
			String value = getMethodValue(name, jobinst);
			// 避免空字符串""引发文件查找不到
			value = "".equals(value) ? null : value;
			if (name.equals("file")) {
				if (value != null && checkFilePath(value)) {
					a.put(name, value);
				}
			} else if (name.equals("jobs")) {
				a.put("jobName", value);
			} else if (name.equals("runParams")) {
				a.put("params", value);
			}
			a.put(name, value);
		}
		KettleExecuteEnvironment.kjbExcuteMethod.invoke(null, a);
		logger.info("【执行Kjb任务结束】");
	}

	private boolean checkFilePath(String value) {
		if (!new File(value).exists()) {
			throw new RuntimeException("该文件不存在,请检查文件路径是否正确;文件路劲为:" + value);
		}
		return true;
	}

	/**
	 * 获取属性的值
	 * 
	 * @param name
	 * @param jobinst
	 * @return
	 */
	private static String getMethodValue(String name, JobInst jobinst) {
		Class Kjb = jobinst.getClass();
		String methodName = "get"
				+ name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
						.toUpperCase());
		Method m;
		String value = null;
		try {
			m = Kjb.getDeclaredMethod(methodName);
			value = (String) m.invoke(jobinst);
		} catch (Exception e) {
			throw new RuntimeException("Kjb获取参数值出错:" + e.getMessage());
		}
		return value;
	}

	/**
	 * 获取字段名
	 * 
	 * @param o
	 * @return
	 */
	private static String[] getFiledName(Object o) {
		Field[] fields = o.getClass().getDeclaredFields();
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}

	public static void main(String[] args) {

		KjbJobInst kjbInst = new KjbJobInst();
		//kjbInst.setFile("C:\\Users\\mhy\\Desktop\\ktr小文件\\runjava.kjb");
		kjbInst.setJobId("1111");
		kjbInst.setPlanInstId("111111");
		kjbInst.setDir("/");
		kjbInst.setRep("test");
		kjbInst.setJobs("runjava");
		KjbJobExecute kjbExecute = new KjbJobExecute();
		try {
			KettleExecuteEnvironment.init();
		} catch (Exception e) {
			e.printStackTrace ();
		}

		kjbExecute.execute(kjbInst);

	}

	@Override
	public String getType() {
		return "kjb";
	}

	@Override
	public void stop () {

	}

	@Override
	public void pause () {

	}

}