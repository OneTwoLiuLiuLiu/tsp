package com.sunyard.frameworkset.plugin.tsp.manager.core.service.mail.impl;


import com.sunyard.frameworkset.plugin.tsp.manager.core.service.mail.MailService;
import com.sunyard.frameworkset.plugin.tsp.spi.exception.TaskSchedulingPlatformException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

public class MailServiceImpl implements MailService {

	private static final String ERRORCODE = "MAIL001";

	@Autowired(required = false)
	private JavaMailSenderImpl mailSender;

	private String fromAddress;

	
	
	public void sendMail(String[] toAddress, String content, String subject) {
		MimeMessage msg = mailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(msg, true,"UTF-8");
			messageHelper.setFrom(fromAddress);
			messageHelper.setTo(toAddress);
			messageHelper.setText(content, true);
			messageHelper.setSubject(subject);
			mailSender.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
			TaskSchedulingPlatformException tspe = new TaskSchedulingPlatformException();
			tspe.setErrorCode(tspe.ERROR);
			tspe.setErrorCode(ERRORCODE);
			tspe.setBriefDescription("发送邮件失败:" + e.getMessage());
			throw tspe;
		}
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	
	
}
