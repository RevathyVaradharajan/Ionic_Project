package com.sstutor.model;
import java.util.ArrayList;
public class RetriveMedia {
	Long school_id;
	String url;
	ArrayList<Usages>user_id;
	
	public RetriveMedia(){
	user_id = new ArrayList<Usages>();
	}
		public Long getschool_id(){
		return school_id;
		}
		public void setschool_id(Long school_id){
		this.school_id = school_id;
		}
		public String geturl(){
		return url;
		}
		public void serurl(String url){
		this.url = url;
		}
		public ArrayList<Usages> getuser_id() {
		return user_id;
		}
		public void setuser_id(ArrayList<Usages> user_id) {
		this.user_id = user_id;
		}
		



}
