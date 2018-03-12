package com.sunyard.frameworkset.plugin.tsp.spi.exception;

/**
 * 自定义调度错误
 * @author whj
 *
 */
public class TaskSchedulingPlatformException extends RuntimeException{
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3805880592799827086L;

	/**
	 * 致命
	 */
	public static final String FATAL="fatal";
	
	/**
	 * 错误
	 */
	public static final String ERROR="error";
	
	/**
	 * 警告
	 */
	public static final String WARNING="warning";
	
	/**
	 * 忽略
	 */
	public static final String IGNORABLE="Ignorable";

	/**
	 * 错误码
	 */
	private String errorCode;
	
	/**
	 * 严重程度
	 */
	private String seriousness;
	
	/**
	 * 简要描述
	 */
	private String briefDescription;
	
	/**
	 * 诊断信息
	 */
	private String diagnosticInference;

	public TaskSchedulingPlatformException() {
		super();
		this.initCause(null);
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getSeriousness() {
		return seriousness;
	}

	public void setSeriousness(String seriousness) {
		this.seriousness = seriousness;
	}

	public String getBriefDescription() {
		return briefDescription;
	}

	public void setBriefDescription(String briefDescription) {
		this.briefDescription = briefDescription;
	}

	public String getDiagnosticInference() {
		return diagnosticInference;
	}

	public void setDiagnosticInference(String diagnosticInference) {
		this.diagnosticInference = diagnosticInference;
	}
	
	public String getErrorMessage(){
		String message = "错误代码为:"+this.errorCode+";严重程度为:"+this.seriousness+";错误信息为:"+this.briefDescription+";";
		if(this.diagnosticInference !=null){
			message = message+this.diagnosticInference;
		}
		return message;
	}
	
	@Override
	public String getMessage() {
		return this.getErrorMessage();
	}
	
}
