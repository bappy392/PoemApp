package com.example.poeamparsing;

public class WriterDetailsTable {
	
	String name,title,details;

	public WriterDetailsTable(String title, String details) {
		super();
		this.name = name;
		this.title = title;
		this.details = details;
	}
	
	public WriterDetailsTable() {
		super();
		this.name = null;
		this.title = null;
		this.details = null;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	
	
	

}
