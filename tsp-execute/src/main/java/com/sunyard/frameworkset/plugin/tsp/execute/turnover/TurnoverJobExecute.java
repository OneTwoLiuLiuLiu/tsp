package com.sunyard.frameworkset.plugin.tsp.execute.turnover;

import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.TurnOverJobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import com.sunyard.frameworkset.plugin.tsp.spi.jobexecute.JobExecute;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Write class comments here
 * *
 * User: jl
 * Date: 2017/2/6 0006 上午 10:34
 * version $Id: TurnoverJobExecute.java, v 0.1 Exp $
 */
public class TurnoverJobExecute extends JobExecute {
    private static final String ERRORCODE = "JBET010";
    private static final Logger logger = LoggerFactory
            .getLogger(TurnoverJobExecute.class);

    @Override
    public ResultMessage execute(JobInst jobinst) {
        ResultMessage rm = new ResultMessage();
        Process pro = null;
        TurnOverJobInst turnoverJobInst = null;
        try {
            turnoverJobInst = (TurnOverJobInst) jobinst;
            logger.info("开始运行turnover作业;实例信息:【{}】", turnoverJobInst);
            String planintsid=jobinst.getPlanInstId();
            System.out.println("planinstid-------"+planintsid);
//			System.out.println("serverAysnService=="+serverAysnService);
//			String flag = serverAysnService.turnover(jobinst.getPlanInstId());
//			if (flag==null) {
//				TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
//				tspe.setErrorCode(ERRORCODE);
//				tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
//				rm.setSuccess(false);
//				rm.setResult(tspe);
//				logger.error("运行turnover作业出错:【{}】", tspe.getMessage());
//			} else {
            rm.setSuccess(true);
            logger.info("运行turnover作业结束;实例信息:【{}】", turnoverJobInst);
//			}
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
            TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
            tspe.setErrorCode(ERRORCODE);
            tspe.setSeriousness(TaskSchedulingPlatformException.ERROR);
            tspe.setBriefDescription(e.getMessage());
            rm.setSuccess(false);
            rm.setResult(tspe);
            logger.error("运行turnover作业出错:【{}】", tspe.getMessage());
        }
        return rm;
    }



    @Override
    public String getType() {

        return "turnover";
    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {

    }

    class MyThread4 extends Thread {
        public void run() {
//			TurnoverJobExecute storeProJobExecute = new TurnoverJobExecute();
//			StoreProJobInst storeProJobInst = new StoreProJobInst();
//			storeProJobInst.setDataBaseType("3");
//			storeProJobInst.setDataBaseName("jsd1410");
//			storeProJobInst.setDataBaseIp("localhost");
//			storeProJobInst.setDataBasePort("5432");
//			storeProJobInst.setDataBaseUser("postgres");
//			storeProJobInst.setDataBasePwd("123456");
//			storeProJobInst.setProcedureName("sp_users_select");
//			// storeProJobInst.setRunParams("110,zhouli,2898");
//			storeProJobExecute.execute(storeProJobInst);
        }
    }

}
