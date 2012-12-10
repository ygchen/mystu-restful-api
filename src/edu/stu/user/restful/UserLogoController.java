package edu.stu.user.restful;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ResultPath;
import org.jasig.cas.client.authentication.AttributePrincipal;

import com.opensymphony.xwork2.ModelDriven;

import edu.stu.generic.StaticFileService;
import edu.stu.user.Const;
import edu.stu.user.action.TempFileMonitor;
import edu.stu.user.service.UserService;
import edu.stu.util.ImageUtils;
import edu.stu.util.ReturnData;

@ResultPath(value="/WEB-INF/content/logo")
@Namespace(value="/rest")
public class UserLogoController  implements ModelDriven<Object>{

	private int userId;
	private StaticFileService staticFileService;
	private UserService userService;
	
	protected  Object model;
	
	public void setStaticFileService(StaticFileService staticFileService) {
		this.staticFileService = staticFileService;
	}
	
	public StaticFileService getStaticFileService() {
		return staticFileService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	

	public UserService getUserService() {
		return userService;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
		
	public int getUserId() {
		return userId;
	}

	protected String getImageUrl()
	{
//		return (String)ServletActionContext.getRequest().getSession().getAttribute(Const.SESSION_KEY_TEMP_LOGO_URL);
		return this.userService.getUserLogo(userId);
	}
	
	public String show()
	{
		this.model=new ReturnUrl(getImageUrl());
		return "show";
	}
	
	public String crop()
	{

		if(this.userId!=getSessionUserId())
		{
			new ReturnData(false,"Illegal access!!");//试图修改别人的
		}
		
		HttpSession session=ServletActionContext.getRequest().getSession();
		TempFileMonitor urlMonitor=(TempFileMonitor)session.getAttribute(Const.SESSION_KEY_TEMP_LOGO_URL);
//		String url=(String)session.getAttribute(Const.SESSION_KEY_TEMP_LOGO_URL);
		if(urlMonitor==null)
			model=new ReturnData(false,"logo not exist!!");
		else
		{
			OperationParam operationParam=(OperationParam)model;	
			try {
				String newUrl=this.staticFileService.generateUrl(urlMonitor.getUrl().substring(urlMonitor.getUrl().lastIndexOf(".")+1));
				ImageUtils.scaleAndCropImageFile(this.staticFileService.getFilePath(urlMonitor.getUrl()), operationParam.getScaledWidth(),operationParam.getScaledHeight(),
						operationParam.getX(), operationParam.getY(), operationParam.getWidth(), operationParam.getHeight(),this.staticFileService.getFilePath(newUrl));
				
				userService.updateUserLogo(userId,newUrl);
				urlMonitor.setUsed(true);
				session.removeAttribute(Const.SESSION_KEY_TEMP_LOGO_URL);
				model=new ReturnUrl(newUrl);
			} catch (IOException e) {
				model=new ReturnData(false,"文件 IO 异常!");
			}
		}
		return null;
	}
	
	public String rotate()
	{

		if(this.userId!=getSessionUserId())
		{
			new ReturnData(false,"Illegal access!!");//试图修改别人的
		}
		
		HttpSession session=ServletActionContext.getRequest().getSession();
			/*String url=(String)session.getAttribute(Const.SESSION_KEY_TEMP_LOGO_URL);*/
		TempFileMonitor urlMonitor=(TempFileMonitor)session.getAttribute(Const.SESSION_KEY_TEMP_LOGO_URL);
		
		if(urlMonitor==null)
			model=new ReturnData(false,"logo not exist!!");
		else
		{
			rotate(session,urlMonitor.getUrl());
		}
		return null;
	}
	
	protected void rotate(HttpSession session,String url)
	{
		OperationParam operationParam=(OperationParam)model;	
		try {			
			String newUrl=this.getStaticFileService().generateUrl(url.substring(url.lastIndexOf(".")+1));
			ImageUtils.rotate(this.getStaticFileService().getFilePath(url), operationParam.getDegree(),this.getStaticFileService().getFilePath(newUrl));
			model=new ReturnUrl(newUrl);
			session.setAttribute(Const.SESSION_KEY_TEMP_LOGO_URL, new TempFileMonitor(this.getStaticFileService(),newUrl));
		} catch (IOException e) {
			model=new ReturnData(false,"文件 IO 异常!");
		}
	}
	
	public Object getModel()
	{
		if(model==null)
			model=new OperationParam();
		return model;
	}
	
	public static class OperationParam
	{
		private int scaledWidth;
		private int scaledHeight;
		private int x;
		private int y;
		private int width;
		private int height;
		private String url;//just used when user directly select  an predefine image as cover, not for crop 
		private int degree;
		
		public int getDegree() {
			return degree;
		}
		public void setDegree(int degree) {
			this.degree = degree;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public int getScaledWidth() {
			return scaledWidth;
		}
		public void setScaledWidth(int scaledWidth) {
			this.scaledWidth = scaledWidth;
		}
		public int getScaledHeight() {
			return scaledHeight;
		}
		public void setScaledHeight(int scaledHeight) {
			this.scaledHeight = scaledHeight;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
				
	}
	
	public static class ReturnUrl
	{
		private String url;
		private boolean success;
		public ReturnUrl(){}
		public ReturnUrl(String url){
			this.success=true;
			this.url=url;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		
	} 
	
	protected int getSessionUserId()
	{
		AttributePrincipal p=(AttributePrincipal) ServletActionContext.getRequest().getUserPrincipal();
		return new Integer(p.getAttributes().get("vid").toString());
	}
		
}
