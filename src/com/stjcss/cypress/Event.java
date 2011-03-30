package com.stjcss.cypress;

public class Event {
	
	public Event(){}
	public Event(String name, String location, String description,
			String time, String eventID){
		this.name = name;
		this.location = location;
		this.description = description;
		this.time = time;
		this.eventID = eventID;
	}
	public Integer id;
	public String name;
	public String location;
	public String description;
	public String time;
	public String eventID;
}
