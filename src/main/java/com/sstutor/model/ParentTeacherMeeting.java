package com.sstutor.model;

public class ParentTeacherMeeting {
	Long school_id;
	Long class_id;
	String section;
	String roll_no;
	String teacher_message;
	String student_message;
	
	public Long getschool_id() {
		return school_id;
	}
	public void setschool_id(Long school_id) {
		this.school_id = school_id;
	}
	public Long getclass_id() {
		return class_id;
	}
	public void setclass_id(Long class_id) {
		this.class_id = class_id;
	}
	public String getsection() {
		return section;
	}
	public void setsection(String section) {
		this.section = section;
	}
	public String getroll_no(){
		return roll_no;
	}
	public void setroll_no(String roll_no){
		this.roll_no = roll_no;
	}
	public String getstudent_message(){
		return student_message;
	}
	public void setstudent_message(String student_message){
		this.student_message = student_message;
	}
	public String getteacher_message(){
		return teacher_message;
	}
	public void setteacher_message(String teacher_message){
		this.teacher_message = teacher_message;
	}



}
