package com.sunyard.frameworkset.plugin.tsp.spi.message.service;

/**
 * 短信接口
 * @author whj
 *
 */
public interface MessageService {

	/**
	 * 发送短信
	 * @param phones 需要发送的手机号码
	 * @param content 短信内容
	 */
	public void sendMessage(String[] phones, String content);
	
}
