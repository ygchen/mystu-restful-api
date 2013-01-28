package edu.stu.user.bean;

public class UserProfile {
	private long id;
	private String name;
	private String nameEn;
	private String coverImage;
	private String profilePic;
	private int userId;
	private String school;
	private Integer yearOfAttendance;//入学年分
	private String status;//状态，已毕业，在校
	private String position;//本科，研究生，博士
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getCoverImage() {
		return coverImage;
	}
	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public Integer getYearOfAttendance() {
		return yearOfAttendance;
	}
	public void setYearOfAttendance(Integer yearOfAttendance) {
		this.yearOfAttendance = yearOfAttendance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
}
