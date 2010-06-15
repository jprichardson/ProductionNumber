package com.reflect7.productionnumber.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;

@SuppressWarnings("serial")
@Cached
public class Day implements Serializable {

	public Day(){};
	
	public Day(short year, byte month, byte day){
		this.year = (byte)(year-2000); this.month = month; this.day = day;
		updateDate();
	}
	
	@Id Long id;
	public Long getId(){ return this.id; }
	
	byte day; //1-31
	public byte getDay() { return day; }
	public void setDay(byte day){ 
		this.day = day; 
		updateDate();
	}
	
	byte month; //1-12
	public byte getMonth() { return month; }
	public void setMonth(byte month) { 
		this.month = month;
		updateDate();
	}
	
	byte year;
	public short getYear() { return (short) (2000+year); }
	public void setYear(short year) { 
		this.year = (byte)(year-2000);
		updateDate();
	}
	
	String date = ""; //YEAR-MONTH-DAY
	public String getDateString() { return date; }
	
	public short getSumConsumptionMins(){
		short sum = 0;
		for (Task t : tasks)
			if (t.getTaskType() == Task.TaskType.Consume)
			sum += t.getTotalMins();
		return sum;
	}
	
	public short getSumProductionMins(){
		short sum = 0;
		for (Task t : tasks)
			if (t.getTaskType() == Task.TaskType.Produce)
				sum += t.getTotalMins();
		return sum;
	} 
	
	Key<User> user;
	
	/*@Embedded List<Task> consumptionTasks = new ArrayList<Task>();
	public List<Task> getConsumptionTasks() { return consumptionTasks; }
	
	@Embedded List<Task> productionTasks = new ArrayList<Task>();
	public List<Task> getProductionTasks() { return productionTasks; }*/
	
	@Embedded List<Task> tasks = new ArrayList<Task>();
	public List<Task> getTasks() { return tasks; }


	private void updateDate(){
		StringBuilder sb = new StringBuilder();
		sb.append("20");
		sb.append(year);
		sb.append('-');
		sb.append(month);
		sb.append('-');
		sb.append(day);
		date = sb.toString();
	}
}
