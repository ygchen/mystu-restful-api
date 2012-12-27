package edu.stu.user.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.stu.generic.StaticFileService;
import edu.stu.user.bean.UserContact;
import edu.stu.user.bean.UserCover;
import edu.stu.user.bean.UserStatus;
import edu.stu.user.dao.UserDao;
import edu.stu.user.service.UserService;

public class UserServiceImpl implements UserService {

	private UserDao userDao;	
	private StaticFileService staticFileService;
	private String defaultCoverFolder;
	private int maxUserCustomedCover=5;
	private int maxUserStatus=5;
	
	public void setStaticFileService(StaticFileService staticFileService) {
		this.staticFileService = staticFileService;
	}
	
	
	

	public void setMaxUserStatus(int maxUserStatus) {
		this.maxUserStatus = maxUserStatus;
	}




	public void setMaxUserCustomedCover(int maxUserCustomedCover) {
		this.maxUserCustomedCover = maxUserCustomedCover;
	}




	public void setDefaultCoverFolder(String defautlCoverFolderPath) {
		this.defaultCoverFolder = defautlCoverFolderPath;
	}



	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void update(UserContact userContact) {
		this.userDao.update(userContact);
	}

	
	@Override
	public void updateUserLogo(int userId, String logoUrl) {
		
		this.userDao.updateUserLogo(userId, logoUrl);
	}

	@Override
	public void updateUserCover(int userId, String coverUrl, boolean withExist) {
		
		List<UserCover> list=this.userDao.findUserCovers(userId,10);				
		if(!withExist)
		{
			UserCover uc=new UserCover();
			uc.setCreatedAt(System.currentTimeMillis());
			uc.setUrl(coverUrl);
			uc.setUserId(userId);			
			this.userDao.insert(uc);
			if(list.size()>maxUserCustomedCover)
			{
				this.userDao.removeUserCover(list.get(list.size()-1));
				//删除文件
				this.staticFileService.delete(list.get(list.size()-1).getUrl());
			}
		}
		
		this.userDao.updateUserCover(userId, coverUrl);
	}

	

	@Override
	public UserContact getUserContact(int userId) {
		return this.userDao.get(userId);
	}

	@Override
	public UserContact getUser(String username) {
		
		return this.userDao.findUser(username);
	}

	@Override
	public void updateUserStatus(int userId, String status) {
		List<UserStatus> list=this.userDao.findUserStatuses(userId,10);
		boolean exist=false;
		for(UserStatus us:list)
		{
			if(us.getStatus().equalsIgnoreCase(status))
			{
				exist=true;
				break;
			}
		}
		if(!exist)
		{
			UserStatus us=new UserStatus();
			us.setCreatedAt(System.currentTimeMillis());
			us.setStatus(status);
			us.setUserId(userId);			
			this.userDao.insert(us);
			if(list.size()>maxUserStatus)
			{
				this.userDao.removeUserStatus(list.get(list.size()-1));
			}
		}
		
		this.userDao.updateUserStatus( userId, status);
	}

	@Override
	public String getUserStatus(int userId) {
		return userDao.getUserStatus(userId);
	}

	@Override
	public String getUserLogo(int userId) {
		return userDao.getUserLogo(userId);
	}
	
	@Override
	public String getUserCover(int userId) {
		return userDao.getUserCover(userId);
	}

	@Override
	public List<String> getUserStatuses(int userId, int rows) {
		List<UserStatus> list=this.userDao.findUserStatuses(userId, rows);
		List<String> ret=new ArrayList<String>(list.size());
		for(UserStatus us:list)
		{
			ret.add(us.getStatus());
		}
		return ret;
	}

	@Override
	public List<String> getUserCovers(int userId, int rows) {		
		List<UserCover> list=this.userDao.findUserCovers(userId, rows);
		List<String> ret=new ArrayList<String>(list.size());
		for(UserCover us:list)
		{
			ret.add(us.getUrl());
		}
		
		if(ret.size()<rows)
		{
			int lessQty=rows-ret.size();
			File[] defaultFiles=getDefaultCovers();
			for(int i=0; i<lessQty && i<defaultFiles.length;i++)
			{
				ret.add(this.staticFileService.getFileUrl(defaultFiles[i].getAbsolutePath()));
			}
		}		
		return ret;
	}
		
	private File[] getDefaultCovers()
	{
		File[] files=new File(this.defaultCoverFolder).listFiles();
		Arrays.sort(files, new Comparator<File>(){

			@Override
			public int compare(File right, File left) {
				
				return right.lastModified()>left.lastModified()?-1:1;
			}
			
		});
	/*	
		List<String> ret=new ArrayList<String>(files.length);
		for(File f:files)
		{
			ret.add(f.getAbsolutePath());
		}*/
		
		return files;
	}	
	
	public static void main(String[] args)
	{
		String folder="e:/";
		File[] files=new File(folder).listFiles();
		Arrays.sort(files, new Comparator<File>(){

			@Override
			public int compare(File right, File left) {
				
				return right.lastModified()>left.lastModified()?-1:1;
			}
			
		});
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-M-d HH:mm");
		for(File f:files)
		{
			System.err.println(f.getName()+", modified at "+sdf.format(f.lastModified()));
		}
	}
}
