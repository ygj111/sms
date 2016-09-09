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

import com.hhh.sms.dao.entity.SmsApplyRecord;
import com.hhh.sms.dao.entity.SmsConsumption;
import com.hhh.sms.web.model.SmsCountBean;

@Repository
public interface SmsConsumptionDao extends PagingAndSortingRepository<SmsConsumption, String>,JpaSpecificationExecutor<SmsConsumption>{
	
	@Query("from SmsConsumption r where r.smsUser.customerId=:customerId")
	public Page<SmsConsumption> findByCustomerId(@Param("customerId")String customerId,Pageable pageable);
	
	@Query("from SmsConsumption r where r.smsUser.customerName=:customerName")
	public Page<SmsConsumption> findByCustomerName(@Param("customerName")String customerName,Pageable pageable);
	
	@Query("from SmsConsumption r where r.smsUser.customerName=:customerName and r.smsUser.customerId=:customerId")
	public Page<SmsConsumption> findByCustomerIdAndCustomerName(@Param("customerId")String customerId,@Param("customerName")String customerName,Pageable pageable);
	
	//查询某个企业发送成功的短信总数
	@Query("select count(1) from SmsConsumption r where r.status=1 and r.smsUser.id =:userId")
	public int findSumMsgByCustomerId(@Param("userId") String userId);
	
	//查询指定用户的消费记录明细
	@Query("from SmsConsumption sc where sc.smsUser.id=:userId")
	public Page<SmsConsumption> findByUserId(@Param("userId") String userId,Pageable pageable);
	
	//查询指定企业的所有消费明细
	@Query("select count(1) from SmsConsumption sc where sc.smsUser.id =:userId")
	public int findCountByUserId(@Param("userId") String userId);
	
	@Modifying
	@Query("delete from SmsConsumption c where c.smsUser.id=:userId")
	public void deleteByUserId(@Param("userId")String userId);
	
	public List<SmsConsumption> findBySmsUserId(String userId);
	
}
