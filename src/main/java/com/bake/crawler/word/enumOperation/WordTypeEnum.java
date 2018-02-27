package com.bake.crawler.word.enumOperation;

public enum WordTypeEnum {
	//日
	RI(1),
	
	//大
	DA(2),
	
	//思
	SI(3),
	
	//风
	FENG(4);
	
	private WordTypeEnum(int code){
		this.code = code;
	}
	private int code;
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}	
	
}
