package com.sstutor.model;

import java.sql.Timestamp;

public class page_visit {
	Long user_id;
	Timestamp timestamp;
	String page_visit;
	public Long getuser_id() {
		return user_id;
	}
	public void setuser_id(Long user_id) {
		this.user_id = user_id;
	}
	
	public String getpage_visit() {
		return page_visit;
	}

	public void setpage_visit(String page_visit) {
		this.page_visit = page_visit;
	}
	public Timestamp gettimestamp() {
		return timestamp;
	}
	public void settimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
