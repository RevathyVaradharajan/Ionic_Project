package com.sstutor.model;

public class Master_db {
	String student_name;
	String section;
	String standard;
	String login_id;
	Long phone_number;
	String email;
	Long student_id;
	Long school_id;
	Long roll;
	
	public String getstudent_name() {
		return student_name;
	}
	public void setstudent_name(String student_name){
		this.student_name=student_name;
	}
	public String getsection() {
		return section;
	}
	public void setsection(String section){
		this.section=section;
	}
	public String getstandard() {
		return standard;
	}
	public void setstandard(String standard){
		this.standard=standard;
	}
	public String getlogin_id() {
		return login_id;
	}
	public void setstudent_id(String login_id) {
		this.login_id = login_id;
	}
	public Long getphone_number() {
		return phone_number;
	}
	
	public void setphone_number(Long phone_number) {
		this.phone_number = phone_number;
	}
	
	public String getemail() {
		return email;
	}
	
	public void setemail(String email){
		this.email=email;
	}
	public Long getstudent_id() {
		return student_id;
	}
	public void setstudent_id(Long student_id) {
		this.student_id = student_id;
	}
	public Long getschool_id() {
		return school_id;
	}
	public void setschool_id(Long school_id) {
		this.school_id = school_id;
	}
	public Long getroll() {
		return roll;
	}
	public void setroll(Long roll) {
		this.roll = roll;
	}
	
	
}
