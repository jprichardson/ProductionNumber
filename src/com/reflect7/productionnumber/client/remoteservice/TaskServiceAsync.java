package com.reflect7.productionnumber.client.remoteservice;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.reflect7.productionnumber.shared.model.Day;

public interface TaskServiceAsync {
	public void getToday(String email, AsyncCallback<Day> callback) throws IllegalArgumentException;
	public void saveDay(Day day, AsyncCallback<String> callback) throws IllegalArgumentException;
	public void getDays(String email, AsyncCallback<Iterable<Day>> callback) throws IllegalArgumentException;
}
