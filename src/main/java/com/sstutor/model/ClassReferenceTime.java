package com.sstutor.model;

public class ClassReferenceTime {
Long id;
Long class_id;
String start_time;
String end_time;
String period_type;
Boolean attendance_required;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public Long getClass_id() {
	return class_id;
}
public void setClass_id(Long class_id) {
	this.class_id = class_id;
}
public String getStart_time() {
	return start_time;
}
public void setStart_time(String start_time) {
	this.start_time = start_time;
}
public String getEnd_time() {
	return end_time;
}
public void setEnd_time(String end_time) {
	this.end_time = end_time;
}
public String getPeriod_type() {
	return period_type;
}
public void setPeriod_type(String period_type) {
	this.period_type = period_type;
}
public Boolean getAttendance_required() {
	return attendance_required;
}
public void setAttendance_required(Boolean attendance_required) {
	this.attendance_required = attendance_required;
}
}
