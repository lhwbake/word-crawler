package com.bake.crawler.word.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bake.crawler.word.service.CrawService;

@Component
@Scope("prototype")
public class CrawTask implements Runnable {
	
	@Autowired
	private CrawService crawService;
	
	private int type;

	private String url;

	private String cssQuery;

	public CrawTask() {}

	public CrawTask(int type, String url, String cssQuery) {
		this.type = type;
		this.url = url;
		this.cssQuery = cssQuery;
	}

	@Override
	public void run() {		
		crawService.getHTML(this.url, this.cssQuery, this.type);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCssQuery() {
		return cssQuery;
	}

	public void setCssQuery(String cssQuery) {
		this.cssQuery = cssQuery;
	}

	
	
}
