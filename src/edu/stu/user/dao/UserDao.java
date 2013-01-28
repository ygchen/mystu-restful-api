package edu.stu.user.dao;

import java.util.List;

import edu.stu.user.bean.UserContact;
import edu.stu.user.bean.UserCover;
import edu.stu.user.bean.UserProfile;
import edu.stu.user.bean.UserStatus;

public interface UserDao {
	UserProfile findUser(String username);
//	UserContact findUser(String username);
	UserContact get(int  userId);
	void update(UserContact userContact);
	void updateUserLogo(int userId,String url);
	void updateUserCover(int userId,String url);
	String getUserStatus(int userId);
	String getUserLogo(int userId);	
	String getUserCover(int userId);
	
	void updateUserStatus(int userId, String status);
	
	List<UserCover> findUserCovers(int userId, int rows);
	List<UserStatus> findUserStatuses(int userId,int rows);
	
	void insert(UserCover userCover);
	void insert(UserStatus userStatus);
	void removeUserCover(UserCover cover);
	void removeUserStatus(UserStatus status);
}