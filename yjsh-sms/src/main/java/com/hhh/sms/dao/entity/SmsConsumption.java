package com.hhh.sms.dao.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the sms_consumption database table.
 * 
 */
@Entity
@Table(name="sms_consumption")
@NamedQuery(name="SmsConsumption.findAll", query="SELECT s FROM SmsConsumption s")
public class SmsConsumption implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="idGenerator") 
	@GenericGenerator(name="idGenerator", strategy="uuid")
	private String id;

	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="send_time")
	private Date sendTime;

	private int status;//0:未发送,1:已发送

	private String telephone;

	//bi-directional many-to-one association to SmsUser
	@ManyToOne
	@JoinColumn(name="user_id")
	private SmsUser smsUser;

	public SmsConsumption() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public SmsUser getSmsUser() {
		return this.smsUser;
	}

	public void setSmsUser(SmsUser smsUser) {
		this.smsUser = smsUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}