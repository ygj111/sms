package com.hhh.fund.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hhh.fund.dao.entity.SysUser;

@Repository
public interface UserDao extends CrudRepository<SysUser,  String> {
	public SysUser findByUsername(String username);
}
