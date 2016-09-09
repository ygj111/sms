package com.hhh.sms.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhh.sms.common.ConstantClassField;
import com.hhh.sms.dao.entity.SmsConsumption;
import com.hhh.sms.dao.entity.SmsUser;
import com.hhh.sms.service.SmsConsumptionService;
import com.hhh.sms.service.SmsUserService;
import com.hhh.sms.web.model.DataTablesResult;
import com.hhh.sms.web.model.SmsConsumptionBean;
import com.hhh.sms.web.model.SmsCountBean;
import com.hhh.sms.web.model.SmsPage;
import com.hhh.sms.web.model.SmsSendInfoBean;


@Controller
@RequestMapping("/smsConsumption")
public class SmsConsumptionController {
	@Autowired
	private SmsConsumptionService scs;
	@Autowired
	private SmsUserService sus;
	
	private final static String PAGE_SIZE="10";
	
	/**
	 * 发送短信并保存消费记录
	 * @return
	 */
	@RequestMapping(value="sendAndSaveSms",method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> sendAndSaveSms(@RequestBody SmsSendInfoBean info){
		String content = info.getContent();
		String userNumbers = info.getUserNumbers();
		String userId = info.getUserId();
		//发送之前先检查企业短信量是否够用
		int page = scs.getPage(0, 10);
		PageRequest pr = new PageRequest(page, 10);
		SmsCountBean countBean = new SmsCountBean();
		countBean.setUserId(userId);
		List<SmsCountBean> list = scs.listSmsCount(countBean, pr);
		if(list.size()!=0){
			countBean = list.get(0);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		Integer msgRemain = countBean.getMsgRemain();//短信剩余量
		String numbers[] = userNumbers.split(",");
		if(msgRemain<numbers.length){//发送的短信量超过短信剩余量时
			map.put("msgRemain", countBean.getMsgRemain());
			return map;
		}
		Map<String,Object> map1 = scs.sendMsg(userId,content, userNumbers);
		List<SmsConsumption> recordList =(List<SmsConsumption>)map1.get("list");
		scs.saveSmsConsumption(recordList);
		List<String> failNums = new ArrayList<String>();
		for(SmsConsumption c : recordList){
			if(c.getStatus()==ConstantClassField.SMS_CONSUMPTION_STATUS_NOT_SEND){
				failNums.add(c.getTelephone());
			}
		}
		map.put("failNums", failNums);
		//发送完短信后统计发送成功的短信量并从管理员的短信量中扣除相应的短信量
//		int successAmount =  numbers.length-failNums.size();
//		List<SmsUser> managers = sus.findByType(ConstantClassField.SMS_USER_TYPE_MANAGER);
//		for(SmsUser manager:managers){
//			sus.updateMsgAmount(manager.getId(), manager.getMsgAmount()-successAmount);
//		}
		return map;
	}
	
	/**
	 * 查询短信消费记录明细
	 * @return
	 */
	@RequestMapping(value="list")
	public @ResponseBody DataTablesResult<SmsConsumptionBean> list(SmsConsumptionBean consumption,int draw,@RequestParam(value = "start",defaultValue="0") int start,
			@RequestParam(value = "length",defaultValue=PAGE_SIZE) int pageSize){
		int page = scs.getPage(start, pageSize);
		PageRequest pr = new PageRequest(page, pageSize);
//		SmsConsumptionBean sc = new SmsConsumptionBean();
		SmsPage<SmsConsumptionBean> cons = scs.listConsumptionByUserId(consumption,pr);
		Integer count = null;
		if(sus.getTypeOfUser(consumption.getUserId())==ConstantClassField.SMS_USER_TYPE_CUSTOMER){//只可以查看企业自己的记录
			count = scs.getCountByUserId(consumption.getUserId());
		 }
		 if(sus.getTypeOfUser(consumption.getUserId())==ConstantClassField.SMS_USER_TYPE_MANAGER&&consumption.getCustomerId()==null){//管理员可以查看所有记录
			 count = scs.getCount();
		 }
		 if(sus.getTypeOfUser(consumption.getUserId())==ConstantClassField.SMS_USER_TYPE_MANAGER&&consumption.getCustomerId()!=null){//当管理员点击明细时查看当前企业的记录
			 count = scs.getCountByUserId(consumption.getUserIdOfDetail());
		 }
		 if(sus.getTypeOfUser(consumption.getUserId())==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER&&consumption.getCustomerId()==null){//部门管理员可以查看所有记录
			 count = scs.getCountBySmsUserDept(consumption);
		 }
		 if(sus.getTypeOfUser(consumption.getUserId())==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER&&consumption.getCustomerId()!=null){//当部门管理员点击明细时查看当前企业的记录
			 count = scs.getCountByUserId(consumption.getUserIdOfDetail());
		 }
		DataTablesResult<SmsConsumptionBean> dtr = new DataTablesResult<SmsConsumptionBean>();
		dtr.setData(cons.getContent());
		dtr.setDraw(draw);
		dtr.setRecordsFiltered(new Long(cons.getTotalElements()).intValue());
		dtr.setRecordsTotal(count);
		return dtr;
	}
	
	/**
	 * 短信统计查询
	 * @return
	 */
	@RequestMapping(value="listSmsCount")
	public @ResponseBody DataTablesResult<SmsCountBean> listSmsCount(SmsCountBean countBean,int draw,@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "length", defaultValue = PAGE_SIZE) int pageSize){
		int page = scs.getPage(start, pageSize);
		PageRequest pr = new PageRequest(page, pageSize);
		List<SmsCountBean> list = scs.listSmsCount(countBean, pr);
		DataTablesResult<SmsCountBean> dtr = new DataTablesResult<SmsCountBean>();
		int type = sus.getTypeOfUser(countBean.getUserId());
		Integer count = null;
		if(type==ConstantClassField.SMS_USER_TYPE_MANAGER){
			count = scs.getTjCount(countBean);
		}else if(type==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER){
			count = scs.getTjCount(countBean);
		}else{
			count = 1;
		}
		dtr.setData(list);
		dtr.setDraw(draw);
		dtr.setRecordsFiltered(count);
		dtr.setRecordsTotal(count);
		return dtr;
	}
	
	
	/**
	 * 部门短信统计查询
	 * @return
	 */
	@RequestMapping(value="listSmsCountByDept")
	public @ResponseBody DataTablesResult<SmsCountBean> listSmsCountByDept(String customerName,int draw,@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "length", defaultValue = PAGE_SIZE) int pageSize){
		int page = scs.getPage(start, pageSize);
		PageRequest pr = new PageRequest(page, pageSize);
		List<SmsCountBean> list  = new ArrayList<SmsCountBean>();
		if(customerName !=null ){//点击查询按钮触发的方法
			list = scs.listCountByDept(customerName, pr);
		}else{//点击菜单触发的方法
			list = scs.listSmsCountByDept(pr);
		}
		int count = scs.findCountAll();
		DataTablesResult<SmsCountBean> dtr = new DataTablesResult<SmsCountBean>();
		dtr.setData(list);
		dtr.setRecordsFiltered(count);
		dtr.setRecordsTotal(count);
		dtr.setDraw(draw);
		return dtr;
	}
	
	/**
	 * 查询短信消费记录明细
	 * @return
	 */
	@RequestMapping(value="listByDept")
	public @ResponseBody DataTablesResult<SmsConsumptionBean> listByDept(String dept,int draw,@RequestParam(value = "start",defaultValue="0") int start,
			@RequestParam(value = "length",defaultValue=PAGE_SIZE) int pageSize){
		int page = scs.getPage(start, pageSize);
		PageRequest pr = new PageRequest(page, pageSize);
//		SmsConsumptionBean sc = new SmsConsumptionBean();
		SmsPage<SmsConsumptionBean> cons = scs.listConsumptionByDept(dept,pr);
		Integer count = null;
		DataTablesResult<SmsConsumptionBean> dtr = new DataTablesResult<SmsConsumptionBean>();
		dtr.setData(cons.getContent());
		dtr.setDraw(draw);
		dtr.setRecordsFiltered(new Long(cons.getTotalElements()).intValue());
		return dtr;
	}
	/**
	 * 跳转到短信消费记录页面
	 * @return
	 */
	@RequestMapping("toSmsConsumption")
	public String toSmsConsumption(){
		return "sms/consumption";
	}
	
	/**
	 * 跳转到部门短信消费记录页面
	 * @return
	 */
	@RequestMapping("toSmsCountByDept")
	public String toSmsConsumptionByDept(){
		return "sms/countByDept";
	}
	
	/**
	 * 跳转到短信统计页面
	 * @return
	 */
	@RequestMapping("toSmsCount")
	public String toSmsCount(){
		return "sms/count";
	}
	
	/**
	 * 跳转到发送短信页面
	 * @return
	 */
	@RequestMapping("toSendMsg")
	public String toSendMsg(){
		return "sms/message";
	}
}
