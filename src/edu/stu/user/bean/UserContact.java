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
	
	private boolean stuEmailVisible;
	private boolean personalEmailVisible;
	private boolean homeAddressVisible;
	private boolean addressVisible;
	
	private boolean mobileVisible;
	private boolean phoneShortnumberVisible;
	private boolean officePhoneVisible;
	private boolean homePageVisible;	
	
	
	
	public boolean isStuEmailVisible() {
		return stuEmailVisible;
	}
	public void setStuEmailVisible(boolean stuEmailVisible) {
		this.stuEmailVisible = stuEmailVisible;
	}
	public boolean isPersonalEmailVisible() {
		return personalEmailVisible;
	}
	public void setPersonalEmailVisible(boolean personalEmailVisible) {
		this.personalEmailVisible = personalEmailVisible;
	}
	public boolean isHomeAddressVisible() {
		return homeAddressVisible;
	}
	public void setHomeAddressVisible(boolean homeAddressVisible) {
		this.homeAddressVisible = homeAddressVisible;
	}
	public boolean isAddressVisible() {
		return addressVisible;
	}
	public void setAddressVisible(boolean addressVisible) {
		this.addressVisible = addressVisible;
	}
	public boolean isMobileVisible() {
		return mobileVisible;
	}
	public void setMobileVisible(boolean mobileVisible) {
		this.mobileVisible = mobileVisible;
	}
	public boolean isPhoneShortnumberVisible() {
		return phoneShortnumberVisible;
	}
	public void setPhoneShortnumberVisible(boolean phoneShortnumberVisible) {
		this.phoneShortnumberVisible = phoneShortnumberVisible;
	}
	public boolean isOfficePhoneVisible() {
		return officePhoneVisible;
	}
	public void setOfficePhoneVisible(boolean officePhoneVisible) {
		this.officePhoneVisible = officePhoneVisible;
	}
	public boolean isHomePageVisible() {
		return homePageVisible;
	}
	public void setHomePageVisible(boolean homePageVisible) {
		this.homePageVisible = homePageVisible;
	}
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
