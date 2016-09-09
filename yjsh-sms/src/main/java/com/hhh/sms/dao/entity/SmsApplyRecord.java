package com.hhh.sms.dao.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the sms_apply_record database table.
 * 
 */
@Entity
@Table(name="sms_apply_record")
@NamedQuery(name="SmsApplyRecord.findAll", query="SELECT s FROM SmsApplyRecord s")
public class SmsApplyRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="idGenerator") 
	@GenericGenerator(name="idGenerator", strategy="uuid")
	private String id;

	@Column(name="apply_code")
	private String applyCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="apply_date")
	private Date applyDate;

	@Column(name="confirm_status")
	private int confirmStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="confirm_time")
	private Date confirmTime;
	
	//bi-directional many-to-one association to SmsPackage
	@ManyToOne
	@JoinColumn(name="confirmer_id")
	private SmsUser confirmer;

	//bi-directional many-to-one association to SmsPackage
	@ManyToOne
	@JoinColumn(name="package_id")
	private SmsPackage smsPackage;

	//bi-directional many-to-one association to SmsUser
	@ManyToOne
	@JoinColumn(name="user_id")
	private SmsUser smsUser;

	public SmsApplyRecord() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplyCode() {
		return this.applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	public Date getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public int getConfirmStatus() {
		return this.confirmStatus;
	}

	public void setConfirmStatus(int confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public Date getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public SmsPackage getSmsPackage() {
		return this.smsPackage;
	}

	public void setSmsPackage(SmsPackage smsPackage) {
		this.smsPackage = smsPackage;
	}

	public SmsUser getSmsUser() {
		return this.smsUser;
	}

	public void setSmsUser(SmsUser smsUser) {
		this.smsUser = smsUser;
	}

	public SmsUser getConfirmer() {
		return confirmer;
	}

	public void setConfirmer(SmsUser confirmer) {
		this.confirmer = confirmer;
	}

}