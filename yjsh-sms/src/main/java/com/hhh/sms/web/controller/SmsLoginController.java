package com.hhh.sms.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SmsLoginController {
	
	/**
	 * 跳转到登陆页面
	 * @return
	 */
	@RequestMapping(value="login",method = RequestMethod.GET)
	public String login(){
		return "sms/login";
	}
	
	@RequestMapping(value="login",method=RequestMethod.POST)
	public String login(String username,String password,Model model,HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		try {
			subject.login(token);
			return "redirect:/smsUser/toUserManage";
		}catch (AuthenticationException e) {
			e.printStackTrace();
			token.clear();
			request.setAttribute("loginTips", "用户名/密码错误");
			return "sms/login";
		}
	}
}
