package com.hhh.sms.common;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.hhh.sms.web.model.SmsBean;
import com.hhh.sms.web.model.SmsLoginBean;
import com.hhh.sms.web.model.SmsResultBean;

public class SmsApi {
	/**
	 * 发送短信
	 * @param loginBean
	 * @param smsBean
	 */
	public SmsResultBean sendMsg(SmsLoginBean loginBean,SmsBean smsBean){
		String responseInfo = null;
		SmsResultBean resultBean = new SmsResultBean();
		String description =null;
		Integer result = null;
		String other = null;
		try{
			responseInfo = send(loginBean,smsBean);
//			System.out.println(responseInfo);
			//获取result值
			Matcher m1=Pattern.compile("result=(.*?)(&|$)").matcher(responseInfo);
			while(m1.find())result = Integer.valueOf(m1.group(1));
//			Matcher m3=Pattern.compile("faillist=(.*?)&").matcher(responseInfo);
//			System.out.println(m3.find());
//			while(m3.find())System.out.println("faillist:"+m3.group(1).equals(""));
			//获取description值
//			if(responseInfo.indexOf("&",responseInfo.indexOf("&")+1)<0){
//				description = responseInfo.substring(responseInfo.indexOf("description=")+"description=".length(),responseInfo.length());
//			}else{
//				Matcher m2=Pattern.compile("description=(.*?)&").matcher(responseInfo);
//				while(m2.find())description = m2.group(1);
//			}
			//获取other
			if(responseInfo.indexOf("&")<0){
				other = null;
			}else{
				other = responseInfo.substring(responseInfo.indexOf("&")+1,responseInfo.length());
			}
			resultBean.setResult(result);
			resultBean.setOther(other);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultBean;
	}
	
	/**
	 * 发送短信
	 * @param loginBean
	 * @param smsBean
	 * @return
	 */
	public String send(SmsLoginBean loginBean,SmsBean smsBean){
		String responseInfo = null;
		try{
			HttpClient httpclient = new HttpClient();
			PostMethod post = new PostMethod("https://smsapi.ums86.com:9600/sms/Api/Send.do");
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
			post.addParameter("SpCode", loginBean.getSpCode());
			post.addParameter("LoginName", loginBean.getLoginName());
			post.addParameter("Password", loginBean.getPassword());
			post.addParameter("MessageContent", smsBean.getMessageContent());
			post.addParameter("UserNumber", smsBean.getUserNumber());
			post.addParameter("SerialNumber", smsBean.getSerialNumber());
			post.addParameter("ScheduleTime", smsBean.getScheduleTime());
			post.addParameter("ExtendAccessNum", smsBean.getExtendAccessNum());
			post.addParameter("f", smsBean.getF());
			httpclient.executeMethod(post);
			responseInfo = new String(post.getResponseBody(),"gbk");
//			System.out.println(responseInfo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return responseInfo;
	}
	
	/**
	 * 查询剩余短信量
	 * @return
	 */
	public Map<String,Object> getRemainNumber(SmsLoginBean loginBean){
		String responseInfo = null;
		String description =null;
		Integer result = null;
		Integer number = null;
		Map<String,Object> map = null;
		try {
			HttpClient httpclient = new HttpClient();
			PostMethod post = new PostMethod("https://smsapi.ums86.com:9600/sms/Api/SearchNumber.do");
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
			post.addParameter("SpCode", loginBean.getSpCode());
			post.addParameter("LoginName", loginBean.getLoginName());
			post.addParameter("Password", loginBean.getPassword());
			httpclient.executeMethod(post);
			responseInfo = new String(post.getResponseBody(),"gbk");
			System.out.println(responseInfo);
			//获取result值
			Matcher r=Pattern.compile("result=(.*?)(&|$)").matcher(responseInfo);
			while(r.find())result = Integer.valueOf(r.group(1));
			Matcher d=Pattern.compile("description=(.*?)(&|$)").matcher(responseInfo);
			while(d.find())description = d.group(1);
			Matcher n=Pattern.compile("number=(.*?)(&|$)").matcher(responseInfo);
			while(n.find())number = Integer.valueOf(n.group(1));
			map = new HashMap<String,Object>();
			map.put("result", result);
			map.put("description", description);
			map.put("number", number);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static void main(String[] args) {
		SmsLoginBean loginBean = new SmsLoginBean();
		loginBean.setSpCode("103904");
		loginBean.setPassword("3hmis8030");
		loginBean.setLoginName("gz_yjshrj");
		SmsApi smsApi = new SmsApi();
		smsApi.getRemainNumber(loginBean);
	}
}
