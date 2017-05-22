package com.sstutor.model;
public class Attendance {
	Long class_id;
	Long student_id;
	Boolean attendance_check;
	String modified_timestamp;
	String period;
	String student_name;
	String section;
	Long school_id;
	Long modified_by;
	String date;
	Long tt_id;
	
	public Long getclass_id() {
		return class_id;
	}
	public void setclass_id(Long class_id) {
		this.class_id = class_id;
	}
	public Long getstudent_id() {
		return student_id;
	}
	public void setstudent_id(Long student_id) {
		this.student_id = student_id;
	}
	public Boolean getattendance_check() {
		return attendance_check;
	}
	public void setattendance_check(Boolean attendance_check) {
		this.attendance_check = attendance_check;
	}
	public String getmodified_timestamp() {
		return modified_timestamp;
	}
	public void setmodified_timestamp(String modified_timestamp) {
		this.modified_timestamp = modified_timestamp;
	}
	public String getstudent_name() {
		return student_name;
	}
	public void setstudent_name(String student_name) {
		this.student_name = student_name;
	}
	public String getperiod() {
		return period;
	}
	public void setperiod(String period) {
		this.period = period;
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
	public Long getmodified_by() {
		return modified_by;
	}
	public void setmodified_by(Long modified_by) {
		this.modified_by = modified_by;
	}
	public String getdate() {
		return date;
	}
	public void setdate(String date) {
		this.date = date;
	}
	public Long gettt_id() {
		return tt_id;
	}
	public void settt_id(Long tt_id) {
		this.tt_id = tt_id;
	}

}
