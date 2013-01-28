package edu.stu.user.restful;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;

import edu.stu.user.service.UserService;

public class UserProfileController implements ModelDriven<Object> {

	private String userId;
	private Object model;
	private UserService userService;
		
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	@Override
	public Object getModel() {	
		return model;
	}
	
	
	public String show()
	{
		this.model=this.userService.getUser(userId);
		if(model==null)
		{
			try {
				ServletActionContext.getResponse().sendError(404, "user not found");
				ServletActionContext.getResponse().flushBuffer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "show";
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public void setUsername(String username) {
		this.userId = username;
	}
	
	

}
