package com.hhh.sms.web.model;

public class SmsUserBean {
	private String id;
	private String contactor;
	private String contactorInfo;
	private String customerId;
	private String customerName;
	private int msgAmount;
	private int type;// 0:企业用户,1:管理用户
	private String username;
	private String password;
	private String salt;
	private String dept;

	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getContactorInfo() {
		return contactorInfo;
	}

	public void setContactorInfo(String contactorInfo) {
		this.contactorInfo = contactorInfo;
	}

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

	public int getMsgAmount() {
		return msgAmount;
	}

	public void setMsgAmount(int msgAmount) {
		this.msgAmount = msgAmount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
}
