package edu.stu.exception;

public class StuException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6711316754808360484L;

	public StuException(){}
	public StuException(String msg){
		super(msg);
	}
	
	public StuException(Throwable t){
		super(t);
	}
	
	public StuException(String msg,Throwable t){
		super(msg,t);
	}
}
