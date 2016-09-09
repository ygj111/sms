package com.hhh.sms.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hhh.sms.common.ConstantClassField;
import com.hhh.sms.common.SmsApi;
import com.hhh.sms.dao.entity.SmsConsumption;
import com.hhh.sms.dao.entity.SmsUser;
import com.hhh.sms.service.SmsApiService;
import com.hhh.sms.service.SmsConsumptionService;
import com.hhh.sms.service.SmsUserService;
import com.hhh.sms.web.model.SmsBean;
import com.hhh.sms.web.model.SmsCountBean;
import com.hhh.sms.web.model.SmsLoginBean;
import com.hhh.sms.web.model.SmsResultBean;

@Controller
@RequestMapping("/smsApi")
public class SmsApiController {
	@Autowired
	private SmsUserService sus;
	@Autowired
	private SmsApiService apiService;
	@Autowired
	private SmsConsumptionService scs;
	@Autowired
	private Environment env;
	
//	@RequestMapping("toSendMsg")
//	public String toSendMsg(){
//		return "sms/message";
//	}
	
	/**
	 * 单点登录
	 * @param customerId
	 * @param password
	 * @return
	 */
	@RequestMapping(value="sso")
	public String sso(String customerId,String password,HttpSession session,RedirectAttributes redirectAttributes){
		Subject subject = SecurityUtils.getSubject();
		SmsUser customer = sus.findByCustomerId(customerId);
		UsernamePasswordToken token = new UsernamePasswordToken(customer.getUsername(),password);
		try {
			subject.login(token);
			return "redirect:/smsUser/toUserManage";
		}catch (AuthenticationException e) {
			e.printStackTrace();
			token.clear();
//			model.addAttribute("loginTips", "用户名/密码错误");
			return "sms/error";
		}
	}
	
	/**
	 * 从联通短信api处同步短信量到短信平台
	 * @param session
	 * @return
	 */
	@RequestMapping("getRemainNumber")
	public @ResponseBody String getRemainNumber(HttpSession session){
		SmsLoginBean loginBean = new SmsLoginBean();
		loginBean.setSpCode(env.getProperty("spCode"));
		loginBean.setPassword(env.getProperty("password"));
		loginBean.setLoginName(env.getProperty("loginName"));
		String result = apiService.getRemainNumber();
		List<SmsUser> users = sus.findByType(ConstantClassField.SMS_USER_TYPE_MANAGER);
		if(result.equals("fail")){
			return "fail";
		}else{
			//修改短信量并保存
			for(SmsUser user:users){
				sus.updateMsgAmount(user.getId(), Integer.valueOf(result));
			}
			return result;
		}
	}
	
	/**
	 * 发送短信
	 * @param customerId
	 * @param phones 手机号码(多个号码用”,”分隔)
	 * @param messages
	 * @param reservedtime 预约发送时间，格式:yyyyMMddHHmmss,如‘20090901010101’， 立即发送请填空
	 * @return
	 */
	@RequestMapping(value="send",method=RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public @ResponseBody String send(String customerId,String phones,String messages,String reservedtime) {
		SmsLoginBean loginBean = new SmsLoginBean();
		loginBean.setSpCode(env.getProperty("spCode"));
		loginBean.setPassword(env.getProperty("password"));
		loginBean.setLoginName(env.getProperty("loginName"));
		SmsBean smsBean = new SmsBean();
		smsBean.setMessageContent(messages);
		smsBean.setUserNumber(phones);
		//生成流水号
		Random random = new Random(); 
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String serialNumber = df.format(new Date())+String.valueOf(random.nextInt(100)+100);
		smsBean.setSerialNumber(serialNumber);//流水号
		smsBean.setScheduleTime("");
		smsBean.setExtendAccessNum("");
		smsBean.setF("1");
		smsBean.setScheduleTime(reservedtime);
		SmsUser customer = sus.findByCustomerId(customerId);
		if(customer==null){
			return "result=99&descriptin=企业id为"+customerId+"的企业还没注册!";
		}
		SmsCountBean scb = new SmsCountBean();
		scb.setUserId(customer.getId());
		List<SmsCountBean> scbList = scs.listSmsCount(scb, null);
		SmsCountBean scbResult = null;
		if(scbList.size()!=0)scbResult = scbList.get(0);
		//短信剩余量
		Integer msgRemain = scbResult.getMsgRemain();
		Integer phonesNum = phones.split(",").length;
		SmsApi smsApi = new SmsApi();
		String result = null;
		if(msgRemain<phonesNum){
			result = "result=98&descriptin=已超过企业短信剩余量!请再申请短信量！";
		}else{
			Map<String,Object> map = scs.sendMsg(customer.getId(),messages, phones);
			List<SmsConsumption> recordList =(List<SmsConsumption>)map.get("list");
			scs.saveSmsConsumption(recordList);
			SmsResultBean resultBean = (SmsResultBean)map.get("result");
			result="result="+resultBean.getResult()+"&"+resultBean.getOther();
			System.out.println(result);
			//result = smsApi.send(loginBean, smsBean);
		}
		return result;
	}
}
