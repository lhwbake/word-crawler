package com.bake.crawler.word;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bake.crawler.word.enumOperation.WordTypeEnum;
import com.bake.crawler.word.service.AnalyzeService;
import com.bake.crawler.word.task.CrawTask;
import com.bake.crawler.word.util.SpringContextUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordCrawlerApplicationTests {

	private static Logger logger = LoggerFactory.getLogger(WordCrawlerApplicationTests.class);
	
	@Autowired
	private AnalyzeService analyzeService;	

	@Test
	public void contextLoads() {
		try{
			/*String[] urls = {"http://xh.5156edu.com/ciyu/190890begin89082.html",
							 "http://xh.5156edu.com/ciyu/73890begin34482.html",
							 "http://xh.5156edu.com/ciyu/204765end95557.html",
							 "http://xh.5156edu.com/ciyu/161985end75593.html"};
				*/							
			//String cssQuery = "td[width=25%]";
	//===============================================================================================	
			
			String cssQuery = "li.unit > a";
			
			//日
			final int riPageSize = 82;
			String url1 = "http://www.hujiang.com/cidian/search/日_1/";
			
			//大
			final int daPageSize = 170;
			String url2 = "http://www.hujiang.com/cidian/search/大_1/";
						
			//思
			final int siPageSize = 36;
			String url3 = "http://www.hujiang.com/cidian/search/思_1/";
						
			//风
			final int fengPageSize = 128;
			String url4 = "http://www.hujiang.com/cidian/search/风_1/";
			
			List<Map<String,Object>> urls = new ArrayList<Map<String,Object>>(riPageSize+daPageSize+siPageSize+fengPageSize);
			urls.addAll(getUrls(url1,riPageSize,WordTypeEnum.RI.getCode()));
			urls.addAll(getUrls(url2,daPageSize,WordTypeEnum.DA.getCode()));
			urls.addAll(getUrls(url3,siPageSize,WordTypeEnum.SI.getCode()));
			urls.addAll(getUrls(url4,fengPageSize,WordTypeEnum.FENG.getCode()));
			
			executor(urls,cssQuery);				
		}catch(Exception e){
			e.printStackTrace();
		}
	}	

	@Test
	public void analy(){
		try{
			Set<Map<String, Object>> set = analyzeService.analyze();
			System.out.println(set);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 执行爬虫方法
	 * @param urls
	 * @param cssQuery
	 */
	private void executor(List<Map<String,Object>> urls, String cssQuery)  {
		try{			
			ExecutorService pool = Executors.newCachedThreadPool();			
			for(int i=0;i<urls.size();i++){	
				CrawTask crawTask = SpringContextUtil.getApplicationContext().getBean(CrawTask.class);
				logger.info("地址: {}", crawTask);
				crawTask.setType(Integer.parseInt(urls.get(i).get("type").toString()));
				crawTask.setUrl(urls.get(i).get("url").toString());
				crawTask.setCssQuery(cssQuery);
				
				//new Thread(()->crawTask.run(),"Thread-read-" + i).start();
				pool.execute(crawTask);
				logger.info("线程池状态:{}", pool.isTerminated());
				logger.info("执行任务数: {}", i);
				if(i%3==0)
				TimeUnit.SECONDS.sleep(1);
				
			}
			
			 pool.shutdown();
			 
			 if(pool.isTerminated()) {
				 analy();
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成目标url链接集合
	 * @param url
	 * @param pageSize
	 * @return
	 */
	private List<Map<String,Object>> getUrls(String url, int pageSize, Integer type) {
		List<Map<String,Object>> urls = new ArrayList<Map<String,Object>>(pageSize);
		StringBuilder sb = new StringBuilder(url);
		for(int i=1; i<pageSize+1; i++){			
			sb.replace(sb.toString().indexOf('_')+1, sb.toString().length()-1, String.valueOf(i));
			Map<String,Object> map = new HashMap<String,Object>(1);
			map.put("type", type);
			map.put("url", sb.toString());
			urls.add(map);
						
		}
		return urls;
	}
	
}
