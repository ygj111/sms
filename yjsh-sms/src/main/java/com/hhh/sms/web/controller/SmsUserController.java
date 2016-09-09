package com.hhh.sms.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.Cookie;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhh.sms.common.ConstantClassField;
import com.hhh.sms.dao.entity.SmsUser;
import com.hhh.sms.service.SmsApiService;
import com.hhh.sms.service.SmsUserService;
import com.hhh.sms.web.model.DataTablesResult;
import com.hhh.sms.web.model.SmsLoginBean;
import com.hhh.sms.web.model.SmsPackageBean;
import com.hhh.sms.web.model.SmsPage;
import com.hhh.sms.web.model.SmsUserBean;

@Controller
@RequestMapping("/smsUser")
public class SmsUserController {
	@Autowired
	private SmsUserService smsUserService;
	
	private final static String PAGE_SIZE="10";
	@Autowired
	private Environment env;
	@RequestMapping(value="list")
	public @ResponseBody DataTablesResult<SmsUserBean> list(SmsUserBean smsUser,int draw,@RequestParam(value = "start",defaultValue="0") int start,
			@RequestParam(value = "length",defaultValue=PAGE_SIZE) int pageSize,HttpSession session){
		int page = smsUserService.getPage(start, pageSize);
		PageRequest pr = new PageRequest(page, pageSize);
		SmsUserBean loginUser = (SmsUserBean)session.getAttribute("loginUser");
		smsUser.setId(loginUser.getId());
		smsUser.setType(loginUser.getType());
		if(loginUser.getType() == 2){//当为部门管理员时，查出部门管理员的部门
			smsUser.setDept(loginUser.getDept());
		}
		SmsPage<SmsUserBean> users = smsUserService.listSmsUsers(smsUser,pr);
		DataTablesResult<SmsUserBean> dtr = new DataTablesResult<SmsUserBean>();
		Integer count = null;
		if(smsUser.getType()==ConstantClassField.SMS_USER_TYPE_MANAGER){
			count = smsUserService.getCount();
		}else if(smsUser.getType()==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER){
			count = smsUserService.getCountByDept(smsUser.getDept());
		}else{
			count = 1;//企业用户只可以看到自己的记录，所以只有一条记录
		}
		dtr.setData(users.getContent());
		dtr.setDraw(draw);
		dtr.setRecordsFiltered(new Long(users.getTotalElements()).intValue());
		dtr.setRecordsTotal(count);
		return dtr;
	}
	
	@RequestMapping(value="getPageCount",method = RequestMethod.GET)
	public @ResponseBody int getPageCount(@RequestParam(value = "pagesize", defaultValue = PAGE_SIZE) int pageSize){
		int pageCount = smsUserService.getPageCount(pageSize);
		return pageCount;
	}
	
	@RequestMapping(value="del",method = RequestMethod.GET)
	public @ResponseBody int del(@RequestParam(value="userId") String userId){
		smsUserService.deleteSmsUserById(userId);
		return 1;
	}
	
	@RequestMapping(value="save",method = RequestMethod.POST)
	public @ResponseBody int save(@RequestBody SmsUserBean smsUser){
		smsUserService.saveOrUpdateSmsUser(smsUser);
		return 1;
	}
	
	/**
	 * 批量删除
	 * @param packageId
	 * @return
	 */
	@RequestMapping(value="delUsers",method = RequestMethod.POST)
	public @ResponseBody int delUsers(@RequestParam("ids[]") List<String> ids){
		for(String id:ids)
			smsUserService.deleteSmsUserById(id);
		return 1;
	}
	
	/**
	 * 跳转到用户管理
	 * @return
	 */
	@RequestMapping("toUserManage")
	public String toUserManage(HttpSession session,Model model){
//		SmsUser loginUser = (SmsUser)session.getAttribute("loginUser");
//		model.addAttribute("loginUser", loginUser);
		return "sms/manage";
	}
	
	/**
	 * 检验用户名是否已存在
	 * @return
	 */
	@RequestMapping(value="checkUserName",method = RequestMethod.POST)
	public @ResponseBody int checkUserName(@RequestParam("username") String username){
		SmsUserBean user = smsUserService.findByUserName(username);
		if(user!=null){
			return 1;
		}else{
			return 0;
		}
	}
	/**
	 * 得到部门的配置信息
	 * @return
	 */
	@RequestMapping(value="getDept",method = RequestMethod.POST)
	public @ResponseBody List<String> getDept(){
		String dept = env.getProperty("dept");
		String[] depts = dept.split("\\,");
		//从sms.properties中将部门信息拿到
		List<String> list = new ArrayList<String>();
		for(int i=0;i<depts.length;i++){
			String deptname = depts[i];
			list.add(deptname);
		}
		return list;
	}
	
	/**
	 * 得到所有的用户权限
	 * @return
	 */
	@RequestMapping(value="getUserType",method = RequestMethod.POST)
	public @ResponseBody List<String> getUserType(){
		String dept = env.getProperty("usertype");
		String[] depts = dept.split("\\,");
		//从sms.properties中将部门信息拿到
		List<String> list = new ArrayList<String>();
		for(int i=0;i<depts.length;i++){
			String deptname = depts[i];
			list.add(deptname);
		}
		return list;
	}
	/**
	 * 登陆
	 * @return
	 */
//	@RequestMapping("checkUserNameAndPwd")
//	public String checkUserNameAndPwd(@RequestParam("userName")String userName,@RequestParam("password")String password,HttpSession session,Model model){
//		Map<String,Object> map = smsUserService.checkUserNameAndPassword(userName,password);
//		boolean result=false;
//		SmsUserBean loginUser =null;
//		for(String key:map.keySet()){
//			result = (boolean)map.get("result");
//			if(result){
//				loginUser = smsUserService.userToUserBean((SmsUser)map.get("loginUser"));
//				session.setAttribute("loginUser", loginUser);
//			}
//		}
//		if(result){
//			return "redirect:/smsUser/toUserManage";
//		}
//		else{
//			model.addAttribute("failTips", "用户名或者密码错误");
//			return "sms/login";
//		}
//	}
	
	/**
	 * 验证用户名密码
	 * @return
	 */
//	@RequestMapping(value="checkUserNameAndPwd",method=RequestMethod.POST)
//	public String checkUserNameAndPwd(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM)String userName,HttpSession session,Model model){
//		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
//		return "sms/login";
//	}
}
