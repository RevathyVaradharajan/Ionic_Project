package com.sstutor.model;

public class StorageMedia {
	Long school_id;
	String url;
	String title;
	Boolean restricted_access;
	public Long getschool_id(){
	return school_id;
	}
	public void setschool_id(Long school_id){
	this.school_id=school_id;
	}
	public String geturl(){
	return url;
	}
	public void seturl(String url){
	this.url = url;
	}
	public String gettitle(){
	return title;
	}
	public void settitle(String title){
	this.title = title;
	}
	public Boolean getrestricted_access(){
	return restricted_access;
	}
	public void setrestricted_access(Boolean restricted_access){
	this.restricted_access = restricted_access;
	}

}
