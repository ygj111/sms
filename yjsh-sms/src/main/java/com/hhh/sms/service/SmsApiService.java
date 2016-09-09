package com.hhh.sms.service;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhh.sms.common.SmsApi;
import com.hhh.sms.web.model.SmsLoginBean;

@Component
@Transactional
public class SmsApiService {
	@Autowired
	private Environment env;
	
	public String getRemainNumber(){
		SmsApi smsApi = new SmsApi();
		SmsLoginBean loginBean = new SmsLoginBean();
		loginBean.setSpCode(env.getProperty("spCode"));
		loginBean.setPassword(env.getProperty("password"));
		loginBean.setLoginName(env.getProperty("loginName"));
		Map<String,Object> map = smsApi.getRemainNumber(loginBean);
		Integer result = (Integer)map.get("result");
		if(result==0){
			return map.get("number").toString();
		}else{
			return "fail";
		}
	}
}
