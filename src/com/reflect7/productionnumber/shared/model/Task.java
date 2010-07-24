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
	public String getStartTimeString() { return DateTimeFormat.getShortTimeFormat().format(startTime); } //this only works on client
	public void setStartTime(Date startTime){ this.startTime = startTime;}
	
	Date stopTime;
	public Date getStopTime() { return stopTime;}
	public String getStopTimeString() { return DateTimeFormat.getShortTimeFormat().format(stopTime); } //this only works on client
	public void setStopTime(Date stopTime) { this.stopTime = stopTime; }
	
	public float getTotalMins(){
		if (stopTime == null)
			return 0;
		
		long diff = stopTime.getTime() - startTime.getTime();
		float d = diff / (1000.0f * 60.0f);
		
		return d;
	}
	
	TaskType taskType;
	public TaskType getTaskType() { return taskType; }
	public void setTaskType(TaskType tt){ taskType = tt;}
	
	public Boolean equals(Task otherTask){
		Boolean compare = true;
		compare = compare && this.desc.equals(otherTask.getDescription());
		compare = compare && this.startTime.equals(otherTask.getStartTime());
		compare = compare && this.stopTime.equals(otherTask.getStopTime());
		compare = compare && this.taskType.equals(otherTask.getTaskType());
		return compare;
	}
	
	public String toString(){
		String tt = "";
		if (this.taskType != null)
			tt = this.taskType.toString();
		
		String d = this.desc;
		
		String sr = "";
		if (this.startTime != null)
			sr = this.startTime.toGMTString();
			
		String sp = "";
		if (this.stopTime != null)
			sp = this.stopTime.toGMTString();
		
		return tt + ": " + d + " " + sr + " to" + sp;
	}
	
}
