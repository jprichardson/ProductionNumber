package com.reflect7.productionnumber.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.google.gwt.i18n.client.DateTimeFormat;

@SuppressWarnings("serial")
public class Task implements Serializable {
	
	public enum TaskType { Consume, Produce };
	
	public Task(){}
	
	@Id Long id;
	public Long getId() { return this.id; }
	
	String desc;
	public String getDescription() { return desc; }
	public void setDescription(String desc) { this.desc = desc; }
	
	Date startTime;
	public Date getStartTime() { return startTime; }
	public String getStartTimeString() { return DateTimeFormat.getShortTimeFormat().format(startTime); }
	public void setStartTime(Date startTime){ this.startTime = startTime;}
	
	Date stopTime;
	public Date getStopTime() { return stopTime;}
	public String getStopTimeString() { return DateTimeFormat.getShortTimeFormat().format(stopTime); }
	public void setStopTime(Date stopTime) { this.stopTime = stopTime; }
	
	public short getTotalMins(){
		if (stopTime == null)
			return 0;
		
		long diff = stopTime.getTime() - startTime.getTime();
		diff /= (1000*60);
		
		return (short)diff;
	}
	
	TaskType taskType;
	public TaskType getTaskType() { return taskType; }
	public void setTaskType(TaskType tt){ taskType = tt;}
	
}
