package com.seekandbuy.haveabeer.exceptions;

public class PromotionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PromotionNotFoundException(String msg){
        super(msg);
    }
	
	public PromotionNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}
}
