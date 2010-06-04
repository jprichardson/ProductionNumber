package com.reflect7.productionnumber.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

@SuppressWarnings("serial")
public class Task implements Serializable {
	@Id Long id;
	Date start;
	Date stop;
	short totalMins;
	byte type; // -1, 1
	String description;
}
