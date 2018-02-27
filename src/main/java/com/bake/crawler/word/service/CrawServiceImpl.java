package com.bake.crawler.word.service;

import java.util.Date;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bake.crawler.word.dto.WordDao;
import com.bake.crawler.word.entity.Word;
import com.bake.crawler.word.util.KeyUtil;

@Service
public class CrawServiceImpl implements CrawService {
	
	private static Logger logger = LoggerFactory.getLogger(AnalyzeServiceImpl.class);
	
	@Autowired
	private WordDao wordDao;
	
	@Override
	public void getHTML(String url, String  cssQuery, int type) {
		try{			
			Document doc = Jsoup.connect(url).get();
			Elements newsHeadLines = doc.select(cssQuery);
			ListIterator<String> iterator = newsHeadLines.eachText().listIterator();
			while(iterator.hasNext()) {
				String content = iterator.next();
				logger.info("{} 采集到的内容 {}", Thread.currentThread().getName(), content);
				
				Word word = new Word();
				word.setId(KeyUtil.generatorUUID());
				word.setUpdateTime(new Date());
				word.setType(type);
				word.setLength(content.length());
				word.setContent(content);
				wordDao.insert(word);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
