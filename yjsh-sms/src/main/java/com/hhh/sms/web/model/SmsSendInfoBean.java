package com.hhh.sms.web.model;

public class SmsSendInfoBean {
	private String userId;
	private String content;
	private String userNumbers;//一个或者多个号码，多个号码之间用逗号隔开
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserNumbers() {
		return userNumbers;
	}
	public void setUserNumbers(String userNumbers) {
		this.userNumbers = userNumbers;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
