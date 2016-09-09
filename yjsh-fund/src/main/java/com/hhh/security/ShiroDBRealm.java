package com.hhh.security;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhh.fund.service.UserService;
import com.hhh.fund.web.model.UserBean;
import com.hhh.security.util.EncryptHelper;

public class ShiroDBRealm extends AuthorizingRealm {
	private final static Logger logger = LoggerFactory.getLogger(ShiroDBRealm.class);
	@Autowired
	private UserService userService;
	
	/**
	 * 认证回调函数，登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) 
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = (String)token.getPrincipal();
//		logger.info("user=" + username + "; password=" + (String)token.getCredentials());
		UserBean user = userService.findByUsername(username);
		
		if (user == null)
			throw new UnknownAccountException();	//没找到帐号  
		
//		logger.info("salt=" + salt);
		
		SimpleAuthenticationInfo authenticateInfo 
						= new SimpleAuthenticationInfo(
													user.getUserName(), 
													user.getPassword(), 
													ByteSource.Util.bytes(user.getSalt()), getName());
		
		return authenticateInfo;
	}
	
	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}
	
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(EncryptHelper.HASH_ALGORITHM);
		matcher.setHashIterations(EncryptHelper.HASH_INTERATIONS);
		
		setCredentialsMatcher(matcher);
	}
}
