package com.example.poeamparsing;

public class PoemSQLTable {
	
	private int id;
	private String name;
	private String title;
	private String details;
	
	public PoemSQLTable(int id, String name, String title, String details) {
		super();
		this.id = id;
		this.name = name;
		this.title = title;
		this.details = details;
	}
	
	public PoemSQLTable(String name, String title, String details) {
		super();
		this.name = name;
		this.title = title;
		this.details = details;
	}
	public PoemSQLTable(String title, String details) {
		super();
		this.name = name;
		this.title = title;
		this.details = details;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "PoemSQLTable [id=" + id + ", name=" + name + ", title=" + title
				+ ", details=" + details + "]";
	}
	
	
	
	

}
