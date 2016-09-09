package com.hhh.fund.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sys_role database table.
 * 
 */
@Entity
@Table(name="sys_role")
@NamedQuery(name="SysRole.findAll", query="SELECT s FROM SysRole s")
public class SysRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String desp;

	private String name;

	private int status;

	//bi-directional many-to-many association to SysUser
	@ManyToMany(mappedBy="sysRoles")
	private List<SysUser> sysUsers;

	//bi-directional many-to-many association to SysPermission
	@ManyToMany
	@JoinTable(
			name="sys_role_perm"
			, joinColumns={
				@JoinColumn(name="role_id")
				}
			, inverseJoinColumns={
				@JoinColumn(name="perm_id")
				}
			)
	private List<SysPermission> sysPermissions;

	public SysRole() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesp() {
		return this.desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<SysUser> getSysUsers() {
		return this.sysUsers;
	}

	public void setSysUsers(List<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

	public List<SysPermission> getSysPermissions() {
		return this.sysPermissions;
	}

	public void setSysPermissions(List<SysPermission> sysPermissions) {
		this.sysPermissions = sysPermissions;
	}

}