package com.hhh.fund.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhh.fund.service.DictService;
import com.hhh.fund.web.model.DictBean;

@Controller
@RequestMapping("/fund")
public class DictRestController {
	@Autowired
	private DictService dictSer;

	private static final String PAGE_SIZE = "10";

	@RequestMapping(value = "/listDic", method = RequestMethod.GET)
	public @ResponseBody List<DictBean> getDicWithPage(@RequestParam(value = "page", defaultValue = "1") int pageNum,
			@RequestParam(value = "pagesize", defaultValue = PAGE_SIZE) int pageSize) {
		return dictSer.getAllDic(pageNum, pageSize);
	}

	@RequestMapping(value = "/getDicPageCount", method = RequestMethod.GET)
	public @ResponseBody int getDicPageCount(@RequestParam(value = "pagesize", defaultValue = PAGE_SIZE) int pageSize) {
		return dictSer.getDicPageCount(pageSize);
	}

	/**
	 * 获取指定code的字典数据
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/getDict", method = RequestMethod.GET)
	public @ResponseBody DictBean getDict(@RequestParam(value = "code") String code) {
		return dictSer.findDictWithCode(code);
	}

	/**
	 * 保存数据字典
	 * 
	 * @param dictBean
	 * @return 1 success
	 */
	@RequestMapping(value = "/saveDict", method = RequestMethod.POST)
	public @ResponseBody int saveDict(@RequestBody DictBean dictBean) {
		dictSer.saveAndUpdateDict(dictBean);
		return 1;
	}
}
