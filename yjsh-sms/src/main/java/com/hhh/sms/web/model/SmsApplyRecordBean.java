package com.hhh.sms.web.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hhh.sms.dao.entity.SmsPackage;
import com.hhh.sms.dao.entity.SmsUser;

public class SmsApplyRecordBean {
	private String id;
	private String userId;
	private String customerId;
	private String customerName;
	private String applyCode;
	private String applyDate;
	private int confirmStatus;
	private String confirmTime;
	private String confirmerId;
	private String startTime;
	private String endTime;
	private float price;
	private int msgAmount;
	private String userIdOfDetail;//用户管理的消费记录明细用到
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
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
	public String getApplyCode() {
		return applyCode;
	}
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		if(applyDate!=null){
			this.applyDate = sdf.format(applyDate);
		}else{
			this.applyDate = null;
		}
	}
	public int getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(int confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public String getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		if(confirmTime!=null){
			this.confirmTime = sdf.format(confirmTime);
		}else{
			this.confirmTime = null;
		}
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getMsgAmount() {
		return msgAmount;
	}
	public void setMsgAmount(int msgAmount) {
		this.msgAmount = msgAmount;
	}
	public String getConfirmerId() {
		return confirmerId;
	}
	public void setConfirmerId(String confirmerId) {
		this.confirmerId = confirmerId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserIdOfDetail() {
		return userIdOfDetail;
	}
	public void setUserIdOfDetail(String userIdOfDetail) {
		this.userIdOfDetail = userIdOfDetail;
	}
}
