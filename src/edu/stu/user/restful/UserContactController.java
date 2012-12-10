package edu.stu.user.restful;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.ValidationAwareSupport;

import edu.stu.user.bean.UserContact;
import edu.stu.user.service.UserService;
import edu.stu.util.ReturnData;

@ResultPath(value="/WEB-INF/content/usercontact")
@Namespace(value="/rest")
public class UserContactController extends ValidationAwareSupport implements
		ModelDriven<Object> {
	
	private Object model;
	
	private int userId;
	
	private UserService userService;
		
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Object getModel() {
		if(model==null)
			model=new UserContact();
		return model;
	}
	
	
    public HttpHeaders show() {
    	model=userService.getUserContact(userId);
        return new DefaultHttpHeaders("show");
    }
    
    public String edit() {
    	model=userService.getUserContact(userId);
        return "edit";
    }
    
    
    public String update()
    {
    	this.userService.update((UserContact)this.model);    	  
    	this.model=new ReturnData();
    	return null;
    }

	public void setUserId(int userId) {	
		this.userId = userId;
	}
	

    public HttpHeaders index() {
        return new DefaultHttpHeaders("index")
            .disableCaching();
    } 
    
}
