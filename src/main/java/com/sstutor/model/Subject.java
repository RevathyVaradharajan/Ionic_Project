package com.sstutor.model;

public class Subject {

	Long school_subject_id;	//ID at school level for all subjects
	String class_id;			
	String subject_name;
	Long school_id;
	
	public Long getSchool_subject_id() {
		return school_subject_id;
	}
	public void setSchool_subject_id(Long school_subject_id) {
		this.school_subject_id = school_subject_id;
	}
	public Long getSchool_id() {
		return school_id;
	}
	public void setSchool_id(Long school_id) {
		this.school_id = school_id;
	}
	
	public String getClass_id() {
		return class_id;
	}
	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}
	public String getSubject_name() {
		return subject_name;
	}
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}
	
}
