package com.hhh.sms.dao.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the sms_sys_menu database table.
 * 
 */
@Entity
@Table(name="sms_sys_menu")
@NamedQuery(name="SmsSysMenu.findAll", query="SELECT s FROM SmsSysMenu s")
public class SmsSysMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="idGenerator") 
	@GenericGenerator(name="idGenerator", strategy="uuid")
	private String id;

	@Column(name="menu_name")
	private String menuName;

	public SmsSysMenu() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

}