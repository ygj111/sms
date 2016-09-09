package com.hhh.sms.web.model;

/**
 * 调用短信接口后的返回内容
 * @author 3hygj
 *
 */
public class SmsResultBean {
	private Integer result;
	private String other;
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
}
