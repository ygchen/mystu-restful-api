package edu.stu.user.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import edu.stu.generic.StaticFileService;
import edu.stu.user.Const;

public class UploadLogoOrCover extends AbstractAction {
	private StaticFileService staticFileService;
	public void setStaticFileService(StaticFileService staticFileService) {
		this.staticFileService = staticFileService;
	}
	
	private File image;
	private String imageFileName;
	private String imageContentType;

	private String url;
	private boolean success;
	
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	public String getImageContentType() {
		return imageContentType;
	}
	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	
	
	public String uploadLogo() throws Exception
	{	
	
		try{
			this.url=this.staticFileService.save2file(image, imageFileName.substring(imageFileName.lastIndexOf('.')+1));
			HttpSession session=ServletActionContext.getRequest().getSession();
			/*
			TempFileMonitor old=(TempFileMonitor)session.getAttribute(Const.SESSION_KEY_TEMP_LOGO_URL);
			if(old!=null)
			{
				this.staticFileService.delete(old.getUrl());
			}
			*/
			session.setAttribute(Const.SESSION_KEY_TEMP_LOGO_URL, new TempFileMonitor(this.staticFileService, url));
//			this.getUserService().updateUserLogo(this.getUserId(), url);
			this.success=true;
		}
		catch(IOException e)
		{
			e.printStackTrace();			
		}
		return SUCCESS;
	}
	
	public String uploadCover() throws Exception
	{	
	
		try{
			this.url=this.staticFileService.save2file(image, imageFileName.substring(imageFileName.lastIndexOf('.')+1));
			
			/*HttpSession session=ServletActionContext.getRequest().getSession();			
			String old=(String)session.getAttribute(Const.SESSION_KEY_TEMP_COVER_URL);
			if(old!=null)
			{
				this.staticFileService.delete(old);
			}			
			session.setAttribute(Const.SESSION_KEY_TEMP_COVER_URL, url);*/
			
			ServletActionContext.getRequest().getSession().setAttribute(Const.SESSION_KEY_TEMP_COVER_URL, new TempFileMonitor(this.staticFileService, url));
			
			//暂更新数据库，等待剪切保存后再更新
//			this.getUserService().updateUserCover(this.getUserId(), url);
			this.success=true;
		}
		catch(IOException e)
		{
			e.printStackTrace();			
		}
		return SUCCESS;
	}
	
	public String getUrl() {
		return url;
	}
	public boolean isSuccess() {
		return success;
	}
	
}
