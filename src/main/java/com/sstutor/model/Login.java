package com.sstutor.model;

public class Login {
	String first_name;
	String last_name;
	String login_id;
	String password;
	Long phone_number;
	String email;
	String forget_password;
	String student_name;
	String section;
	String standard;
	
	
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
	
	public String getfirst_name() {
		return first_name;
	}
	public void setfirst_name(String first_name){
		this.first_name=first_name;
	}
	public String getlast_name() {
		return last_name;
	}
	
	public void setlast_name(String last_name){
		this.last_name=last_name;
	}
	
	public String getlogin_id() {
		return login_id;
	}
	public void setlogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getpassword(){
		return password;
	}
	public void setpassword(String password){
		this.password=password;
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
	
	public String getforget_password() {
		return forget_password;
	}
	public void setforget_password(String forget_password){
		this.forget_password=forget_password;
	}
}
