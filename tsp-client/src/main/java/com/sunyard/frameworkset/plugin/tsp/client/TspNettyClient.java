package com.sunyard.frameworkset.plugin.tsp.client;

import com.sunyard.frameworkset.plugin.tsp.client.service.TspNettyClientServiceImpl;
import com.sunyard.frameworkset.plugin.tsp.client.utils.ClassUtils;
import com.sunyard.frameworkset.plugin.tsp.client.utils.FileUtil;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.config.JobServerConfigVO;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import com.sunyard.frameworkset.plugin.tsp.spi.jobexecute.JobExecute;
import com.sunyard.frameworkset.plugin.tsp.spi.jobexecute.JobExecutetorFactory;
import com.sunyard.frameworkset.plugin.tsp.spi.server.service.TspNettyServerService;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.JobServerResource;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import org.apache.commons.lang.time.DateUtils;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.summercool.hsf.netty.service.HsfConnectorImpl;
import org.summercool.hsf.proxy.ServiceProxyFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TspNettyClient {

	private static final String ERRORCODE = "PNTC002";

	private static TspNettyClient client;

	private Logger logger = LoggerFactory.getLogger (TspNettyClient.class);

	private static final Integer DEFAULTMAXRUNNUM = 50;

	private static final Integer DEFAULTMINFREEMEMORY = 10;

	private static final Integer DEFAULTMINFREECPU = 20;

	private static final Integer DEFAULTMAXHISTORYDAY = 5;

	private static final Map<Thread, Condition> notifyRelationship = new ConcurrentHashMap<Thread, Condition> ();

	private static final ReentrantLock locker = new ReentrantLock (false);

	private Map<String, JobExecute> runningTaskMap = new ConcurrentHashMap<String, JobExecute> ();

	private Map<String, String> runningThreadMap = new ConcurrentHashMap<String, String> ();
	/**
	 * 最大运行线程数
	 */
	private Integer maxRunNum = DEFAULTMAXRUNNUM;

	/**
	 * 线程池
	 */
	private ThreadPoolExecutor threadPool;


	/**
	 * 空闲内存
	 */
	private Integer minfreeMemory = DEFAULTMINFREEMEMORY;

	/**
	 * 空闲CPU
	 */
	private Integer minfreeCPU = DEFAULTMINFREECPU;

	/**
	 * netty客户端
	 */
	private HsfConnectorImpl hsfClient;

	private TspNettyServerService serverAysnService;

	private TspNettyServerService serverSysnService;

	private Sigar sigar;

	private String logpath;

	private int maxHistoryDay = DEFAULTMAXHISTORYDAY;

	private TspNettyClient() {

	}

	public static TspNettyClient getInst () {
		if (client == null) {
			client = new TspNettyClient ();
		}
		return client;
	}

	public void init () throws Exception {
		logger.info ("================开始初始化=================");
		Properties p = new Properties ();

		logger.info ("================加载配置文件=================");
		File file = new File ("config.properties");
		if (!file.exists ()) {
			throw new FileNotFoundException ("找不到config.properties配置文件,路劲为:" + file.getAbsolutePath ());
		}
		p.load (new FileInputStream (file));
		logger.info ("================加载配置文件结束=================");


		File sigarFile = new File ("sigar-bin");
		if (!sigarFile.exists ()) {
			throw new FileNotFoundException ("缺少sigar-bin文件夹,路劲为:" + sigarFile.getAbsolutePath ());
		}
		String sigarpath = sigarFile.getAbsolutePath ();
		String libs = System.getProperty ("java.library.path");
		libs = libs + File.pathSeparatorChar + sigarpath;
		System.setProperty ("java.library.path", libs);
		sigar = new Sigar ();

		logger.info ("================加载执行器=================");
		Set<Class<?>> jobExecuters = ClassUtils.getClasses("com.sunyard.frameworkset.plugin.tsp", JobExecute.class);
		for (Class<?> class1 : jobExecuters) {
			JobExecute execute = (JobExecute) class1.newInstance ();
			JobExecutetorFactory.register(execute.getType(), execute);
		}
		logger.info (JobExecutetorFactory.executors.toString ());
		logger.info ("================加载执行器结束===============");


		logpath = p.getProperty ("logpath");
		String host = p.getProperty ("host");
		Integer port = Integer.valueOf (p.getProperty ("port"));
		String groupName = p.getProperty ("clientname");
		hsfClient = new HsfConnectorImpl ();
		hsfClient.setGroupName (groupName);
		hsfClient.registerService (new TspNettyClientServiceImpl());
		serverAysnService = ServiceProxyFactory.getRoundFactoryInstance (hsfClient).wrapAsyncProxy (TspNettyServerService.class);
		serverSysnService = ServiceProxyFactory.getRoundFactoryInstance (hsfClient).wrapSyncProxy (TspNettyServerService.class);
		threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool (1);
		threadPool.setMaximumPoolSize (maxRunNum);
		hsfClient.connect (new InetSocketAddress (host, port));
		if (hsfClient.isAlived ()) {
			logger.info ("================连接服务器成功=================");
		} else {
			logger.warn ("连接服务器失败:服务器地址:{};端口:{}", host, port);
		}

		logger.info ("启动日志删除监控,日志保留天数为:{},路径为:{}", maxHistoryDay, logpath);
		LogMonitoring log = new LogMonitoring ();
		log.start ();
		logger.info ("启动日志删除监控结束");
		logger.info ("================初始化结束=================");

	}


	public Integer getMaxRunNum () {
		return maxRunNum;
	}

	public void setMaxRunNum (Integer maxRunNum) {
		threadPool.setCorePoolSize (maxRunNum);
		threadPool.setMaximumPoolSize (maxRunNum);
		this.maxRunNum = maxRunNum;
	}

	public Integer getMinfreeMemory () {
		return minfreeMemory;
	}

	public void setMinfreeMemory (Integer minfreeMemory) {
		this.minfreeMemory = minfreeMemory;
	}

	public Integer getMinfreeCPU () {
		return minfreeCPU;
	}

	public void setMinfreeCPU (Integer minfreeCPU) {
		this.minfreeCPU = minfreeCPU;
	}

	public int getMaxHistoryDay () {
		return maxHistoryDay;
	}

	public void setMaxHistoryDay (int maxHistoryDay) {
		this.maxHistoryDay = maxHistoryDay;
	}

	/**
	 * 获取当前空闲的线程数
	 *
	 * @return
	 */
	public int getFreeThreadNum () {
		return this.maxRunNum - threadPool.getActiveCount ();
	}

	public void runJob (JobInst jobInst) {
		jobInst.setRunHostName (this.hsfClient.getGroupName ());
		threadPool.execute (new MyRunnable (jobInst));
	}

	public void logtest () {
		logpath = "logback/log/job";
		LogMonitoring log = new LogMonitoring ();
		log.start ();
		client.getResources ();
	}

	class LogMonitoring extends Thread {
		@Override
		public void run () {
			while (true) {
				try {
					File logdir = new File (logpath);
					deleteLog (logdir);
					Thread.sleep (1600000);
				} catch (Exception e) {
					e.printStackTrace ();
				}

			}
		}

		private void deleteLog (File parentFile) {
			File[] files = parentFile.listFiles ();
			for (File file : files) {
				if (file.isDirectory ()) {
					deleteLog (file);
				} else {
					Long lasttime = file.lastModified ();
					Date lastDate = new Date (lasttime);
					Date dat = DateUtils.addDays(lastDate, maxHistoryDay);
					Date now = new Date ();
					if (dat.getTime () < now.getTime ()) {
						file.delete ();
					}
				}
			}
		}

	}

	class MyRunnable implements Runnable {
		private JobInst jobInst;
		private Logger log = LoggerFactory.getLogger (MyRunnable.class);

		private MyRunnable (JobInst jobInst) {
			this.jobInst = jobInst;
		}

		public void run () {
			final Thread t = Thread.currentThread ();
			final ThreadLocal<Boolean> isWait = new ThreadLocal<Boolean> ();
			notifyRelationship.put (t, locker.newCondition ());
//			logger.info ("============线程{}锁住===========270", t.getName ());
			locker.lock ();
			try {
				isWait.set (false);
				JobExecute je = null;
				try {
					je = JobExecutetorFactory.getJobExecute (jobInst
							.getType ()).getClass ().newInstance ();
				} catch (InstantiationException e) {
					e.printStackTrace ();
				} catch (IllegalAccessException e) {
					e.printStackTrace ();
				}
				String runJobId = serverSysnService.checkRunning (jobInst);
				runningTaskMap.put (runJobId, je);
//				logger.info ("============线程{}解锁锁===========285", t.getName ());
				locker.unlock ();
				MDC.remove ("jobid");
				MDC.put ("jobid", jobInst.getPlanInstId () + "_" + jobInst.getJobId ());
				Integer retrycnt = jobInst.getRetryCnt ();
				Integer retrysec = jobInst.getRetrySec ();
				ResultMessage rm = new ResultMessage ();
				for (int i = 0; i < retrycnt + 1; i++) {
					try {
						if (je.getIsPause ()) {
							je.setIsPause (false);
							log.info ("继续执行第" + (i + 1) + "次作业,作业ID:【{}】;计划实例ID:【{}】",
									jobInst.getJobId (), jobInst.getPlanInstId ());
						} else {
							log.info ("开始执行第" + (i + 1) + "次作业,作业ID:【{}】;计划实例ID:【{}】",
									jobInst.getJobId (), jobInst.getPlanInstId ());
						}
						rm = je.execute (jobInst);
						log.info ("执行作业结束,作业ID:【{}】;计划实例ID:【{}】",
								jobInst.getJobId (), jobInst.getPlanInstId ());
						if (je.getIsPause ()) {
//							logger.info ("============线程{}锁住===========306", t.getName ());
							locker.lock ();
							runningThreadMap.put (runJobId, t.getName ());
							isWait.set (true);
//							logger.info ("============线程{}解锁===========310", t.getName ());
							locker.unlock ();
						}
						while (isWait.get ()) {
							try {
//								logger.info ("============线程{}锁住===========315", t.getName ());
								locker.lock ();
								log.info ("线程暂停：【{}】", t.getName ());
								final Condition c = notifyRelationship.get (t);
								c.await ();
								isWait.set (false);
//								logger.info ("============线程{}解锁===========321", t.getName ());
								locker.unlock ();
							} catch (InterruptedException ex) {
								System.err.println (ex.getMessage ());
							}
						}
						if (je.getIsPause ()) {
							i--;
							continue;
						}
						if (rm.getSuccess ()) {// 如果运行成功跳出循环,如果运行失败则抛出异常
							break;
						} else {
							Object result = rm.getResult ();
							if (result instanceof TaskSchedulingPlatformException) {
								throw (TaskSchedulingPlatformException) result;
							} else {
								throw new RuntimeException ("执行第" + (i + 1) + "失败");
							}
						}
					} catch (Exception e) {
						e.printStackTrace ();
						rm.setSuccess (false);
						rm.setResult (e.getMessage ());
						log.error ("作业服务器:" + hsfClient.getGroupName () + "执行作业出错;作业ID:【{}】;计划实例ID:【{}】;错误信息:{}", new String[]{jobInst.getJobId (), jobInst.getPlanInstId (), e.getMessage ()});
						try {
							if (i < retrycnt) {
								log.info ("作业" + retrysec + "秒后重试");
								Thread.sleep (retrysec * 1000);
								log.info ("开始第" + (i + 1) + "次重试");
							}
						} catch (InterruptedException e1) {
							e1.printStackTrace ();
						}
					}
				}
				rm.setRunjobId (runJobId);
				serverAysnService.dealWithJobResult (rm);
//				logger.info ("============线程{}锁住===========359", t.getName ());
				locker.lock ();
				runningTaskMap.remove (jobInst.getJobId ());
				notifyRelationship.remove (t);
			} finally {
//				logger.info ("============线程{}解锁===========364", t.getName ());
				locker.unlock ();
			}
		}
	}

	public ResultMessage refresh (JobServerConfigVO jobServerConfig) {
		logger.info ("刷新资源:{}", jobServerConfig.toString ());
		ResultMessage rm = new ResultMessage ();
		String[] jobType = jobServerConfig.getRunJobTypes ();
		String type = "";
		try {
			if (jobType != null) {
				for (String string : jobType) {
					JobExecute a = JobExecutetorFactory.getJobExecute (string);
					if (a == null) {
						throw new ClassNotFoundException ("客户端没有类型为:" + string + "作业执行器,请确定是否安装了该类型的jar包");
					}
				}
			}
			if (jobServerConfig.getMaxThreadNum () == null || jobServerConfig.getMaxThreadNum () == 0) {
				this.setMaxRunNum (DEFAULTMAXRUNNUM);
			} else {
				int threadNum = jobServerConfig.getMaxThreadNum ();
				this.setMaxRunNum (threadNum);
			}

			if (jobServerConfig.getFreeCPU () == null || jobServerConfig.getFreeCPU () == 0) {
				this.setMinfreeCPU (DEFAULTMINFREECPU);
			} else {
				this.setMinfreeCPU (jobServerConfig.getFreeCPU ());
			}

			if (jobServerConfig.getFreeMemory () == null || jobServerConfig.getFreeMemory () == 0) {
				this.setMinfreeMemory (DEFAULTMINFREEMEMORY);
			} else {
				this.setMinfreeMemory (jobServerConfig.getFreeMemory ());
			}

			if (jobServerConfig.getMaxHistoryDay () == null || jobServerConfig.getMaxHistoryDay () == 0) {
				this.setMaxHistoryDay (DEFAULTMAXHISTORYDAY);
			} else {
				this.setMaxHistoryDay (jobServerConfig.getMaxHistoryDay ());
			}

		} catch (Exception e) {
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException ();
			tspe.setErrorCode (ERRORCODE);
			tspe.setSeriousness (TaskSchedulingPlatformException.FATAL);
			tspe.setBriefDescription (e.getMessage ());
			tspe.setDiagnosticInference ("请检查该作业服务器:" + hsfClient.getGroupName () + "是否安装了执行" + type + "作业的执行器");
			rm.setResult (tspe.getMessage ());
			rm.setSuccess (false);
			e.printStackTrace ();
			logger.error ("刷新资源出错:{}", tspe.getMessage ());
		}
		logger.info ("刷新资源结束");
		return rm;
	}

	public ResultMessage getResources () {
		ResultMessage rm = new ResultMessage ();
		boolean flag = true;
		try {
			Mem mem = sigar.getMem ();
			Long freeMem = mem.getFree ();
			Double freeMemoryD = (double) (this.minfreeMemory.doubleValue () / 100);
			CpuPerc cpuperc = sigar.getCpuPerc ();
			double idle = cpuperc.getIdle ();
			Double freeCPUD = (double) (this.minfreeCPU.doubleValue () / 100);
			Long totalMem = mem.getTotal ();
			Double nowfreeMem = freeMem / Double.valueOf (totalMem);
			if (nowfreeMem < freeMemoryD) {
				flag = false;
				logger.warn ("没有足够的内存;设置的最小剩余内存为:【{}%】;当前剩余内存为:【{}%】",
						freeMemoryD * 100, nowfreeMem * 100);
				rm.setSuccess (flag);
				rm.setResult ("没有足够的内存可以运行");
				return rm;
			}
			if (idle < freeCPUD) {
				flag = false;
				logger.warn ("没有足够的空闲CPU资源;设置的CPU最小空闲为:【{}%】;当前空闲为:【{}%】", freeCPUD * 100, idle * 100);
				rm.setSuccess (flag);
				rm.setResult ("没有足够的CPU资源");
				return rm;
			}
			JobServerResource jsr = new JobServerResource ();
			jsr.setEnoughResource (flag);
			jsr.setFreeThreadNum (this.getFreeThreadNum ());
			rm.setSuccess (true);
			rm.setResult (jsr);
		} catch (SigarException e) {
			e.printStackTrace ();
			rm.setSuccess (false);
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException ();
			tspe.setErrorCode ("PNTC002");
			tspe.setBriefDescription ("获取资源出错,出错信息为:" + e.getMessage ());
			tspe.setSeriousness (TaskSchedulingPlatformException.ERROR);
			rm.setResult (tspe.getMessage ());
		}
		return rm;
	}

	public ResultMessage getLogContext (String planInst, String jobId) {
		ResultMessage rm = new ResultMessage ();
		try {
			String filePath = logpath + File.separator + planInst + "_" + jobId + ".log";
			File file = new File (filePath);
			if (file.exists ()) {
				rm.setResult (FileUtil.parseFileToString(file));
			} else {
				rm.setResult ("日志文件不存在,或许已经被自动删除。请检查文件路径是否正确,文件路径为:" + file.getAbsolutePath ());
			}
		} catch (Exception e) {
			e.printStackTrace ();
			rm.setResult ("读取日志文件出错,错误信息:" + e.getMessage ());
		}
		return rm;
	}

	public ResultMessage stopJob (String jobId) {
		ResultMessage rm = new ResultMessage ();
		try {
//			logger.info ("============线程{}锁住===========488", Thread.currentThread ().getName ());
			locker.lock ();
			JobExecute je = runningTaskMap.get (jobId);
//			logger.info ("============线程{}解锁===========491", Thread.currentThread ().getName ());
			locker.unlock ();
			if (je != null) {
				je.stop ();
				logger.info ("作业已停止:{}", jobId);
				rm.setSuccess (true);
				rm.setResult ("作业已停止");
			} else {
				logger.info ("作业不存在:{}", jobId);
				rm.setSuccess (false);
				rm.setResult ("作业不存在");
			}
		} catch (Exception e) {
			e.printStackTrace ();
			rm.setSuccess (false);
			rm.setResult ("作业停止失败:" + e.getMessage ());
		}
		return rm;
	}

	public ResultMessage pauseJob (String jobId) {
		ResultMessage rm = new ResultMessage ();
		try {
//			logger.info ("============线程{}锁住===========514", Thread.currentThread ().getName ());
			locker.lock ();
			JobExecute je = runningTaskMap.get (jobId);
//			logger.info ("============线程{}解锁===========517", Thread.currentThread ().getName ());
			locker.unlock ();
			if (je != null) {
				je.pause ();
				logger.info ("作业已暂停:{}", jobId);
				rm.setSuccess (true);
				rm.setResult ("作业已暂停");
			} else {
				logger.info ("作业不存在:{}", jobId);
				rm.setSuccess (false);
				rm.setResult ("作业不存在");
			}
		} catch (Exception e) {
			e.printStackTrace ();
			rm.setSuccess (false);
			rm.setResult ("作业暂停失败:" + e.getMessage ());
		}
		return rm;
	}

	public ResultMessage continueJob (String jobId) {
		ResultMessage rm = new ResultMessage ();
		try {
			final Set<Map.Entry<Thread, Condition>> entrySet = notifyRelationship
					.entrySet ();
			for (final Map.Entry<Thread, Condition> entry : entrySet) {
				final Thread t = entry.getKey ();
				final Condition c = entry.getValue ();
//				logger.info ("============线程{}锁住===========545", Thread.currentThread ().getName ());
				locker.lock ();
				String threadName = runningThreadMap.get (jobId);
				try {
					if (threadName != null) {
						if (threadName.equals (t.getName ())) {
							logger.info ("线程继续运行:【{}】", t.getName ());
							c.signal ();
							runningThreadMap.remove (jobId);
							logger.info ("作业已继续:{}", jobId);
							rm.setSuccess (true);
							rm.setResult ("作业已继续");
							return rm;
						}
					}
				} finally {
//					logger.info ("============线程{}解锁===========554", Thread.currentThread ().getName ());
					locker.unlock ();
				}
			}
			logger.info ("作业不存在:{}", jobId);
			rm.setSuccess (false);
			rm.setResult ("作业不存在");
		} catch (Exception e) {
			e.printStackTrace ();
			rm.setSuccess (false);
			rm.setResult ("作业继续失败:" + e.getMessage ());
		}
		return rm;
	}
}
