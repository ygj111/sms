package com.hhh.fund.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sys_dict database table.
 * 
 */
@Entity
@Table(name="sys_dict")
@NamedQuery(name="SysDict.findAll", query="SELECT s FROM SysDict s")
public class SysDict implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	private String category;

	private String name;

	private String parent;

	private int status;

	public SysDict() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}