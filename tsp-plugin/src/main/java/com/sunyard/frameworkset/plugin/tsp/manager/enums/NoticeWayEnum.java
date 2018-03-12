package com.sunyard.frameworkset.plugin.tsp.manager.enums;

import com.sunyard.frameworkset.util.enums.EnumAware;

/**
 * Created by Misaki on 2017/1/3.
 */
public enum NoticeWayEnum implements EnumAware {
	Message ("1", "短信"),
	MAIL ("2", "邮件"),
	MAIL_AND_MESSAGE ("3", "邮件和短信"),;

	NoticeWayEnum (String code, String name) {
		this.code = code;
		this.name = name;
	}

	private String code;
	private String name;

	@Override
	public String getCode () {
		return this.code;
	}

	@Override
	public String getName () {
		return this.name;
	}

	@Override
	public String getSimpleName () {
		return this.name;
	}
}
