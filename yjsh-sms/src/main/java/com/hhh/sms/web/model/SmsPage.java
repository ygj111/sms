package com.hhh.sms.web.model;

import java.util.List;

public class SmsPage<T> {
	private int totalPages;//总页数
	
	private long totalElements;//总记录数
	
	private List<T> content;
	
	public SmsPage(int pages, long total, List<T> list){
		this.totalPages = pages;
		this.totalElements = total;
		this.content = list;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public List<T> getContent() {
		return content;
	}
}
