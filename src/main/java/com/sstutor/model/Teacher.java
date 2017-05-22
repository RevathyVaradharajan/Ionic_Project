package com.sstutor.model;

import java.util.ArrayList;

public class Teacher {

	Long teacher_id;
	Long school_id;
	String name;
	
	//Subjects and Roles depends on the class id; it is at a class level
	Long class_id;
	ArrayList<Subject> subjects;
	ArrayList<SchoolTeacherRole> roles;
	
	public Teacher()
	{
		subjects = new ArrayList<Subject>();
		roles = new ArrayList<SchoolTeacherRole>();
	}

	public Long getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}

	public Long getSchool_id() {
		return school_id;
	}

	public void setSchool_id(Long school_id) {
		this.school_id = school_id;
	}

	public Long getClass_id() {
		return class_id;
	}

	public void setClass_id(Long class_id) {
		this.class_id = class_id;
	}

	public ArrayList<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(ArrayList<Subject> subjects) {
		this.subjects = subjects;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<SchoolTeacherRole> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<SchoolTeacherRole> roles) {
		this.roles = roles;
	}
	
	
}
