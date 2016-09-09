package com.hhh.fund.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.fund.dao.UserDao;
import com.hhh.fund.dao.entity.SysUser;
import com.hhh.fund.web.model.UserBean;
import com.hhh.security.util.EncryptHelper;


/**
 * 系统用户管理
 * @author mars.zhong
 *
 */
@Component
@Transactional
public class UserService {
	@Autowired
	private UserDao userDao;
	
	/**
	 * 新建用户
	 */
//	@Override
	@Transactional( propagation=Propagation.REQUIRED )
	public UserBean createUser(UserBean user) {
		
		String salt = EncryptHelper.randomNumberSalt();
		String pwd = EncryptHelper.entrypt(user.getPassword(), salt);
	
		
		SysUser sysUser = new SysUser();
		sysUser.setUsername(user.getUserName());
		sysUser.setPassword(pwd);
		sysUser.setSalt(salt);
		sysUser.setEmail(user.getEmail());
		sysUser.setPhone(user.getPhone());
		sysUser.setDisplayname(user.getDisplayName());
		sysUser.setCreatetime(new Date());
		
		userDao.save(sysUser);
		
		return user;
	}



//	@Override
	@Transactional(propagation =Propagation.NOT_SUPPORTED, readOnly=true)
	public UserBean findByUsername(String userName) {
		SysUser sysUser = userDao.findByUsername(userName);
		
		if (sysUser != null) {
			UserBean user = new UserBean();
			
			user.setUserId(sysUser.getId());
			user.setUserName(sysUser.getUsername());
			user.setPassword(sysUser.getPassword());
			user.setSalt(sysUser.getSalt());
			user.setDisplayName(sysUser.getDisplayname());
			user.setEmail(sysUser.getEmail());
			user.setPhone(sysUser.getPhone());
			
			return user;
		}
		
		return null;
	}

}
