package com.reflect7.productionnumber.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;

@SuppressWarnings("serial")
public class Day implements Serializable {

	public Day(){};
	
	@Id Long id;
	Date day;
	
	short sumConsumptionMins;
	short sumProductionMins;
	
	Key<User> user;
	
	@Embedded List<Task> tasks = new ArrayList<Task>();
}
