package edu.stu.user.action;

public class ValidateUser extends AbstractAction {
	public String execute()
	{
		this.setJsonResult("userId", getUserId());
		return JSON;
	}
}
