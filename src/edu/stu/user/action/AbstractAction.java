package edu.stu.user.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.authentication.AttributePrincipal;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;

import edu.stu.user.bean.UserContact;
import edu.stu.user.service.UserService;

public abstract class AbstractAction extends ActionSupport {
	
	public final static String JSON="json";
	private UserService userService;
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private String jsonResult;
	
	protected UserContact getCurrentUser() {
		UserContact userContact = (UserContact) ServletActionContext.getRequest().getSession()
				.getAttribute("user");
		return userContact;
	}

	public String encode(String s) {
		if ((s == null) || (s.length() == 0))
			return s;
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return s;
	}
	

	public String getJsonResult() {
		return jsonResult;
	}
	
	protected void setJsonResult(String key,Object value)
	{
		JSONObject json=new JSONObject();
		json.put(key, value);
		setJsonResult(json);
	}
	
	protected void setJsonResult(String jsonStr){
		this.jsonResult=jsonStr;
	}
	
	protected void setJsonResult(JSONObject json){
		this.jsonResult=json.toJSONString();
	}
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-M-d HH:mm");
	public String format(long date)
	{
		return sdf.format(new Date(date));
	}
	
	protected int getUserId()
	{
		AttributePrincipal p=(AttributePrincipal) ServletActionContext.getRequest().getUserPrincipal();
		return new Integer(p.getAttributes().get("vid").toString());
	}
	/*protected boolean isValidUser(int userId)
	{
		AttributePrincipal p=(AttributePrincipal) ServletActionContext.getRequest().getUserPrincipal();
		Object val=p.getAttributes().get("vid");
		if(val!=null && new Integer(val.toString()).intValue()==userId)
			return true;
		else
			return false;
		
	}
	*/
}
