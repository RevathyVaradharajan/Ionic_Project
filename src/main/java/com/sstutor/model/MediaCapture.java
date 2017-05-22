package com.sstutor.model;

public class MediaCapture {
	
	//created_by and id to be included
	
	Long school_id;
	String url;
	String title;
	Boolean restricted_access;
	Long id;
	String created_by;
	String name;
	
	public Long getschool_id(){
	return school_id;
	}
	public void setschool_id(Long school_id){
	this.school_id=school_id;
	}
	
	public String getcreated_by(){
	return created_by;
	}
	public void setcreated_by(String created_by){
	this.created_by=created_by;
	}
		
	public Long getid(){
	return id;
	}
	public void setid(Long id){
	this.id=id;
	}
	
	public String geturl(){
	return url;
	}
	public void seturl(String url){
	this.url = url;
	}
	public String getname(){
		return name;
		}
		public void setname(String name){
		this.name = name;
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
