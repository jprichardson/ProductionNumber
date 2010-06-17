package com.reflect7.productionnumber.client.remoteservice;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.RemoteService;
import com.reflect7.productionnumber.shared.model.Day;


@RemoteServiceRelativePath("task")
public interface TaskService extends RemoteService{
	Day getToday(String user) throws IllegalArgumentException;
	String saveDay(Day day) throws IllegalArgumentException;
	Iterable<Day> getDays(String email) throws IllegalArgumentException;
}
