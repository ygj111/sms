package com.hhh.sms.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.sms.dao.entity.SmsPackage;

@Repository
public interface SmsPackageDao extends PagingAndSortingRepository<SmsPackage, String>{
	public SmsPackage findByPackageName(String packageName);
	
	@Modifying@Transactional
	@Query("update SmsPackage sp set sp.status=:status where sp.id=:packageId")
	public void updateStatus(@Param("status")int status,@Param("packageId")String packageId);
	
	public Page<SmsPackage> findByStatus(int status,Pageable page);
}
