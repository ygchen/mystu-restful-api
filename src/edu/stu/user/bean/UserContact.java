package edu.stu.user.bean;


// Generated 2012-9-25 16:51:36 by Hibernate Tools 3.4.0.CR1

/**
 * TUsers generated by hbm2java
 */
public class UserContact implements java.io.Serializable {

	private long id;
	private int userId;
	private String stuEmail;
	private String personalEmail;
	private String homeAddress;
	private String dormBuildingAddress;
	private String dormRoom;
	private String mobile;
	private String phoneShortnumber;
	private String officePhone;
	private String homePage;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
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
}