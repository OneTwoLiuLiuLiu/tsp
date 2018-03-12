package com.sunyard.frameworkset.plugin.tsp.manager.core.service.mail;

/**
 * 邮件服务接口
 * @author whj
 *
 */
public interface MailService {

	/**
	 * 发送邮件
	 * @param toAddress 需要发送的邮件地址
	 * @param content 邮件的内容
	 * @param subject 主题
	 */
	public void sendMail(String[] toAddress, String content, String subject);
}
