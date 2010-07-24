package com.reflect7.productionnumber.server.remoteservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.reflect7.productionnumber.client.remoteservice.TaskService;
import com.reflect7.productionnumber.client.remoteservice.TaskServiceAsync;
import com.reflect7.productionnumber.shared.model.Day;
import com.reflect7.productionnumber.shared.model.Task;
import com.reflect7.productionnumber.shared.model.User;

@SuppressWarnings("serial")
public class TaskServiceImpl extends RemoteServiceServlet implements TaskService {

	public Iterable<Day> getDays(String email) throws IllegalArgumentException {
		Objectify ofy = initObjectify();
		QueryResultIterable<Day> results = ofy.query(Day.class).fetch();
		
		List<Day> days = new ArrayList<Day>();
		for (Day t : results){
			days.add(t);
		}
		
		//List<Day> toSave = new ArrayList<Day>();
		//This is a hack to fix the duplication bug
		for (Day origDay : days){
			for (Day copyDay : days){
				if (origDay != copyDay){
					if (origDay.getDateString().equals(copyDay.getDateString())){
						//move tasks overs
						for (Task t : copyDay.getTasks()){
							if(!origDay.doesContainTask(t))
								origDay.getTasks().add(t);
						}
						
						ofy.put(origDay);
						ofy.delete(copyDay);
					}
				}
			}
		}
		
		return days;
	}
	
	public Day getToday(String email) throws IllegalArgumentException {
		Objectify ofy = initObjectify();
	
		TimeZone tz = TimeZone.getTimeZone("America/Chicago");
		
		Calendar c = Calendar.getInstance(tz);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(tz);
		String date = sdf.format(c.getTime());
		System.out.println(date);
		Day day = ofy.query(Day.class).filter("date", date).get();
		if (day == null){
			day = new Day((short)c.get(Calendar.YEAR), (byte)(c.get(Calendar.MONTH)+1), (byte)c.get(Calendar.DAY_OF_MONTH));
		}
		
		return day;
	}
	
	public String saveDay(Day day) throws IllegalArgumentException {
		Objectify ofy = initObjectify();
		
		ofy.put(day);
		
		return null;
	}


	
	private Objectify initObjectify(){
		ObjectifyService.register(Task.class);
		ObjectifyService.register(Day.class);
		ObjectifyService.register(User.class);
		
		Objectify ofy = ObjectifyService.begin();
		return ofy;
	}
}
