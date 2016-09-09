package com.hhh.sms.dao.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Set;


/**
 * The persistent class for the sms_package database table.
 * 
 */
@Entity
@Table(name="sms_package")
@NamedQuery(name="SmsPackage.findAll", query="SELECT s FROM SmsPackage s")
public class SmsPackage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="idGenerator") 
	@GenericGenerator(name="idGenerator", strategy="uuid")
	private String id;

	@Column(name="msg_amount")
	private int msgAmount;

	@Column(name="package_des")
	private String packageDes;

	@Column(name="package_name")
	private String packageName;

	private float price;
	
	private int status=1;//0:无效,1:有效

	//bi-directional many-to-one association to SmsApplyRecord
	@OneToMany(mappedBy="smsPackage")
	private Set<SmsApplyRecord> smsApplyRecord;

	public SmsPackage() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMsgAmount() {
		return this.msgAmount;
	}

	public void setMsgAmount(int msgAmount) {
		this.msgAmount = msgAmount;
	}

	public String getPackageDes() {
		return this.packageDes;
	}

	public void setPackageDes(String packageDes) {
		this.packageDes = packageDes;
	}

	public String getPackageName() {
		return this.packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public SmsApplyRecord addSmsApplyRecord(SmsApplyRecord smsApplyRecord) {
		getSmsApplyRecord().add(smsApplyRecord);
		smsApplyRecord.setSmsPackage(this);

		return smsApplyRecord;
	}

	public SmsApplyRecord removeSmsApplyRecord(SmsApplyRecord smsApplyRecord) {
		getSmsApplyRecord().remove(smsApplyRecord);
		smsApplyRecord.setSmsPackage(null);

		return smsApplyRecord;
	}

	public Set<SmsApplyRecord> getSmsApplyRecord() {
		return smsApplyRecord;
	}

	public void setSmsApplyRecord(Set<SmsApplyRecord> smsApplyRecord) {
		this.smsApplyRecord = smsApplyRecord;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}