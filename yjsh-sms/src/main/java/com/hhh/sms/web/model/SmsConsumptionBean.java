package com.hhh.sms.web.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsConsumptionBean {
	private String id;
	private String userId;
	private String customerId;
	private String customerName;
	private String telephone;//手机号码
	private String sendTime;
	private int status;//0:未发送,1:已发送
	private String content;
	private String startTime;
	private String endTime;
	private String userIdOfDetail;//用于短信统计的消费记录明细
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		if(sendTime!=null){
			this.sendTime = sdf.format(sendTime);
		}else{
			this.sendTime = null;
		}
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getUserIdOfDetail() {
		return userIdOfDetail;
	}
	public void setUserIdOfDetail(String userIdOfDetail) {
		this.userIdOfDetail = userIdOfDetail;
	}
}
