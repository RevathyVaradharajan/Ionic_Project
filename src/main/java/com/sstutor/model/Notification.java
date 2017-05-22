package com.sstutor.model;

public class Notification {
    Long id;
    String from_date;
    String to_date;
    String title;
    String message;
    String created_by;
    Long school_id;
    String weekday1;
    String weekday2;
    String weekday3;
    String weekday4;
    String weekday5;
    String weekday6;
    String weekday7;
      
    
	public Long getid(){
		return id;
	}
	public void setid(Long id){
		this.id=id;
	}
	
	public String getmessage(){
		return message;
	}
	public void setmessage(String message){
		this.message = message;
	}
	
	public String gettitle(){
		return title;
	}
	public void settitle(String title){
		this.title = title;
	}
	
	
	public Long getschool_id(){
		return school_id;
		}
	public void setschool_id(Long school_id){
		this.school_id=school_id;
	}
	
	public String getfrom_date(){
			return from_date;
	}
	public void setfrom_date(String from_date){
			this.from_date = from_date;
	}
	
	public String getto_date(){
		return to_date;
	}
	public void setto_date(String to_date){
		this.to_date = to_date;
	}
	public String getcreated_by(){
		return created_by;
	}
	public void setcreated_by(String created_by){
		this.created_by=created_by;
	}
	public String getweekday1(){
		return weekday1;
	}
	public void setweekday1(String weekday1){
		this.weekday1=weekday1;
	}
	public String getweekday2(){
		return weekday2;
	}
	public void setweekday2(String weekday2){
		this.weekday2=weekday2;
	}
	public String getweekday3(){
		return weekday3;
	}
	public void setweekday3(String weekday3){
		this.weekday3=weekday3;
	}
	public String getweekday4(){
		return weekday4;
	}
	public void setweekday4(String weekday4){
		this.weekday4=weekday4;
	}
	public String getweekday5(){
		return weekday5;
	}
	public void setweekday5(String weekday5){
		this.weekday5=weekday5;
	}
	public String getweekday6(){
		return weekday6;
	}
	public void setweekday6(String weekday6){
		this.weekday6=weekday6;
	}
	public String getweekday7(){
		return weekday7;
	}
	public void setweekday7(String weekday7){
		this.weekday7=weekday7;
	}

	
	
}
