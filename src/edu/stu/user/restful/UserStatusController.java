package edu.stu.user.restful;

import com.opensymphony.xwork2.ModelDriven;

import edu.stu.user.service.UserService;
import edu.stu.util.ReturnData;

public class UserStatusController implements ModelDriven<Object> {

	private int userId;
	private Object model;
	private UserService userService;
	
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUserId(int userId)
	{
		this.userId=userId;
	}
	
	@Override
	public Object getModel() {	
		if(model==null)
			model=new UserStatus();
		return model;
	}
	
	public String list()
	{
		this.model=this.userService.getUserStatuses(userId, 5);
		return null;
	}
	
	public String show()
	{
		String status=this.userService.getUserStatus(userId);
		this.model=new UserStatus(status);
		return "show";
	}
	
	public String update()
	{				
		this.userService.updateUserStatus(userId, ((UserStatus)model).getStatus());
		this.model=new ReturnData();
		
		return null;
	}
	

	public static class UserStatus
	{
		private String status;
		
		public UserStatus(){}
		
		public UserStatus(String st)
		{
			this.status=st;
		}
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
		
	}
}
