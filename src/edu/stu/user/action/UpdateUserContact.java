package edu.stu.user.action;

import org.apache.commons.beanutils.BeanUtils;

import edu.stu.user.bean.UserContact;

public class UpdateUserContact extends AbstractAction {
	
	private String stuEmail;
	private String personalEmail;
	private String homeAddress;
	private String dormBuildingAddress;
	private String dormRoom;
	private String mobile;
	private String phoneShortnumber;
	private String officePhone;
	private String homePage;
	
	public String getStuEmail() {
		return stuEmail;
	}

	public void setStuEmail(String stuEmail) {
		this.stuEmail = stuEmail;
	}

	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getDormBuildingAddress() {
		return dormBuildingAddress;
	}

	public void setDormBuildingAddress(String dormBuildingAddress) {
		this.dormBuildingAddress = dormBuildingAddress;
	}

	public String getDormRoom() {
		return dormRoom;
	}

	public void setDormRoom(String dormRoom) {
		this.dormRoom = dormRoom;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhoneShortnumber() {
		return phoneShortnumber;
	}

	public void setPhoneShortnumber(String phoneShortnumber) {
		this.phoneShortnumber = phoneShortnumber;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}


	public String execute()
	{
		
		UserContact uc=new UserContact();
		try {
			BeanUtils.copyProperties(uc, this);
			this.setJsonResult("success",Boolean.TRUE);
			uc.setUserId(getUserId());
			this.getUserService().update(uc);
		} catch (Exception e) {
			e.printStackTrace();
			this.setJsonResult("success",Boolean.FALSE);
		}
		
		
		return JSON;
	}
	
}
