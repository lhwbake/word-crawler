package com.bake.crawler.word.entity;

import java.sql.Time;
import java.util.Date;

import lombok.Data;

@Data
public class Word {

	private String id;
	
	private String content;
	
	private int type;
	
	private int length;
	
	private Date updateTime;
	
	private Time timeStamp;
	
}
