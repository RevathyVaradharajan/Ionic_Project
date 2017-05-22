package com.sstutor.model;

public class Dailydiary1 {
	
	Long class_id;
	String section;
	String end_date;
	String title;
	String message;
	String subject;
	String created_by;
	Long school_id;
	String activity;
	Long id;

	public Long getid(){
		return id;
	}
	public void setid(Long id){
		this.id=id;
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
	public String getend_date() {
		return end_date;
	}
	public void setend_date(String end_date) {
		this.end_date = end_date;
	}
	public String gettitle(){
		return title;
	}
	public void settitle(String title){
		this.title = title;
	}

	public String getmessage(){
		return message;
	}
	public void setmessage(String message){
		this.message = message;
	}
	public String getsubject() {
		return subject;
	}
	public void setsubject(String subject) {
		this.subject = subject;
	}
	public String getcreated_by() {
		return created_by;
	}
	public void setcreated_by(String created_by) {
		this.created_by = created_by;
	}
	public Long getschool_id() {
		return school_id;
	}
	public void setschool_id(Long school_id) {
		this.school_id = school_id;
	}
	public String getactivity() {
		return activity;
	}
	public void setactivity(String activity) {
		this.activity = activity;
	}

}
