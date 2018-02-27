package com.bake.crawler.word.dto;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bake.crawler.word.entity.Word;

@Repository
public class WordDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int insert(Word word){
		try{
			String sql = "insert into word(id, content, type, length, update_time ) values(?,?,?,?,?)";
			return jdbcTemplate.update(sql,word.getId(),word.getContent(),word.getType(),word.getLength(), word.getUpdateTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
		
	}
	
	public List<Map<String,Object>> select(int type, int length) throws Exception{
		String sql = "select content from word where type = ? and length = ?";
		return jdbcTemplate.queryForList(sql, type, length);
	}
}
