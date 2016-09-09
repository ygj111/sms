package com.hhh.sms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hhh.sms.web.model.SmsCountBean;

public interface SmsCountDao{
	//根据用户id查询发送短信统计记录
	public List<SmsCountBean> findSmsCount(String userId,String customerId,String customerName,Pageable pageable);
	
	//获取短信统计总记录数
	public int getCount(String userId, String customerId, String customerName);
	public List<SmsCountBean> findSmsCountByDept(Pageable pageable);
	public List<SmsCountBean> findCountByDept(String dept,Pageable pageable);
	//获取部门短信总记录数用来分页
	public List<SmsCountBean> findAllCount();
}
