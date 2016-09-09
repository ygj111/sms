package com.hhh.sms.web.model;

/**
 * 短信发送的post参数
 * @author 3hygj
 *
 */
public class SmsBean {
	private String MessageContent;
	private String UserNumber;
	private String SerialNumber;
	private String ScheduleTime;
	private String ExtendAccessNum;
	private String f;
	public String getMessageContent() {
		return MessageContent;
	}
	public void setMessageContent(String messageContent) {
		MessageContent = messageContent;
	}
	public String getUserNumber() {
		return UserNumber;
	}
	public void setUserNumber(String userNumber) {
		UserNumber = userNumber;
	}
	public String getSerialNumber() {
		return SerialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		SerialNumber = serialNumber;
	}
	public String getScheduleTime() {
		return ScheduleTime;
	}
	public void setScheduleTime(String scheduleTime) {
		ScheduleTime = scheduleTime;
	}
	public String getF() {
		return f;
	}
	public void setF(String f) {
		this.f = f;
	}
	public String getExtendAccessNum() {
		return ExtendAccessNum;
	}
	public void setExtendAccessNum(String extendAccessNum) {
		ExtendAccessNum = extendAccessNum;
	}
}
