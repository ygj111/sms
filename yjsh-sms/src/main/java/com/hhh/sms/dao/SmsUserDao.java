package com.hhh.sms.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.sms.dao.entity.SmsUser;


@Repository
public interface SmsUserDao extends PagingAndSortingRepository<SmsUser, String>,JpaSpecificationExecutor<SmsUser> {
	public Page<SmsUser> findByCustomerId(String customerId,Pageable pageable);
	
	public Page<SmsUser> findByCustomerIdAndCustomerName(String customerId,String customerName,Pageable pageable);
	
	public Page<SmsUser> findByCustomerName(String customerName,Pageable pageable);
	
	public Page<SmsUser> findByType(int type,Pageable pageable);
	
	public List<SmsUser> findByType(int type);
	
	public SmsUser findByUsername(String userName);
	
	public Page<SmsUser> findById(String id,Pageable pageable);
	
	@Modifying@Transactional
	@Query("update SmsUser su set su.msgAmount=:msgAmount where su.id=:id")
	public void updateMsgAmount(@Param("id")String id,@Param("msgAmount")int msgAmount);
	
	public SmsUser findByCustomerId(String customerId);
	
	public List<SmsUser> findByDept(String dept);
}
