package com.sstutor.model;



public class url {
	Long school_id;
	Long user_id;
	String url;
		
		public Long getschool_id(){
		return school_id;
		}
		public void setschool_id(Long school_id){
		this.school_id = school_id;
		}
		public Long getuser_id(){
			return user_id;
			}
			public void setuser_id(Long user_id){
			this.user_id = user_id;
			}
			
		public String geturl(){
		return url;
		}
		public void seturl(String url){
		this.url = url;
		}
		public void setString(int i, String url) {
			this.url=url;
			
		}
		



}
