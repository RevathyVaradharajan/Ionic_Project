package com.sstutor.model;

//Restricted media - change class name and filename
public class Restricted_Media_Access {
	Long media_id;
	Long user_id;
	String url;
	Boolean restricted_access;
	
		public Long getmedia_id(){
		return media_id;
		}
		public void setmedia_id(Long media_id){
		this.media_id = media_id;
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
			public Boolean getrestricted_access(){
				return restricted_access;
				}
				public void setrestricted_access(Boolean restricted_access){
				this.restricted_access = restricted_access;
				}
}
