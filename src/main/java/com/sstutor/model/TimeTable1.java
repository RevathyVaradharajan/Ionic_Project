package com.sstutor.model;

public class TimeTable1 {

	Long id;
	Long class_id;
	String start_time;
	String end_time;
	String period;
	Boolean attendance_required;
	String tt_date;
	String teacher_name;
	Long teacher_id;
	String day;
	String section;
	Long school_id;
	String subject;
	String active;

	public Long getid() {
		return id;
	}
	public void setid(Long id) {
		this.id = id;
	}
	public Long getclass_id() {
		return class_id;
	}
	public void setclass_id(Long class_id) {
		this.class_id = class_id;
	}
	public String getstart_time() {
		return start_time;
	}
	public void setstart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getend_time() {
		return end_time;
	}
	public void setend_time(String end_time) {
		this.end_time = end_time;
	}
	public String getperiod() {
		return period;
	}
	public void setperiod(String period) {
		this.period = period;
	}
	public Boolean getattendance_required() {
		return attendance_required;
	}
	public void setattendance_required(Boolean attendance_required) {
		this.attendance_required = attendance_required;
	}
	public String gettt_date() {
		return tt_date;
	}
	public void settt_date(String tt_date) {
		this.tt_date = tt_date;
	}

	public String getteacher_name() {
		return teacher_name;
	}
	public void setteacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getday() {
		return day;
	}
	public void setday(String day) {
		this.day = day;
	}
	public Long getteacher_id() {
		return teacher_id;
	}
	public void setteacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getsection() {
		return section;
	}
	public void setsection(String section) {
		this.section = section;
	}
	public Long getschool_id() {
		return school_id;
	}
	public void setschool_id(Long school_id) {
		this.school_id = school_id;
	}

	public String getsubject() {
		return subject;
	}
	public void setsubject(String subject) {
		this.subject = subject;
	}
	public String getactive() {
		return active;
	}
	public void setactive(String active) {
		this.active = active;
	}



}
