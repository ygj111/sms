package com.hhh.sms.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhh.sms.dao.entity.SmsPackage;
import com.hhh.sms.service.SmsPackageService;
import com.hhh.sms.web.model.DataTablesResult;
import com.hhh.sms.web.model.SmsPackageBean;
import com.hhh.sms.web.model.SmsUserBean;

@Controller
@RequestMapping("/smsPackage")
public class SmsPackageController {
	@Autowired
	private SmsPackageService sps;
	
	private final static String PAGE_SIZE="10";
	
	@RequestMapping(value="list",method = RequestMethod.GET)
	public @ResponseBody DataTablesResult<SmsPackageBean> list(int draw,@RequestParam(value = "start",defaultValue="0") int start,
			@RequestParam(value = "length",defaultValue="10") int pageSize,HttpSession session){
		int page = sps.getPage(start, pageSize);
		PageRequest pr = new PageRequest(page, pageSize);
		SmsUserBean loginUser = (SmsUserBean)session.getAttribute("loginUser");
		List<SmsPackageBean> list = sps.listAllSmsPackages(loginUser,pr);
		DataTablesResult<SmsPackageBean> dtr = new DataTablesResult<SmsPackageBean>();
		dtr.setData(list);
		dtr.setDraw(draw);
		dtr.setRecordsFiltered(sps.getCount());
		dtr.setRecordsTotal(sps.getCount());
		return dtr;
	}
	
	@RequestMapping(value="save",method = RequestMethod.POST)
	public @ResponseBody int save(@RequestBody SmsPackageBean packageBean){
		sps.saveOrUpdateSmsPackage(packageBean);
		return 1;
	}
	
	@RequestMapping(value="del",method = RequestMethod.GET)
	public @ResponseBody int del(@RequestParam(value="packageId") String packageId){
		sps.delSmsPackage(packageId);
		return 1;
	}
	
	@RequestMapping(value="getPageCount",method = RequestMethod.GET)
	public @ResponseBody int getPageCount(@RequestParam(value = "pagesize", defaultValue = PAGE_SIZE) int pageSize){
		int pageCount = sps.getPageCount(pageSize);
		return pageCount;
	}
	
	/**
	 * 批量删除
	 * @param packageId
	 * @return
	 */
	@RequestMapping(value="delPackages",method = RequestMethod.POST)
	public @ResponseBody int delPackages(@RequestParam("ids[]") List<String> ids){
		for(String id:ids)
			sps.delSmsPackage(id);
		return 1;
	}
	
	/**
	 * 跳转到短信套餐页面
	 * @return
	 */
	@RequestMapping("toSmsPackage")
	public String toSmsPackage(){
		return "sms/package";
	}
	
	/**
	 * 验证套餐名是否已存在
	 * @return
	 */
	@RequestMapping("checkPackageName")
	public @ResponseBody int checkPackageName(String packageName){
		SmsPackage p = sps.findByPackageName(packageName);
		if(p!=null){
			return 1;
		}else{
			return 0;
		}
	}
}
