package com.sstutor.model;

public class Result {

	String exam_type;
	String standard;
	String section;
	String roll_no;
	String subject;
	Long mark;
	Long out_of_mark;
	
	public String getexam_type(){
		return exam_type;
	}
	public void setexam_type(String exam_type){
		this.exam_type=exam_type;
	}
	public String getstandard() {
		return standard;
	}
	public void setstandard(String standard) {
		this.standard = standard;
	}
	public String getsection() {
		return section;
	}
	public void setsection(String section) {
		this.section = section;
	}
	public String getsubject(){
		return subject;
	}
	public void setsubject(String subject){
		this.subject=subject;
	}
	public String getroll_no(){
		return roll_no;
	}
	public void setroll_no(String roll_no){
		this.roll_no=roll_no;
	}
	public Long getout_of_mark() {
		return out_of_mark;
	}
	public void setout_of_mark(Long out_of_mark) {
		this.out_of_mark = out_of_mark;
	}
	
	public Long getmark() {
		return mark;
	}
	public void setmark(Long mark) {
		this.mark = mark;
	}
	
}
