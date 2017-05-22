package com.sstutor.model;

public class Exam_timetable {
	String date;
	String from_time;
	String end_time;
	String exam_type;
	String subject;
	Long id;
	Long class_id;
	Long school_id;
	String syllabus;
	
	public String getdate(){
		return date;
	}
	public void setdate(String date){
		this.date = date;
	}
	public String getfrom_time(){
		return from_time;
	}
	public void setfrom_time(String from_time){
		this.from_time=from_time;
	}
	public String getend_time(){
		return end_time;
	}
	public void setend_time(String end_time){
		this.end_time=end_time;
	}
	public String getexam_type(){
		return exam_type;
	}
	public void setexam_type(String exam_type){
		this.exam_type=exam_type;
	}
	public String getsubject(){
		return subject;
	}
	public void setsubject(String subject){
		this.subject=subject;
	}
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
	public Long getschool_id(){
		return school_id;
		}
	public void setschool_id(Long school_id){
		this.school_id=school_id;
	}
	public String getsyllabus(){
		return syllabus;
	}
	public void setsyllabus(String syllabus){
		this.syllabus=syllabus;
	}
	
}
