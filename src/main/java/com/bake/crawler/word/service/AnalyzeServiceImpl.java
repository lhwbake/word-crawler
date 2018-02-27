package com.bake.crawler.word.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bake.crawler.word.dto.WordDao;
import com.bake.crawler.word.enumOperation.WordLengthEnum;
import com.bake.crawler.word.enumOperation.WordTypeEnum;

@Service
public class AnalyzeServiceImpl implements AnalyzeService{
	
	private static Logger logger = LoggerFactory.getLogger(AnalyzeServiceImpl.class);
	
	@Autowired
	private WordDao wordDao;

	@Override
	public Set<Map<String, Object>> analyze() throws Exception{

		List<Map<String, Object>> riList = wordDao.select(WordTypeEnum.RI.getCode(), WordLengthEnum.TWO.getCode());
		
		List<Map<String, Object>> daList = wordDao.select(WordTypeEnum.DA.getCode(), WordLengthEnum.TWO.getCode());
		
		List<Map<String, Object>> siList = wordDao.select(WordTypeEnum.SI.getCode(), WordLengthEnum.TWO.getCode());
		
		List<Map<String, Object>> fengList = wordDao.select(WordTypeEnum.FENG.getCode(), WordLengthEnum.TWO.getCode());
		
		Set<Map<String, Object>> newList1 = compare(riList, daList, 1, 1);
		System.out.println(newList1);
		Set<Map<String, Object>> newList2 = compare(siList, fengList, 0, 0);
		System.out.println(newList2);
		Set<Map<String, Object>> newList3 = compare(newList1,newList2, 1 , 0);
		System.out.println(newList3);
		return newList3;
	}
	
	
	public Set<Map<String, Object>> compare(Collection<Map<String, Object>> firstList, Collection<Map<String, Object>> secondList, int firstIndex, int secondIndex){
		
		Set<Map<String, Object>> newList = new HashSet<Map<String, Object>>();
		int count = 0;
		for(Map<String, Object> first : firstList) {
			for(Map<String, Object> second : secondList) {
				char firstChar  = first.get("content").toString().charAt(firstIndex);
				char secondChar = second.get("content").toString().charAt(secondIndex);
				count++;
				if(firstChar==secondChar){
					newList.add(first);
					newList.add(second);
				}
			}
		}
		logger.info("比较的次数为{}", count);
		return newList;
	}

}
