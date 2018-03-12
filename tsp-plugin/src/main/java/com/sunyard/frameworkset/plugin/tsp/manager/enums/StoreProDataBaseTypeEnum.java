package com.sunyard.frameworkset.plugin.tsp.manager.enums;

import com.sunyard.frameworkset.util.enums.EnumAware;

/**
 * Created by Misaki on 2017/1/16.
 */
public enum StoreProDataBaseTypeEnum implements EnumAware {
	ORACLE ("0", "oracle"),
	DB2 ("1", "db2"),
	mysql ("2", "mysql"),
	POSTGRESQL ("3", "postgresql");

	StoreProDataBaseTypeEnum (String code, String name) {
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
