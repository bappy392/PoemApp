package com.example.poeamparsing;

public class WriterNameTable {
	
	int no;
	String name;
	
	public WriterNameTable(int no, String name) {
		super();
		this.no = no;
		this.name = name;
	}
	
	public WriterNameTable() {
		super();
		this.no = 0;
		this.name = null;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
