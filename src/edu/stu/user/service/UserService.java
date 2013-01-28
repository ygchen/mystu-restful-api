package edu.stu.user.service;

import java.util.List;

import edu.stu.user.bean.UserContact;
import edu.stu.user.bean.UserProfile;

public interface UserService {
	void update(UserContact userContact);
	void updateUserLogo(int userId,String logoUrl);
	void updateUserCover(int userId,String logoUrl, boolean withExist);
	UserContact getUserContact(int userId) ;		
	UserProfile getUser(String username);
	void updateUserStatus(int userId, String status);
	List<String> getUserStatuses(int userId, int rows);
	String getUserStatus(int userId);
	String getUserLogo(int userId);	
	String getUserCover(int userId);	
	List<String> getUserCovers(int userId, int rows);
}
