package com.hhh.sms.web.model;

import javax.persistence.Column;

public class SmsPackageBean {
	private String id;
	private int msgAmount;
	private String packageDes;
	private String packageName;
	private float price;
	private int status;
	public int getMsgAmount() {
		return msgAmount;
	}
	public void setMsgAmount(int msgAmount) {
		this.msgAmount = msgAmount;
	}
	public String getPackageDes() {
		return packageDes;
	}
	public void setPackageDes(String packageDes) {
		this.packageDes = packageDes;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
