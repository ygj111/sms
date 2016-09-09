package com.hhh.fund.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.fund.dao.DictDao;
import com.hhh.fund.dao.entity.SysDict;
import com.hhh.fund.web.model.DictBean;

@Component
@Transactional
public class DictService {
private final static Logger logger = LoggerFactory.getLogger(DictService.class);
	
	@Autowired
	private DictDao dicDao;
	
	/**
	 * 分页获取字典数据
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	@Transactional(propagation =Propagation.NOT_SUPPORTED, readOnly=true)
	public List<DictBean> getAllDic(int pageNum, int pageSize) {
		PageRequest pageReq = new PageRequest(pageNum - 1, pageSize);
		
		Page<SysDict> dics = dicDao.findAll(pageReq);
		
		// 倒换字典数据
		List<SysDict> dicList = dics.getContent();
		List<DictBean> list = new ArrayList<DictBean>();
		DictBean dicBean = null;
		SysDict dic = null;
		
		for (int i =0; i < dicList.size(); i++) {
			dic = dicList.get(i);
			dicBean = new DictBean();
			
			dicBean.setCode(dic.getCode());
			dicBean.setName(dic.getName());
			dicBean.setStatus(dic.getStatus());
			dicBean.setParent(dic.getParent());
			dicBean.setCategory(dic.getCategory());
			
			list.add(dicBean);
		}
		
		return list;
	}
	
	/**
	 * 获取字典数据分页总数
	 * @param pageSize 页面显示记录数
	 * @return
	 */
	@Transactional(propagation =Propagation.NOT_SUPPORTED, readOnly=true)
	public int getDicPageCount(int pageSize) {
		logger.info("count=" + dicDao.count() );
		return (int)Math.ceil( (double)dicDao.count() / pageSize);
	}
	
	/**
	 * 获取指定code的字典数据
	 * @param code 编码
	 * @return 字典数据
	 */
	@Transactional(propagation =Propagation.NOT_SUPPORTED, readOnly=true)
	public DictBean findDictWithCode(String code) {
		SysDict dict = dicDao.findByCode(code);
		DictBean dictBean = null;
		
		if (dict != null) {
			dictBean = toDictBean(dict);
		}
		
		return dictBean;
	}
	
	/**
	 * 新增/修改字典数据
	 * @param dict 字典数据对象
	 */
	@Transactional( propagation=Propagation.REQUIRED )
	public void saveAndUpdateDict(DictBean dictBean) {
		SysDict dict = toSysDict(dictBean);
		dicDao.save(dict);
	}
	
	/**
	 * 实体对象转换
	 * @param dictBean 页面实体Bean
	 * @return 数据实体
	 */
	private SysDict toSysDict(DictBean dictBean) {
		SysDict dict = new SysDict();
		
		dict.setCode(dictBean.getCode());
		dict.setName(dictBean.getName());
		dict.setStatus(dictBean.getStatus());
		dict.setParent(dictBean.getParent());
		dict.setCategory(dictBean.getCategory());
		
		return dict;
	}
	
	/**
	 * 实体对象转换
	 * @param dict 数据实体
	 * @return 页面实体
	 */
	private DictBean toDictBean(SysDict dict) {
		DictBean dictBean = new DictBean();
		
		dictBean.setCode(dict.getCode());
		dictBean.setName(dict.getName());
		dictBean.setStatus(dict.getStatus());
		dictBean.setParent(dict.getParent());
		dictBean.setCategory(dict.getCategory());
		
		return dictBean;
	}
}
