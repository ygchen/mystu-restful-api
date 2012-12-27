package edu.stu.user.restful;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import edu.stu.user.Const;
import edu.stu.user.action.TempFileMonitor;
import edu.stu.util.ImageUtils;
import edu.stu.util.ReturnData;

public class UserCoverController extends UserLogoController {
	
	@Override
	protected String getImageUrl()
	{
		return this.getUserService().getUserCover(getUserId());
	}
	
	public String update()
	{
		OperationParam operationParam=(OperationParam)model;			
		this.getUserService().updateUserCover(getUserId(), operationParam.getUrl(), true);
		model=new ReturnData();
		return null;
	}
	
	public String list()
	{
		this.model=this.getUserService().getUserCovers(getUserId(), 10);
		return null;
	}
	
	@Override
	public String crop()
	{
	
		if(this.getUserId()!=getSessionUserId())
		{
			new ReturnData(false,"Illegal access!!");//试图修改别人的
		}

		HttpSession session=ServletActionContext.getRequest().getSession();
		TempFileMonitor urlMonitor=(TempFileMonitor)session.getAttribute(Const.SESSION_KEY_TEMP_COVER_URL);

		if(urlMonitor==null)
			model=new ReturnData(false,"cover not exist!!");
		else
		{
			OperationParam operationParam=(OperationParam)model;			
			try {
				BufferedImage img= ImageUtils.scaleAndCropAndAddTransparencyToLeftRightSideFromFile(this.getStaticFileService().getFilePath(urlMonitor.getUrl()),
						operationParam.getScaledWidth(),operationParam.getScaledHeight(),
						operationParam.getX(), operationParam.getY(), operationParam.getWidth(), operationParam.getHeight(),200);
				File temp=File.createTempFile("cover", ".png");
				FileOutputStream fout=new FileOutputStream(temp);
				ImageIO.write(img, "png", fout);
				fout.flush();
				fout.close();		
				
				//delete file in session
				session.removeAttribute(Const.SESSION_KEY_TEMP_COVER_URL);
				
				//save as png and get new url
				String url=getStaticFileService().save2file(temp, "png");
				
				//save to db
				this.getUserService().updateUserCover(getUserId(), url,false);
				
				//delete temporary file
				temp.delete();
				
				model=new ReturnUrl(url);
			} catch (IOException e) {
				e.printStackTrace();
				model=new ReturnData(false,"文件 IO 异常!");
			}
		}
		return null;
	}
	
	@Override
	public String rotate()
	{

		if(this.getUserId()!=getSessionUserId())
		{
			new ReturnData(false,"Illegal access!!");//试图修改别人的
		}
		
		HttpSession session=ServletActionContext.getRequest().getSession();
		TempFileMonitor urlMonitor=(TempFileMonitor)session.getAttribute(Const.SESSION_KEY_TEMP_COVER_URL);
		
		if(urlMonitor==null)
			model=new ReturnData(false,"cover not exist!!");
		else
		{
			rotate(session,urlMonitor.getUrl());
		}
		return null;
	}
}
