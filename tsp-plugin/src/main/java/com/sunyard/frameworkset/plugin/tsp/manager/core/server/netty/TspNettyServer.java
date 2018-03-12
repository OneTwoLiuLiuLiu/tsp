package com.sunyard.frameworkset.plugin.tsp.manager.core.server.netty;

import com.sunyard.frameworkset.plugin.dictionary.DictionaryServer;
import com.sunyard.frameworkset.plugin.tsp.manager.biz.SystemParamsConfigManager;
import com.sunyard.frameworkset.plugin.tsp.manager.entity.JobServerConfig;
import com.sunyard.frameworkset.plugin.tsp.manager.service.JobServerConfigService;
import com.sunyard.frameworkset.plugin.tsp.spi.client.service.TspNettyClientService;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.config.JobServerConfigVO;
import com.sunyard.frameworkset.plugin.tsp.spi.entity.job.inst.JobInst;
import com.sunyard.frameworkset.plugin.tsp.spi.server.service.TspNettyServerService;
import com.sunyard.frameworkset.plugin.tsp.spi.spi.entity.ResultMessage;
import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.summercool.hsf.exception.HsfTimeoutException;
import org.summercool.hsf.netty.channel.HsfChannel;
import org.summercool.hsf.netty.listener.ChannelEventListenerAdapter;
import org.summercool.hsf.netty.listener.EventBehavior;
import org.summercool.hsf.netty.service.HsfAcceptor;
import org.summercool.hsf.netty.service.HsfAcceptorImpl;
import org.summercool.hsf.proxy.ServiceProxyFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Transactional
public class TspNettyServer  {

	private int port;

	private static final String GOUPNAME = "sunyardServer";

	private HsfAcceptor acceptor;

	@Autowired(required = false)
	private JobServerConfigService jobServerConfigService;

	@Autowired(required = false)
	private TspNettyServerService tspNettyServerService;

	@Resource
	private DictionaryServer dictionaryServer;

	@Autowired(required = false)
	private SystemParamsConfigManager systemParamsConfigManager;

	public int getPort () {
		return port;
	}

	public void setPort (int port) {
		this.port = port;
	}

	@PostConstruct
	public void init () {
		acceptor = new HsfAcceptorImpl ();
		acceptor.getListeners ().add (new AcceptorChannelEventHandler ());
		acceptor.registerService (TspNettyServerService.class.getSimpleName (),
				tspNettyServerService);
		acceptor.setGroupName (GOUPNAME);
		acceptor.bind (port);

	}

	/**
	 * 根据客户端名称获取客户端的异步服务
	 *
	 * @param clientName
	 * @return
	 */
	public TspNettyClientService getAsynTspClientServiceByClientName (
			String clientName) {
		TspNettyClientService clientService = ServiceProxyFactory
				.getBroadcastFactoryInstance (acceptor).wrapAsyncProxy (
						TspNettyClientService.class, clientName);
		return clientService;
	}

	/**
	 * 根据客户端名称获取客户端的同步服务
	 *
	 * @param clientName
	 * @return
	 */
	public TspNettyClientService getSynTspClientServiceByClientName (
			String clientName) {
		TspNettyClientService clientService = ServiceProxyFactory
				.getBroadcastFactoryInstance(acceptor).wrapSyncProxy(
						TspNettyClientService.class, clientName);
		return clientService;
	}

	public ResultMessage refreshClientResource (JobServerConfig jsc) {
		JobServerConfigVO jscvo = new JobServerConfigVO ();
		jscvo.setFreeCPU (jsc.getMinFreeCpu ());
		jscvo.setFreeMemory (jsc.getMinFreeMemory ());
		jscvo.setMaxThreadNum (jsc.getMaxRunNum ());
		jscvo.setMaxHistoryDay (jsc.getMaxHistoryDay ());
		String runjobType = jsc.getRunJobType ();
		if (StringUtils.isNotBlank (runjobType)) {
			Map<String, String> runjobClass = new HashMap<String, String> ();
			String[] types = runjobType.split (",");
			for (String string : types) {
				runjobClass.put (string, dictionaryServer.findByKey("tsp.job.type", string).getDicVal());
			}
			jscvo.setRunJobTypes (types);
			jscvo.setRunClasses (runjobClass);
		} else {
			jsc.setRunJobType (null);
		}
		ResultMessage rm = new ResultMessage ();
		try {
			rm = this.getSynTspClientServiceByClientName (jsc.getHostname ())
					.refresh (jscvo);
		} catch (RuntimeException e) {
			if (e instanceof HsfTimeoutException) {
				e.printStackTrace ();
			} else {
				throw e;
			}
		}
		return rm;
	}

	public ResultMessage stopJob (String hostName, String jobId) {
		ResultMessage rm = this.getSynTspClientServiceByClientName(hostName).stopJob(jobId);
		return rm;
	}

	public ResultMessage pauseJob (String hostName, String jobId) {
		ResultMessage rm = this.getSynTspClientServiceByClientName (hostName).pauseJob (jobId);
		return rm;
	}

	public ResultMessage continueJob (String hostName, String jobId) {
		ResultMessage rm = this.getSynTspClientServiceByClientName (hostName).continueJob(jobId);
		return rm;
	}


	public ResultMessage getResources (JobServerConfig jsc) {
		return this.getSynTspClientServiceByClientName (jsc.getHostname ())
				.getResources ();
	}


	public String getLogContent (String hostName, String planInstId, String jobId) {
		ResultMessage rm = this.getSynTspClientServiceByClientName (hostName).getLogContext(planInstId, jobId);
		return rm.getResult ().toString ();
	}

	public ResultMessage getFreeTheadNum (String clientName) {
		return this.getSynTspClientServiceByClientName(clientName)
				.getResources();
	}

	public void runJob (JobInst jobInst, String clientName) {
		this.getAsynTspClientServiceByClientName (clientName).createJob (jobInst);
	}

	public boolean JobServerIsAlive (String clientName) {
		return acceptor.getGroups().containsKey(clientName);
	}

	class AcceptorChannelEventHandler extends ChannelEventListenerAdapter {

		/**
		 * 当作业服务器与主服务器连接上后触发
		 */

		public EventBehavior groupCreated (ChannelHandlerContext ctx,
										   HsfChannel channel, String groupName) {
			jobServerConfigService.connectJobServer (groupName);
			return super.groupCreated (ctx, channel, groupName);
		}

		/**
		 * 当作业服务器与主服务器断开后触发,如果作业服务器与主服务器的心跳超时也会造成作业服务器与主服务器的断开然后触发该事件
		 */

		public EventBehavior groupRemoved (ChannelHandlerContext ctx,
										   HsfChannel channel, String groupName) {
			jobServerConfigService.disconnectServer (groupName);
			return super.groupRemoved (ctx, channel, groupName);
		}
	}


}
