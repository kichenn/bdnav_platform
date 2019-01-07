package com.bdxh.common.base.exception;

/**
 * @description: 配置异常
 * @author: xuyuan
 * @create: 2018-12-17 14:25
 **/
public class ConfigException extends RuntimeException {

	private static final long serialVersionUID = 6480772904575978373L;

	public ConfigException(String message) {
		super(message);
	}

	public ConfigException() {
	}
}
