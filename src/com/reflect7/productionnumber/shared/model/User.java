package com.reflect7.productionnumber.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;

@SuppressWarnings("serial")
public class User implements Serializable {
	
	private enum NameStyle {FirstLast, LastFirst};

	public User() {};
	
	@Id Long id;
	public Long getId() { return this.id; }
	
	String email;
	public String getEmail() { return this.email; }
	public void setEmail(String email) { this.email = email; }
	
	/*String firstName;
	public String 
	
	String lastName;
	
	public String getWholeName(NameStyle ns) {
		
		if (ns == NameStyle.FirstLast)
			return firstName + " " + lastName;
		else if (ns == NameStyle.LastFirst)
			return lastName + ", " + firstName;
		
		return "";
	}*/
}
