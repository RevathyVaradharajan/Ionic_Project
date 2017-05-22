package com.sstutor.model;

public class Quiz {

	Long classes;
	String subject;
	String questions;
	String option1;
	String option2;
	String option3;
	String option4;
	String answer;
	Long school_id;

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
	public String getoption1(){
		return option1;
	}
	public void setoption1(String option1){
		this.option1=option1;
	}
	public String getoption2(){
		return option2;
	}
	public void setoption2(String option2){
		this.option2=option2;
	}
	public String getoption3(){
		return option3;
	}
	public void setoption3(String option3){
		this.option3=option3;
	}
	public String getoption4(){
		return option4;
	}
	public void setoption4(String option4){
		this.option4=option4;
	}
	public String getanswer(){
		return answer;
	}
	public void setanswer(String answer){
		this.answer=answer;
	}
	public Long getschool_id(){
		return school_id;
	}
	public void setschool_id(Long school_id){
		this.school_id=school_id;
	}


}
