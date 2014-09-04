package com.example.poeamparsing;

import java.io.Serializable;

public class History implements Serializable{
	
	String title;
	String details;
	public History(String title, String details) {
		super();
		this.title = title;
		this.details = details;
	}
	
	public History() {
		super();
	
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "History [title=" + title + ", details=" + details + "]";
	}
	
	

}
