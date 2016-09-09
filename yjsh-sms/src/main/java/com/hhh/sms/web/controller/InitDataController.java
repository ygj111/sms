package com.hhh.sms.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhh.fund.web.model.UserBean;
import com.hhh.sms.common.ConstantClassField;
import com.hhh.sms.dao.entity.SmsUser;
import com.hhh.sms.service.SmsUserService;
import com.hhh.sms.web.model.SmsUserBean;

@Controller
public class InitDataController {

	@Autowired
	private SmsUserService userService;
	
	/**
	 * 初始化数据， 添加admin用户
	 * @return
	 */
	@RequestMapping(value="/initdata",  
            method=RequestMethod.GET,produces={"application/xml", "application/json"})
	public String initData(){
		if(userService.findByUserName("admin") == null){
			SmsUserBean user = new SmsUserBean();
			user.setUsername("admin");
			user.setPassword("123");
			user.setCustomerId("10000");
			user.setCustomerName("粤建三和");
			user.setType(ConstantClassField.SMS_USER_TYPE_MANAGER);
			userService.saveOrUpdateSmsUser(user);
		}
		return "initdata";
	}
}
