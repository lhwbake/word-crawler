package com.bake.crawler.word.enumOperation;

/**
 * 字符长度
 * @author hadoop
 *
 */
public enum WordLengthEnum {

	ONE(1),

	TWO(2),

	THREE(3),

	FOUR(4),
	
	FIVE(5),
	
	SIX(6);

	private WordLengthEnum(int code){
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
