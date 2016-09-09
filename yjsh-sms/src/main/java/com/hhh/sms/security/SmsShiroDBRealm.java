package com.hhh.sms.security;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhh.fund.web.model.UserBean;
import com.hhh.security.util.EncryptHelper;
import com.hhh.sms.dao.SmsUserDao;
import com.hhh.sms.dao.entity.SmsUser;
import com.hhh.sms.service.SmsUserService;
import com.hhh.sms.web.model.SmsUserBean;

public class SmsShiroDBRealm extends AuthorizingRealm{
	@Autowired
	private SmsUserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String userName = (String)token.getPrincipal();
//		logger.info("user=" + username + "; password=" + (String)token.getCredentials());
		SmsUserBean user = userService.findByUserName(userName);
		
		if (user==null)
			throw new UnknownAccountException();	//没找到帐号  
		
//		logger.info("salt=" + salt);
		SimpleAuthenticationInfo authenticateInfo = null;
		authenticateInfo 
						= new SimpleAuthenticationInfo(
													user.getUsername(), 
													user.getPassword(), 
													ByteSource.Util.bytes(user.getSalt()), getName());
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		session.setAttribute("loginUser", user);
		return authenticateInfo;
	}
	
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(EncryptHelper.HASH_ALGORITHM);
		matcher.setHashIterations(EncryptHelper.HASH_INTERATIONS);
		
		setCredentialsMatcher(matcher);
	}
}
