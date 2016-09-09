package com.hhh.sms.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhh.sms.common.ConstantClassField;
import com.hhh.sms.dao.entity.SmsUser;
import com.hhh.sms.service.SmsApplyRecordService;
import com.hhh.sms.service.SmsPackageService;
import com.hhh.sms.service.SmsUserService;
import com.hhh.sms.web.model.DataTablesResult;
import com.hhh.sms.web.model.SmsApplyRecordBean;
import com.hhh.sms.web.model.SmsPackageBean;
import com.hhh.sms.web.model.SmsPage;

@Controller
@RequestMapping("/smsApplyRecord")
public class SmsApplyRecordController {
	@Autowired
	private SmsApplyRecordService sars;
	@Autowired
	private SmsUserService sus;
	@Autowired
	private SmsPackageService sps;
	
	private final static String PAGE_SIZE="10";
	
	/**
	 * 查询
	 * @param userId 用户id
	 * @param customerId 需要查看的企业id
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="list")
	public @ResponseBody DataTablesResult<SmsApplyRecordBean> list(SmsApplyRecordBean record,int draw,@RequestParam(value = "start",defaultValue="0") int start,@RequestParam(value = "length",defaultValue=PAGE_SIZE) int pageSize){
		int page = sars.getPage(start, pageSize);
		PageRequest pr = new PageRequest(page, pageSize);
		SmsPage<SmsApplyRecordBean> records = sars.listRecordsByUserId(record,pr);
		Integer count = null;
		if(sus.getTypeOfUser(record.getUserId())==ConstantClassField.SMS_USER_TYPE_CUSTOMER){//只可以查看企业自己的记录
			count = 1;
		 }
		 if(sus.getTypeOfUser(record.getUserId())==ConstantClassField.SMS_USER_TYPE_MANAGER&&record.getUserIdOfDetail()==null){//管理员可以查看所有记录
			 count = sars.getCount();
		 }
		 if(sus.getTypeOfUser(record.getUserId())==ConstantClassField.SMS_USER_TYPE_MANAGER&&record.getUserIdOfDetail()!=null){//当管理员点击用户管理明细时查看当前企业的记录
			 count = sars.getCountByUserId(record.getUserIdOfDetail());
		 }
		 if(sus.getTypeOfUser(record.getUserId())==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER&&record.getUserIdOfDetail()==null){//当管理员点击用户管理明细时查看当前企业的记录
			 count = sars.getCountBySmsUserDept(record);
		 }
		 if(sus.getTypeOfUser(record.getUserId())==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER&&record.getUserIdOfDetail()!=null){//当管理员点击用户管理明细时查看当前企业的记录
			 count = sars.getCountByUserId(record.getUserIdOfDetail());
		 }
		DataTablesResult<SmsApplyRecordBean> dtr = new DataTablesResult<SmsApplyRecordBean>();
		dtr.setData(records.getContent());
		dtr.setDraw(draw);
		dtr.setRecordsFiltered(new Long(records.getTotalElements()).intValue());
		dtr.setRecordsTotal(count);
		return dtr;
	}
	
	/**
	 * 短信申购
	 * @return
	 */
	@RequestMapping(value="smsApply",method = RequestMethod.POST)
	public @ResponseBody int smsApply(@RequestParam("userId") String userId,@RequestParam(value="packageIds[]") List<String> packageIds){
		//申购之前先比较管理员的短信量和申购的短信量
		SmsPackageBean spb = null;
		int msgSumAmount = 0;
		for(String packageId : packageIds){
			spb = sps.getPackageById(packageId);
			msgSumAmount = msgSumAmount + spb.getMsgAmount();
		}
		List<SmsUser> managers = sus.findByType(ConstantClassField.SMS_USER_TYPE_MANAGER);
		int msgAmountOfManager=0;
		for(SmsUser manager:managers){
			msgAmountOfManager = manager.getMsgAmount();
		}
		if(msgSumAmount>msgAmountOfManager){
			return 0;
		}else{
			for(String packageId : packageIds)
				sars.smsApply(userId,packageId);
			return 1;
		}
	}
	
	/**
	 * 短信申购审批
	 */
	@RequestMapping(value="smsApplyConfirm",method = RequestMethod.POST)
	public @ResponseBody int smsApplyConfirm(@RequestParam String applyRecordId){
		sars.smsApplyConfirm(applyRecordId);
		return 1;
	}
	
	/**
	 * 短信申购审批
	 */
	@RequestMapping(value="deleteApprove",method = RequestMethod.POST)
	public @ResponseBody int deleteApprove(@RequestParam String applyRecordId){
		sars.deleteApprove(applyRecordId);
		return 1;
	}
	
	/**
	 * 跳转到短信申购历史页面
	 * @return
	 */
	@RequestMapping("toSmsApplyRecord")
	public String toSmsApplyRecord(){
		return "sms/apply";
	}
	
	/**
	 * 跳转到短信申购页面
	 * @return
	 */
	@RequestMapping("toSmsApply")
	public String toSmsApply(){
		return "sms/manage_buy";
	}
}
