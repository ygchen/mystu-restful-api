package edu.stu.user.restful;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.jasig.cas.client.authentication.AttributePrincipal;

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
    	if(this.userId!=getSessionUserId())//非本人，只能看公开的
    	{
    		UserContact ct=(UserContact) model;
    		if(!ct.isStuEmailVisible())
    		{
    			ct.setStuEmail(null);
    		}
    		
    		if(!ct.isAddressVisible())
    		{
    			ct.setDormBuildingAddress(null);
    			ct.setDormRoom(null);
    		}
    		
    		if(!ct.isHomeAddressVisible())
    		{
    			ct.setHomeAddress(null);
    		}
    		
    		if(!ct.isHomePageVisible())
    		{
    			ct.setHomePage(null);
    		}
    		
    		if(!ct.isMobileVisible())
    		{
    			ct.setMobile(null);
    		}
    		
    		if(!ct.isOfficePhoneVisible())
    		{
    			ct.setOfficePhone(null);
    		}
    		
    		if(!ct.isPersonalEmailVisible())
    		{
    			ct.setPersonalEmail(null);
    		}
    		
    		if(!ct.isPhoneShortnumberVisible())
    		{
    			ct.setPhoneShortnumber(null);
    		}
    	}

        return new DefaultHttpHeaders("show");
    }
    
    public String edit() {
    	model=userService.getUserContact(userId);
        return "edit";
    }
    
    
    public String update()
    {
    	if(this.userId!=getSessionUserId())
		{
			this.model= new ReturnData(false,"Illegal access!!");//试图修改别人的
		}
    	else
    	{
    		this.userService.update((UserContact)this.model);    	  
    		this.model=new ReturnData();
    	}
    	return null;
    }

	public void setUserId(int userId) {	
		this.userId = userId;
	}
	

    public HttpHeaders index() {
        return new DefaultHttpHeaders("index")
            .disableCaching();
    } 
    
    protected int getSessionUserId()
	{
		AttributePrincipal p=(AttributePrincipal) ServletActionContext.getRequest().getUserPrincipal();
		return new Integer(p.getAttributes().get("vid").toString());
	}
}
