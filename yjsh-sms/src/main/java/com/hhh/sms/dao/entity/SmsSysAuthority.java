package com.hhh.sms.dao.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the sms_sys_authority database table.
 * 
 */
@Entity
@Table(name="sms_sys_authority")
@NamedQuery(name="SmsSysAuthority.findAll", query="SELECT s FROM SmsSysAuthority s")
public class SmsSysAuthority implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="idGenerator") 
	@GenericGenerator(name="idGenerator", strategy="uuid")
	private String id;

	@Column(name="menu_id")
	private int menuId;

	//bi-directional many-to-one association to SmsUser
	@ManyToOne
	@JoinColumn(name="user_id")
	private SmsUser smsUser;

	public SmsSysAuthority() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMenuId() {
		return this.menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public SmsUser getSmsUser() {
		return this.smsUser;
	}

	public void setSmsUser(SmsUser smsUser) {
		this.smsUser = smsUser;
	}

}