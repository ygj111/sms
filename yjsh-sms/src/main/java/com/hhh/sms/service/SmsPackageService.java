package com.hhh.sms.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.hhh.sms.common.ConstantClassField;
import com.hhh.sms.dao.SmsPackageDao;
import com.hhh.sms.dao.entity.SmsPackage;
import com.hhh.sms.web.model.SmsPackageBean;
import com.hhh.sms.web.model.SmsUserBean;

@Component
@Transactional
public class SmsPackageService {
	@Autowired
	private SmsPackageDao smsPackageDao;
	
	/**
	 * 增加或修改短信套餐
	 */
	public void saveOrUpdateSmsPackage(SmsPackageBean p){
		SmsPackage smsPackage = new SmsPackage();
		smsPackage.setId(p.getId());
		smsPackage.setPackageName(p.getPackageName());
		smsPackage.setPrice(p.getPrice());
		smsPackage.setMsgAmount(p.getMsgAmount());
		smsPackage.setPackageDes(p.getPackageDes());
		smsPackage.setStatus(p.getStatus());
		smsPackageDao.save(smsPackage);
	}
	
	/**
	 * 删除短信套餐
	 * 
	 */
	public void delSmsPackage(String id){
		//将短信套餐状态置为无效状态
		smsPackageDao.updateStatus(ConstantClassField.SMS_PACKAGE_STATUS_DISABLE,id);
	}
	
	/**
	 * 查询所有短信套餐
	 */
	public List<SmsPackageBean> listAllSmsPackages(SmsUserBean user,Pageable pageable){
		Page<SmsPackage> page = null;
		if(user.getType()==ConstantClassField.SMS_USER_TYPE_MANAGER){
			 page = smsPackageDao.findAll(pageable);
		}else{
			 page = smsPackageDao.findByStatus(ConstantClassField.SMS_PACKAGE_STATUS_ENABLE,pageable);
		}
		List<SmsPackageBean> list = new ArrayList<SmsPackageBean>();
		List<SmsPackage> spList = page.getContent();
		SmsPackageBean spb = null;
		for(SmsPackage sp : spList){
			spb = new SmsPackageBean();
			spb.setId(sp.getId());
			spb.setMsgAmount(sp.getMsgAmount());
			spb.setPackageName(sp.getPackageName());
			spb.setPrice(sp.getPrice());
			spb.setPackageDes(sp.getPackageDes());
			spb.setStatus(sp.getStatus());
			list.add(spb);
		}
		return list;
	}
	
	/**
	 * 根据id获取短信套餐
	 * @return
	 */
	public SmsPackageBean getPackageById(String id){
		SmsPackage sp = smsPackageDao.findOne(id);
		SmsPackageBean spb = new SmsPackageBean();
		spb.setId(sp.getId());
		spb.setMsgAmount(sp.getMsgAmount());
		spb.setPackageName(sp.getPackageName());
		spb.setPrice(sp.getPrice());
		spb.setPackageDes(sp.getPackageDes());
		spb.setStatus(sp.getStatus());
		return spb;
	}
	
	/**
	 * 获取总页数
	 * @param pageSize
	 * @return
	 */
	public int getPageCount(int pageSize) {
		return (int)Math.ceil( (double)smsPackageDao.count() / pageSize);
	}
	
	/**
	 * 获取记录总数
	 * @return
	 */
	public int getCount(){
		return (int)smsPackageDao.count();
	}
	
	/**
	 * 获取页码,从0页开始
	 * @return
	 */
	public int getPage(int start,int pageSize){
		return (int)Math.floor((double)start/pageSize);
	}
	
	/**
	 * 根据套餐名查找套餐
	 * @param packageName
	 * @return
	 */
	public SmsPackage findByPackageName(String packageName) {
		SmsPackage p = smsPackageDao.findByPackageName(packageName);
		return p;
	}
}
