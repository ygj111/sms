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

@Repository
public interface SmsApplyRecordDao extends PagingAndSortingRepository<SmsApplyRecord, String>,JpaSpecificationExecutor<SmsApplyRecord> {
	@Query("select r from SmsApplyRecord r where r.smsUser.id = :userId")
	public Page<SmsApplyRecord> findByUserId(@Param("userId")String userId,Pageable pageable);
	
	//找出最大申购编号
	@Query("select max(sac.applyCode) from SmsApplyRecord sac")
	public String findMaxApplyCode();
	
	@Query("select count(1) from SmsApplyRecord r where r.smsUser.id=:userId")
	public int getCountByUserId(@Param("userId")String userId);
	
	@Modifying
	@Query("delete from SmsApplyRecord r where r.smsUser.id=:userId")
	public void deleteByUserId(@Param("userId")String userId);
}
