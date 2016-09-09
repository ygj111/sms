package com.hhh.sms.web.model;

/**
 * 短信统计bean
 * @author 3hygj
 *
 */
public class SmsCountBean {
	private String userId;
	private String customerId;
	private String customerName;
	private Integer msgAmount;//短信总量
	private Integer sendedAmount;//短信已发送量
	private Integer msgRemain;//短信剩余量
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getMsgAmount() {
		return msgAmount;
	}
	public void setMsgAmount(Integer msgAmount) {
		this.msgAmount = msgAmount;
	}
	public Integer getSendedAmount() {
		return sendedAmount;
	}
	public void setSendedAmount(Integer sendedAmount) {
		this.sendedAmount = sendedAmount;
	}
	public Integer getMsgRemain() {
		return msgRemain;
	}
	public void setMsgRemain(Integer msgRemain) {
		this.msgRemain = msgRemain;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
