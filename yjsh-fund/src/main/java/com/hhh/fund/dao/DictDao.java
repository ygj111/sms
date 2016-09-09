package com.hhh.fund.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hhh.fund.dao.entity.SysDict;

@Repository
public interface DictDao extends PagingAndSortingRepository<SysDict, String>, JpaSpecificationExecutor<SysDict> {
	SysDict findByCode(String code);
}
