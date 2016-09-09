package com.hhh.fund.dao.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the sys_user database table.
 * 
 */
@Entity
@Table(name="sys_user")
@NamedQuery(name="SysUser.findAll", query="SELECT s FROM SysUser s")
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="idGenerator") 
	@GenericGenerator(name="idGenerator", strategy="uuid")
	private String id;

	private int admin;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	private String displayname;

	private String email;

	private int locked;

	private String mobile;

	private String password;

	private String phone;

	private String salt;

	private int status;

	private String username;

	//bi-directional many-to-many association to SysRole
	@ManyToMany
	@JoinTable(
			name="sys_user_role"
			, joinColumns={
				@JoinColumn(name="user_id")
				}
			, inverseJoinColumns={
				@JoinColumn(name="role_id")
				}
			)
	private List<SysRole> sysRoles;

	//bi-directional many-to-many association to SysOrg
	@ManyToMany
	@JoinTable(
			name="sys_user_org"
			, joinColumns={
				@JoinColumn(name="user_id")
				}
			, inverseJoinColumns={
				@JoinColumn(name="org_id")
				}
			)
	private List<SysOrg> sysOrgs;

	public SysUser() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAdmin() {
		return this.admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getDisplayname() {
		return this.displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLocked() {
		return this.locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<SysRole> getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(List<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

	public List<SysOrg> getSysOrgs() {
		return this.sysOrgs;
	}

	public void setSysOrgs(List<SysOrg> sysOrgs) {
		this.sysOrgs = sysOrgs;
	}

}