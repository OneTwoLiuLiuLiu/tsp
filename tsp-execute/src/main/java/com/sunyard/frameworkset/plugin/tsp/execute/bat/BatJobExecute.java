package com.sunyard.frameworkset.plugin.tsp.execute.bat;

import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.BatJobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import com.sunyard.frameworkset.plugin.tsp.spi.jobexecute.JobExecute;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class BatJobExecute extends JobExecute {

	private static final String ERRORCODE = "JBET005";

	private static final Logger logger = LoggerFactory.getLogger(BatJobExecute.class);

	public ResultMessage execute(JobInst jobinst) {
		logger.info("开始运行bat脚本;实例信息:【{}】", jobinst);
		ResultMessage rm = new ResultMessage();
		try {
			BatJobInst batJobInst = (BatJobInst) jobinst;
			String command =batJobInst.getFilePath() + " "
					+ batJobInst.getRunParams();
			Process pro = null;

			pro = Runtime.getRuntime().exec(command);
			pro.waitFor();
			InputStream in = pro.getInputStream();
			String info = this.processStream(in, Charset.defaultCharset()
					.toString());
			rm.setSuccess(true);
			logger.info(info);
			InputStream errors = pro.getErrorStream();
			String error = this.processStream(errors, Charset.defaultCharset()
					.toString());
			if (error != null && !"".equals(error)) {
				TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
				tspe.setErrorCode(ERRORCODE);
				tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
				tspe.setBriefDescription(error);
				rm.setSuccess(false);
				rm.setResult(tspe);
				logger.error("bat脚本运行出错:【{}】", tspe.getMessage());
			}else{
				logger.info("bat脚本运行结束;实例信息:【{}】", jobinst);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
			tspe.setErrorCode(ERRORCODE);
			tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
			tspe.setBriefDescription(e.getMessage());
			rm.setSuccess(false);
			rm.setResult(tspe);
			logger.error("bat脚本运行出错:【{}】", tspe.getMessage());
		}
		return rm;
	}

	/** */
	/**
	 * @param in
	 * @param charset
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private String processStream(InputStream in, String charset)
			throws Exception {
		byte[] buf = new byte[1024];
		StringBuilder sb = new StringBuilder();
		while (in.read(buf) != -1) {
			sb.append(new String(buf, charset));
		}
		return sb.toString();
	}

//	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		System.out.println("请输入bat脚本的文件路径");
//		String filePath = sc.next();
//
//		System.out.println("请输入参数以空格分隔");
//		String param = "1111  33333";
//
//		BatJobExecute sje = new BatJobExecute();
//		BatJobInst sji = new BatJobInst();
//		sji.setFilePath(filePath);
//		sji.setRunParams(param);
//		sje.execute(sji);
//	}

	@Override
	public String getType() {
		return "bat";
	}

	@Override
	public void stop () {

	}

	@Override
	public void pause () {

	}
}
