package com.sstutor.model;

public class Quizanswer {

	Long classes;
	String subject;
	String questions;
	String answer;
	
	public Long getclasses(){
		return classes;
	}
	public void setclasses(Long classes){
		this.classes=classes;
	}
	public String getsubject(){
		return subject;
	}
	public void setsubject(String subject){
		this.subject=subject;
	}
	
	public String getquestions(){
		return questions;
	}
	public void setquestions(String questions){
		this.questions=questions;
	}
	public String getanswer(){
		return answer;
	}
	public void setanswer(String answer){
		this.answer=answer;
	}

	
	
}
