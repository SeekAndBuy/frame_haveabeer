package com.seekandbuy.haveabeer.exceptions;

public class InvalidInputException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidInputException(String msg)
	{
        super(msg);
    }
	
	public InvalidInputException(String msg, Throwable cause)
	{
		super(msg, cause);
	}
}
