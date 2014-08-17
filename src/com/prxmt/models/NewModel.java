package com.prxmt.models;

import java.util.List;

public class NewModel {
	public String message = "";
	public String getMessage() { return message; }
	public void setMessage(String message) { this.message = message; }
	
	public List records;
	public List getRecords() { return records; }
	public void setRecords(List records) { this.records = records; }
}
