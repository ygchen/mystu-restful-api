package edu.stu.util;

public class ReturnData {
	private boolean success;
	private String err;
	
	public ReturnData()
	{
		this.success=true;
	}
	
	public ReturnData(boolean success,String errMsg)
	{
		this.success=success;
		this.err=errMsg;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	
}
