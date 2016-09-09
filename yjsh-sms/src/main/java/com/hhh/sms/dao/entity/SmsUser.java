package com.hhh.sms.dao.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Set;


/**
 * The persistent class for the sms_user database table.
 * 
 */
@Entity
@Table(name="sms_user")
@NamedQuery(name="SmsUser.findAll", query="SELECT s FROM SmsUser s")
public class SmsUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="idGenerator") 
	@GenericGenerator(name="idGenerator", strategy="uuid")
	private String id;

	private String contactor;

	@Column(name="contactor_info")
	private String contactorInfo;

	@Column(name="customer_id")
	private String customerId;

	@Column(name="customer_name")
	private String customerName;

	@Column(name="msg_amount")
	private int msgAmount;//短信总量

	private String password;

	private String salt;

	private int type;//0:企业用户,1:管理用户

	private String username;
	
	private String dept;

	//bi-directional many-to-one association to SmsApplyRecord
	@OneToMany(mappedBy="smsUser")
	private Set<SmsApplyRecord> smsApplyRecords;

	//bi-directional many-to-one association to SmsConsumption
	@OneToMany(mappedBy="smsUser")
	private Set<SmsConsumption> smsConsumptions;

	//bi-directional many-to-one association to SmsSysAuthority
	@OneToMany(mappedBy="smsUser")
	private Set<SmsSysAuthority> smsSysAuthorities;
	
	@OneToMany(mappedBy="confirmer")
	private Set<SmsApplyRecord> smsSysAuthoritiesOfconfirmer;

	public SmsUser() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContactor() {
		return this.contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getContactorInfo() {
		return this.contactorInfo;
	}

	public void setContactorInfo(String contactorInfo) {
		this.contactorInfo = contactorInfo;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getMsgAmount() {
		return this.msgAmount;
	}

	public void setMsgAmount(int msgAmount) {
		this.msgAmount = msgAmount;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Set<SmsApplyRecord> getSmsApplyRecord() {
		return this.smsApplyRecords;
	}

	public void setSmsApplyRecords(Set<SmsApplyRecord> smsApplyRecord) {
		this.smsApplyRecords = smsApplyRecord;
	}

	public SmsApplyRecord addSmsApplyRecord(SmsApplyRecord smsApplyRecord) {
		getSmsApplyRecord().add(smsApplyRecord);
		smsApplyRecord.setSmsUser(this);

		return smsApplyRecord;
	}

	public SmsApplyRecord removeSmsApplyRecord(SmsApplyRecord smsApplyRecord) {
		getSmsApplyRecord().remove(smsApplyRecord);
		smsApplyRecord.setSmsUser(null);

		return smsApplyRecord;
	}

	public Set<SmsConsumption> getSmsConsumptions() {
		return this.smsConsumptions;
	}

	public void setSmsConsumptions(Set<SmsConsumption> smsConsumptions) {
		this.smsConsumptions = smsConsumptions;
	}

	public SmsConsumption addSmsConsumption(SmsConsumption smsConsumption) {
		getSmsConsumptions().add(smsConsumption);
		smsConsumption.setSmsUser(this);

		return smsConsumption;
	}

	public SmsConsumption removeSmsConsumption(SmsConsumption smsConsumption) {
		getSmsConsumptions().remove(smsConsumption);
		smsConsumption.setSmsUser(null);

		return smsConsumption;
	}

	public Set<SmsSysAuthority> getSmsSysAuthorities() {
		return this.smsSysAuthorities;
	}

	public void setSmsSysAuthorities(Set<SmsSysAuthority> smsSysAuthorities) {
		this.smsSysAuthorities = smsSysAuthorities;
	}

	public SmsSysAuthority addSmsSysAuthority(SmsSysAuthority smsSysAuthority) {
		getSmsSysAuthorities().add(smsSysAuthority);
		smsSysAuthority.setSmsUser(this);

		return smsSysAuthority;
	}

	public SmsSysAuthority removeSmsSysAuthority(SmsSysAuthority smsSysAuthority) {
		getSmsSysAuthorities().remove(smsSysAuthority);
		smsSysAuthority.setSmsUser(null);

		return smsSysAuthority;
	}

	public Set<SmsApplyRecord> getSmsSysAuthoritiesOfconfirmer() {
		return smsSysAuthoritiesOfconfirmer;
	}

	public void setSmsSysAuthoritiesOfconfirmer(Set<SmsApplyRecord> smsSysAuthoritiesOfconfirmer) {
		this.smsSysAuthoritiesOfconfirmer = smsSysAuthoritiesOfconfirmer;
	}
	
	public SmsApplyRecord addSmsApplyRecordOfconfirmer(SmsApplyRecord smsApplyRecord) {
		getSmsApplyRecord().add(smsApplyRecord);
		smsApplyRecord.setSmsUser(this);

		return smsApplyRecord;
	}

	public SmsApplyRecord removeSmsApplyRecordOfconfirmer(SmsApplyRecord SmsApplyRecord) {
		getSmsApplyRecord().remove(SmsApplyRecord);
		SmsApplyRecord.setSmsUser(null);

		return SmsApplyRecord;
	}
}