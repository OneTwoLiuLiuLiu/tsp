package com.sunyard.frameworkset.plugin.tsp.execute.kettle;

import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.KtrJobInst;
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

public class KtrJobExecute extends JobExecute {
	private static final String ERRORCODE = "JBET002";
	private static final Logger logger = LoggerFactory.getLogger(KtrJobExecute.class);

	public KtrJobExecute() {
		try {
			KettleExecuteEnvironment.init();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args){
		KtrJobInst jobInst = new KtrJobInst();
		jobInst.setName("ktr测试");
		jobInst.setType("ktr");
		jobInst.setRunParams("");
		jobInst.setJobId("11111");
		jobInst.setIgnoreErr("2");
		jobInst.setRetryCnt(3);
		jobInst.setPlanInstId("22222");
		jobInst.setRunHostName("mohanyun");
		jobInst.setRep("test");
		jobInst.setDir("/");
		jobInst.setTrans("java");
		KtrJobExecute ktrJobExecute = new KtrJobExecute();
		ktrJobExecute.execute(jobInst);
	}

	public ResultMessage execute(JobInst jobinst) {

		try {
			KettleExecuteEnvironment.init();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		logger.info("开始运行ktr转换;实例信息:【{}】", jobinst);
		KtrJobInst ktrJobInst = (KtrJobInst) jobinst;
		ResultMessage rm = new ResultMessage();
		try {
			executeKettle(ktrJobInst);
		} catch (Exception e) {
			e.printStackTrace();
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
			tspe.setErrorCode(ERRORCODE);
			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
			tspe.setBriefDescription("ktr执行错误:" + e.getMessage());
			rm.setSuccess(false);
			rm.setResult(tspe);
			logger.error("执行失败:", e);
		}
		return rm;
	}

	private void executeKettle(JobInst jobinst) throws Exception {
		logger.info("【开始执行Ktr任务】");
		Map<String, String> a = new HashMap<String, String>();
		KtrJobInst ktrJobInst = (KtrJobInst) jobinst;
		for (String name : getFiledName(ktrJobInst)) {
			String value = getMethodValue(name, jobinst);
			// 避免空字符串""引发文件查找不到
			value = "".equals(value) ? null : value;
			if (name.equals("file")) {
				if (value != null && checkFilePath(value)) {
					a.put(name, value);
				}
			} else if (name == "trans") {
				a.put("tran", value);
			} else if (name.equals("runParams")) {
				a.put("params", value);
			}
			a.put(name, value);
		}
		KettleExecuteEnvironment.ktrExcuteMethod.invoke(null, a);
		logger.info("【执行Ktr任务结束】");
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

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "ktr";
	}

	@Override
	public void stop () {

	}

	@Override
	public void pause () {

	}
}