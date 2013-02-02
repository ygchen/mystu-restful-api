package edu.stu.user.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import edu.stu.user.bean.UserContact;
import edu.stu.user.bean.UserCover;
import edu.stu.user.bean.UserProfile;
import edu.stu.user.bean.UserStatus;
import edu.stu.user.dao.AbstractHibernateDao;
import edu.stu.user.dao.UserDao;

public class UserDaoImpl extends AbstractHibernateDao implements UserDao {

/*	@Override
	public UserContact findUser(String username) {	
		List rows= this.find("from UserContact where upper(username)=upper(?)",username, 1);
		if(rows.size()==0)
			return null;
		else
			return (UserContact)rows.get(0);
	}*/

	@Override
	public UserContact get(int userId) {
		List ret=this.find("from UserContact where userId=?", new Integer(userId), 1);
		if(ret.size()>0)
			return (UserContact)ret.get(0);
		else
			return null;
	}

	@Override
	public void update(UserContact userContact) {
		UserContact old=this.get(userContact.getUserId());
		String stuEmail=old.getStuEmail();
		long id=old.getId();
		try {
			BeanUtils.copyProperties(old, userContact);
		} catch (Exception e) {
			e.printStackTrace();
		}
		old.setId(id);
		old.setStuEmail(stuEmail);
		this.getHibernateTemplate().update(old);
	}

	@Override
	public void updateUserLogo(final int userId,final String url) {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query q=session.getNamedQuery("update_user_logo");
				q.setString(0, url);
				q.setInteger(1, userId);
				return q.executeUpdate();
			}
		});
	}
	
	@Override
	public void updateUserCover(final int userId,final String url) {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query q=session.getNamedQuery("update_user_cover");
				q.setString(0, url);
				q.setInteger(1, userId);
				return q.executeUpdate();
			}
		});
	}

	@Override
	public String getUserStatus(int userId) {
		return (String)this.findUniqueByNameSql("get_user_status", userId);
	}
	

	@Override
	public String getUserLogo(int userId) {
		return (String)this.findUniqueByNameSql("get_user_logo", userId);
	}
	
	@Override
	public String getUserCover(int userId) {
		return (String)this.findUniqueByNameSql("get_user_cover", userId);
	}

	@Override
	public void updateUserStatus(final int userId, final String status)  {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query q=session.getNamedQuery("update_user_status");
				q.setString(0, status);
				q.setInteger(1, userId);
				return q.executeUpdate();
			}
		});
	}

	@Override
	public List<UserCover> findUserCovers(int userId, int rows) {
		return this.find("from UserCover where userId=? order by createdAt desc ", new Integer(userId), rows);
	}

	@Override
	public List<UserStatus> findUserStatuses(int userId, int rows) {
		return this.find("from UserStatus where userId=? order by createdAt desc", new Integer(userId), rows);
	}

	@Override
	public void removeUserCover(UserCover cover) {
		this.getHibernateTemplate().delete(cover);
	}

	@Override
	public void removeUserStatus(UserStatus status) {
		this.getHibernateTemplate().delete(status);
	}

	@Override
	public void insert(UserCover userCover) {
		this.getHibernateTemplate().save(userCover);
	}

	@Override
	public void insert(UserStatus userStatus) {
		this.getHibernateTemplate().save(userStatus);
	}

	@Override
	public UserProfile findUser(String username) {
		if(username.indexOf('@')<0)
		{
			username=username+"@stu.edu.cn";
		}
		
		List users=this.find("select a from UserProfile a, UserContact b where a.userId=b.userId and b.stuEmail=?", username, 1);
		if(users.size()==0)
			return null;
		else
			return (UserProfile)users.get(0);
	}
	

}
