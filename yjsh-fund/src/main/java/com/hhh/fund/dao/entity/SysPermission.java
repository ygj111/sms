package com.hhh.fund.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sys_permission database table.
 * 
 */
@Entity
@Table(name="sys_permission")
@NamedQuery(name="SysPermission.findAll", query="SELECT s FROM SysPermission s")
public class SysPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String desc;

	private String obj;

	private String permission;

	private int status;

	//bi-directional many-to-many association to SysRole
	@ManyToMany(mappedBy="sysPermissions")
	private List<SysRole> sysRoles;

	public SysPermission() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getObj() {
		return this.obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<SysRole> getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(List<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

}